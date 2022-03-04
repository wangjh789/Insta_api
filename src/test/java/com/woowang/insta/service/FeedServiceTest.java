package com.woowang.insta.service;

import com.woowang.insta.domain.Feed;
import com.woowang.insta.domain.LikeFeed;
import com.woowang.insta.domain.Member;
import com.woowang.insta.repository.FeedRepository;
import com.woowang.insta.repository.LikeFeedRepository;
import com.woowang.insta.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FeedServiceTest {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	FeedService feedService;
	@Autowired
	FeedRepository feedRepository;
	@Autowired
	LikeFeedRepository likeFeedRepository;

	@Test
	public void 피드_작성(){
		Long memberId = memberService.join("email1","password","nickname");
		Long feedId = feedService.write(memberId,"imagePath1","desc",null);
		Feed feed = feedRepository.findById(feedId).get();

		assertEquals(feedId,feed.getId());
		assertEquals(memberId,feed.getMember().getId());
	}

	@Test
	public void 좋아요(){
		Long memberId = memberService.join("email1","password","nickname");
		Member member = memberRepository.findById(memberId).get();
		Long feedId = feedService.write(memberId,"imagePath1","desc",null);
		Feed feed = feedRepository.findById(feedId).get();

		feedService.likeFeed(memberId,feedId);

		LikeFeed likeFeed = likeFeedRepository.findByMemberAndFeed(member,feed).get(0);

		assertEquals(member.getLikeFeeds().get(0),likeFeed);
		assertEquals(feed.getLikeFeeds().get(0),likeFeed);
	}

	@Test
	public void 좋아요_취소(){
		Long memberId = memberService.join("email1","password","nickname");
		Member member = memberRepository.findById(memberId).get();
		Long feedId = feedService.write(memberId,"imagePath1","desc",null);
		Feed feed = feedRepository.findById(feedId).get();

		feedService.likeFeed(memberId,feedId); //좋아요
		LikeFeed likeFeed = likeFeedRepository.findByMemberAndFeed(member,feed).get(0);

		feedService.likeFeed(memberId,feedId); //좋아요 취소

		assertEquals(member.getLikeFeeds().size(),0);
		assertEquals(feed.getLikeFeeds().size(),0);
	}

}