package com.woowang.insta.controller;

import com.querydsl.core.Tuple;
import com.woowang.insta.domain.Comment;
import com.woowang.insta.domain.Feed;
import com.woowang.insta.domain.Member;
import com.woowang.insta.dto.CommentDto;
import com.woowang.insta.dto.FeedDto;
import com.woowang.insta.dto.MemberDto;
import com.woowang.insta.repository.query.FeedQueryRepository;
import com.woowang.insta.service.FeedService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedController {

	private final FeedQueryRepository feedQueryRepository;
	private final FeedService feedService;

	/**
	 *  피드 작성
	 */
	@PostMapping("/feed")
	public FeedResponse saveFeed(@RequestBody FeedRequest request){
		Long feedId = feedService.write(
				request.getMemberId(),
				request.getImagePath(),
				request.getDescription(),
				request.tags);
		return new FeedResponse(feedId);
	}
	/**
	 * 피드 수정
	 */
	@PutMapping("/feed/{id}")
	public FeedResponse editFeed(@PathVariable("id")Long feedId,
	                             @RequestBody FeedRequest request){
		Long editId = feedService.edit(
				request.memberId,
				feedId,
				request.imagePath,
				request.description,
				request.tags);
		return new FeedResponse(editId);
	}
	/**
	 * 피드 삭제
	 */
	@DeleteMapping("/feed/{id}")
	public FeedResponse deleteFeed(@PathVariable("id")Long feedId,
	                       @RequestBody FeedRequest request){
		feedService.delete(request.memberId, feedId);
		return new FeedResponse((long) -1);

	}
	/**
	 * 피드 여러개 조회 (작성자, 피드, 댓글 수, 좋아요 수) sql 1건 발생
	 */
	@GetMapping("/feeds")
	public List<FeedDto> getFeeds(){
		List<Tuple> feeds = feedQueryRepository.getFeeds();
		return feeds.stream().map(f->{
			FeedDto feedDto = new FeedDto(f.get(0, Feed.class));
			feedDto.setCommentCount(f.get(1,Long.class));
			feedDto.setLikeFeedCount(f.get(2,Long.class));
			return feedDto;
		}).collect(Collectors.toList());
	}
	/**
	 * 피드 한건 조회 (피드, 댓글(작성자 포함), 작성자) -> sql 3번 (피드,피드 작성자,좋아요 수 +댓글 + 댓글 작성자 )
	 * 상위 3개만 조회
	 * XXToOne 의 경우는 fetch, 나머지는 lazy 로딩
	 */
	@GetMapping("/feed/{id}")
	public FeedDto getFeed(@PathVariable("id")Long id){
		Tuple feed = feedQueryRepository.getFeed(id);
		FeedDto feedDto = new FeedDto(feed.get(0,Feed.class));
		feedDto.setCommentCount((long) feed.get(0,Feed.class).getComments().size());
		int size = Math.min(feed.get(0, Feed.class).getComments().size(), 3);
		for(int i=0;i<size;i++){
			feedDto.getComments().add(new CommentDto(feed.get(0,Feed.class).getComments().get(i)));
		}
		feedDto.setLikeFeedCount(feed.get(1,Long.class));
		return feedDto;
	}
	/**
	 * 해당 게시글 댓글들 불러오기 fetch로 join, 5개씩 페이지네이션
	 */
	@GetMapping("/feed/{id}/comments")
	public List<CommentDto> getComments(@PathVariable("id")Long id,
	                                    @RequestParam(value = "offset",defaultValue = "0")int offset,
	                                    @RequestParam(value = "limit",defaultValue = "5")int limit){
		List<Comment> comments = feedQueryRepository.getComments(id,offset,limit);
		List<CommentDto> commentDtos = comments.stream().map(c-> new CommentDto(c)).collect(Collectors.toList());
		return commentDtos;
	}

	/**
	 * 해당 게시글 좋아요 한 멤버 불러오기  sql 1번 실행, 2개씩 페이지네이션
	 */
	@GetMapping("/feed/{id}/likes")
	public List<MemberDto> getMembersLikeFeed(@PathVariable("id") Long id,
	                                          @RequestParam(value = "offset",defaultValue = "0")int offset,
	                                          @RequestParam(value = "limit",defaultValue = "2")int limit){
		List<Member> members = feedQueryRepository.getMembersLikeFeed(id,offset,limit);
		return members.stream().map(m->new MemberDto(m)).collect(Collectors.toList());
	}
	@Data
	@AllArgsConstructor
	static class Result<T>{
		private int count;
		private T data;
	}

	@Data
	static class FeedRequest {
		private Long memberId;
		private String imagePath;
		private String description;
		private String tags;
	}
	@Data
	static class FeedResponse {
		private Long id;
		FeedResponse(Long id){this.id = id;}
	}
}