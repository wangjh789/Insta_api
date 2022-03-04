package com.woowang.insta.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeFeed {

	@Id @GeneratedValue
	@Column(name = "like_feed_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	private LocalDateTime createdAt;

	//==연관관계 메서드==//
	public void setMember(Member member){
		this.member = member;
		member.getLikeFeeds().add(this);
	}
	public void setFeed(Feed feed){
		this.feed = feed;
		feed.getLikeFeeds().add(this);
	}

	//==생성 메서드==//
	public static LikeFeed createLikeFeed(Member member,Feed feed){
		LikeFeed likeFeed = new LikeFeed();

		likeFeed.setMember(member);
		likeFeed.setFeed(feed);

		likeFeed.createdAt = LocalDateTime.now();
		return likeFeed;
	}

	//==biz ==//
	public void cancel(){
		this.member.getLikeFeeds().remove(this);
		this.feed.getLikeFeeds().remove(this);
	}
}
