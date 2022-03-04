package com.woowang.insta.service;

import com.woowang.insta.domain.Comment;
import com.woowang.insta.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private FeedService feedService;

	@Test
	public void 댓글_작성(){
		Long memberId = memberService.join("email","password","nickname");
		Long feedId = feedService.write(memberId,"imagePath","desc","tags");

		Long commentId = commentService.write(memberId,feedId,"desc1");
		Comment comment = commentRepository.findById(commentId).get();

		assertEquals(commentId,comment.getId());
		assertEquals(comment.getDescription(),"desc1");
		assertEquals(comment.getMember().getId(),memberId);
	}

}