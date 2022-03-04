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
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Column(unique = true)
	private String email;
	private String password;

	@Column(unique = true)
	private String nickname;

	@OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
	private List<Feed> feeds = new ArrayList<>();

	@OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "member")
	private List<LikeFeed> likeFeeds = new ArrayList<>();

	private LocalDateTime createdAt;

	//== 생성 메서드==//
	public static Member createMember(String email,String password,String nickname){
		Member member = new Member();
		member.email = email;
		member.password = password;
		member.nickname = nickname;
		member.createdAt = LocalDateTime.now();

		return member;
	}

	//==biz==//
	public Long editNickname(String nickname){
		this.nickname = nickname;
		return getId();
	}



}
