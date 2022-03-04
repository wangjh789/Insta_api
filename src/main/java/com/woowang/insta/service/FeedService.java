package com.woowang.insta.service;

import com.woowang.insta.domain.Feed;
import com.woowang.insta.domain.LikeFeed;
import com.woowang.insta.domain.Member;
import com.woowang.insta.repository.FeedRepository;
import com.woowang.insta.repository.LikeFeedRepository;
import com.woowang.insta.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedService {

	private final FeedRepository feedRepository;
	private final MemberRepository memberRepository;
	private final LikeFeedRepository likeFeedRepository;

	/**
	 * 작성
	 */
	@Transactional
	public Long write(Long memberId,String imagePath,String description,String tags){
		validFeedFormat(imagePath);
		Member member = memberRepository.findById(memberId).get();
		Feed feed = Feed.createFeed(member,imagePath,description,tags);
		feedRepository.save(feed);

		return feed.getId();
	}
	/**
	 * 수정
	 */
	@Transactional
	public Long edit(Long memberId,Long feedId,String imagePath,String description,String tags){
		validFeedFormat(imagePath);
		Member member = memberRepository.findById(memberId).get();
		Feed feed = feedRepository.findById(feedId).get();
		checkFeedWriter(member,feed);

		feed.editFeed(imagePath,description,tags);
		return feed.getId();
	}
	/**
	 * 삭제
	 */
	@Transactional
	public void delete(Long memberId,Long feedId){
		Member member = memberRepository.findById(memberId).get();
		Feed feed = feedRepository.findById(feedId).get();
		checkFeedWriter(member,feed);

		feed.deleteFeed();
		feedRepository.delete(feed);
	}

	/**
	 * 좋아요 (이미 좋아요를 누른 상태면 취소)
	 */
	@Transactional
	public void likeFeed(Long memberId,Long feedId){
		Member member = memberRepository.findById(memberId).get();
		Feed feed = feedRepository.findById(feedId).get();
		List<LikeFeed> find = likeFeedRepository.findByMemberAndFeed(member, feed);
		if(find.isEmpty()){ //Like
			LikeFeed likeFeed = LikeFeed.createLikeFeed(member,feed);
			likeFeedRepository.save(likeFeed);
		}else{ // cancel like
			find.get(0).cancel();
			likeFeedRepository.delete(find.get(0));
		}
	}

	private void validFeedFormat(String imagePath){
		if(!StringUtils.hasText(imagePath)) throw new IllegalStateException("올바른 포맷이 아닙니다.");
	}

	private void checkFeedWriter(Member member,Feed feed){
		if(feed.getMember() != member){
			throw new IllegalStateException("작성자가 아닙니다.");
		}
	}

}
