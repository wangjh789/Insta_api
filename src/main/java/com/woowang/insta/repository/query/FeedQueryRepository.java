package com.woowang.insta.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowang.insta.domain.*;
import com.woowang.insta.dto.MemberDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FeedQueryRepository {

	private final EntityManager em;

	public List<Tuple> getFeeds(){ //피드 + 작성자 + 댓글 수
		QFeed feed = QFeed.feed;
		QComment comment = QComment.comment;
		QMember member = QMember.member;
		QLikeFeed likeFeed = QLikeFeed.likeFeed;
		JPAQueryFactory query = new JPAQueryFactory(em);

		List<Tuple> fetch = query
				.select(feed, comment.count(),likeFeed.count())
				.from(feed)
				.join(feed.member, member)
				.fetchJoin()
				.leftJoin(comment)
				.on(feed.id.eq(comment.feed.id))
				.leftJoin(likeFeed)
				.on(feed.id.eq(likeFeed.feed.id))
				.groupBy(feed.id)
				.fetch();
		return fetch;
	}

	public Tuple getFeed(Long id){
		QFeed feed = QFeed.feed;
		QComment comment = QComment.comment;
		QMember member = QMember.member;
		QLikeFeed likeFeed = QLikeFeed.likeFeed;
		JPAQueryFactory query = new JPAQueryFactory(em);

		Tuple tuple = query.select(feed,likeFeed.count())
				.from(feed)
				.where(feed.id.eq(id))
				.join(feed.member, member)
				.fetchJoin()
				.join(feed.comments,comment)
				.join(feed.likeFeeds,likeFeed)
				.fetchOne();
		return tuple;
	}

	public List<Comment> getComments(Long id,int offset,int limit) {
		QComment comment = QComment.comment;
		QMember member = QMember.member;
		JPAQueryFactory query = new JPAQueryFactory(em);
		return query.select(comment)
				.from(comment)
				.where(comment.feed.id.eq(id))
				.join(comment.member, member)
				.fetchJoin()
				.offset(offset)
				.limit(limit)
				.fetch();
	}

	public List<Member> getMembersLikeFeed(Long id,int offset,int limit) {
		QMember member = QMember.member;
		QLikeFeed likeFeed = QLikeFeed.likeFeed;
		JPAQueryFactory query = new JPAQueryFactory(em);

		List<Member> fetch = query.select(member)
				.from(member)
				.join(member.likeFeeds, likeFeed)
				.on(likeFeed.feed.id.eq(id))
				.offset(offset)
				.limit(limit)
				.fetch();

		return fetch;
	}
}
