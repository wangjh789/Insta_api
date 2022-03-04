package com.woowang.insta.service;

import com.woowang.insta.domain.Member;
import com.woowang.insta.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
	private final MemberRepository memberRepository;

	/**
	 * 회원가입
	 * email, nickname 중복확인
	 */
	@Transactional
	public Long join(String email,String password,String nickname){
		validateDuplicateNickname(nickname);
		validateDuplicateEmail(email);
		Member member = Member.createMember(email,password,nickname);
		memberRepository.save(member);
		return member.getId();
	}

	/**
	 * 닉네임 수정
	 */
	@Transactional
	public Long editNickname(Long memberId,String nickname){
		Member member = memberRepository.findById(memberId).get();
		validateDuplicateNickname(nickname);
		member.editNickname(nickname);
		return member.getId();
	}

	/**
	 * 회원 탈퇴
	 */
	@Transactional
	public void deleteMember(Long memberId){
		Member member = memberRepository.findById(memberId).get();
		memberRepository.delete(member);
	}

	private void validateDuplicateEmail(String email){
		if(!memberRepository.findByEmail(email).isEmpty()){
			throw new IllegalStateException("이미 존재하는 이메일 입니다.");
		}
	}
	private void validateDuplicateNickname(String nickname){
		if(!memberRepository.findByNickname(nickname).isEmpty()){
			throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
		}
	}
}
