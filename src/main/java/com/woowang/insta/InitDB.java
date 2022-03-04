package com.woowang.insta;

import com.woowang.insta.service.CommentService;
import com.woowang.insta.service.FeedService;
import com.woowang.insta.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitDB {
	private final InitService initService;

	@PostConstruct
	public void init(){
		initService.dbInit1();
	}


	@Transactional
	@Component
	@RequiredArgsConstructor
	static class InitService{
		private final MemberService memberService;
		private final FeedService feedService;
		private final CommentService commentService;

		public void dbInit1(){
			Long member1Id = memberService.join("member1","member1","member1");
			Long feed1Id = feedService.write(member1Id,"image","desc1",null);
			Long comment1_1 = commentService.write(member1Id,feed1Id,"comment1");

			Long member2Id = memberService.join("member2","member2","member2");
			Long feed2Id = feedService.write(member2Id,"image2","desc2",null);
			Long comment1_2 = commentService.write(member2Id,feed1Id,"comment2");
			Long comment2_1 = commentService.write(member2Id,feed2Id,"comment1");

			Long member3Id = memberService.join("member3","member3","member3");
			Long feed3Id = feedService.write(member3Id,"image3","desc3",null);
			Long comment1_3 = commentService.write(member3Id,feed1Id,"comment3");
			Long comment1_4 = commentService.write(member3Id,feed1Id,"comment4");
			Long comment1_5 = commentService.write(member3Id,feed1Id,"comment5");

			Long member4Id = memberService.join("member4","member4","member4");
			Long comment1_6 = commentService.write(member4Id,feed1Id,"comment6");

			feedService.likeFeed(member1Id,feed1Id);
			feedService.likeFeed(member2Id,feed1Id);
			feedService.likeFeed(member3Id,feed1Id);


		}

	}
}
