package com.woowang.insta.dto;

import com.woowang.insta.controller.FeedController;
import com.woowang.insta.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto{
	private Long id;
	private MemberDto member;
	private String description;
	public CommentDto(Comment comment){
		this.id = comment.getId();
		this.member = new MemberDto(comment.getMember());
		this.description = comment.getDescription();
	}
}
