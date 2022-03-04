package com.woowang.insta.repository;

import com.woowang.insta.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
	List<Member> findByEmail(String email);

	List<Member> findByNickname(String nickname);


}
