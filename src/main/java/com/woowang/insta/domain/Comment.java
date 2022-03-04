package com.woowang.insta.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	@Id @GeneratedValue
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String description;
	private LocalDateTime createdAt;

	//==연관관계 메서드==//
	public void setMember(Member member){
		this.member = member;
		member.getComments().add(this);
	}
	public void setFeed(Feed feed){
		this.feed = feed;
		feed.getComments().add(this);
	}

	//==생성자 메서드==//
	public static Comment createComment(Feed feed,Member member,String description){
		Comment comment = new Comment();

		comment.setMember(member);
		comment.setFeed(feed);

		comment.description = description;
		comment.createdAt = LocalDateTime.now();
		return comment;
	}

}