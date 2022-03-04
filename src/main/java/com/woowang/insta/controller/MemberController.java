package com.woowang.insta.controller;

import com.woowang.insta.repository.MemberRepository;
import com.woowang.insta.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;

	/**
	 * 회원가입
	 */
	@PostMapping("/member")
	public MemberResponse saveMember(@RequestBody CreateMemberRequest request){
		Long id = memberService.join(request.getEmail(), request.getPassword(), request.getNickname());
		return new MemberResponse(id);
	}

	/**
	 * 닉네임 수정
	 */
	@PutMapping("/member/{id}")
	public MemberResponse editMember(@RequestBody UpdateMemberRequest request){
		Long editId = memberService.editNickname(request.getId(), request.getNickname());
		return new MemberResponse(editId);
	}

	/**
	 * 회원탈퇴
	 */
	@DeleteMapping("/member/{id}")
	public MemberResponse deleteMember(@PathVariable("id")Long id){
		memberService.deleteMember(id);
		return new MemberResponse((long) -1);
	}

	@Data
	static class CreateMemberRequest{
		private String email;
		private String password;
		private String nickname;
	}

	@Data
	static class MemberResponse {
		private Long id;

		public MemberResponse(Long id) {
			this.id = id;
		}
	}

	@Data
	static class UpdateMemberRequest{
		private Long id;
		private String nickname;
	}

}
