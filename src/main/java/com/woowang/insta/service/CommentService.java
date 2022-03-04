package com.woowang.insta.service;

import com.woowang.insta.domain.Comment;
import com.woowang.insta.domain.Feed;
import com.woowang.insta.domain.Member;
import com.woowang.insta.repository.CommentRepository;
import com.woowang.insta.repository.FeedRepository;
import com.woowang.insta.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final FeedRepository feedRepository;

	/**
	 * 댓글 작성
	 */
	@Transactional
	public Long write(Long memberId,Long feedId,String description){
		Member member = memberRepository.findById(memberId).get();
		Feed feed = feedRepository.findById(feedId).get();

		Comment comment = Comment.createComment(feed,member,description);
		commentRepository.save(comment);

		return comment.getId();
	}


}
