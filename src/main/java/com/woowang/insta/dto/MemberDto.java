package com.woowang.insta.dto;


import com.woowang.insta.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto{
	private Long id;
	private String email;
	private String nickname;

	public MemberDto(Member member){
		this.id = member.getId();
		this.email = member.getEmail();
		this.nickname = member.getNickname();
	}
}
