package com.woowang.insta.service;

import com.woowang.insta.domain.Member;
import com.woowang.insta.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	MemberService memberService;

	@Test
	public void 회원가입(){
		Long memberId = memberService.join("email1", "password1", "nickname1");

		Member member = memberRepository.findById(memberId).get();

		assertEquals(member.getId(),memberId);
		assertEquals(member.getNickname(),"nickname1");
	}

	@Test
	public void 회원가입_닉네임_중복(){
		Long member1 = memberService.join("email1", "password1", "nickname1");
		try{
			Long member2 = memberService.join("email2", "password1", "nickname1");
		}catch (Exception e){
			return ;
		}
		fail("회원가입 시 이미 존재하는 닉네임을 경우 예외발생");
	}

	@Test
	public void 회원가입_이메일_중복(){
		Long member1 = memberService.join("email1", "password1", "nickname1");
		try{
			Long member2 = memberService.join("email1", "password1", "nickname2");
		}catch (Exception e){
			return ;
		}
		fail("회원가입 시 이미 존재하는 닉네임을 경우 예외발생");
	}

}