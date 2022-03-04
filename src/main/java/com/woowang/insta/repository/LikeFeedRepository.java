package com.woowang.insta.repository;

import com.woowang.insta.domain.Feed;
import com.woowang.insta.domain.LikeFeed;
import com.woowang.insta.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeFeedRepository extends JpaRepository<LikeFeed,Long> {
	List<LikeFeed> findByMemberAndFeed(Member member, Feed feed);
}
