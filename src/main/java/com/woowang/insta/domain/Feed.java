package com.woowang.insta.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

	@Id @GeneratedValue
	@Column(name = "feed_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "feed",cascade = CascadeType.ALL)
	private List<LikeFeed> likeFeeds = new ArrayList<>();

	@Column(nullable = false)
	private String imagePath;
	private String description;
	private String tags;
	private LocalDateTime createdAt;

	//==연관관계 메소드==//
	public void setMember(Member member){
		this.member = member;
		member.getFeeds().add(this);
	}

	//==생성 메소드==//
	public static Feed createFeed(Member member, String imagePath, String description, String tags){
		Feed feed = new Feed();
		feed.setMember(member); //양뱡향이므로 연관관계 메소드로
		feed.imagePath = imagePath;
		feed.description = description;
		feed.tags = tags;
		feed.createdAt = LocalDateTime.now();
		return feed;
	}

	//==biz ==//
	public Long editFeed(String imagePath,String description,String tags){
		this.imagePath = imagePath;
		this.description = description;
		this.tags = tags;
		return getId();
	}
	public void deleteFeed(){
		this.member.getFeeds().remove(this);
//		this.comments.clear();
//		this.likeFeeds.clear();
		//영속성 전이를 위해 참고하기 때문에 지우면 안됨
	}
}