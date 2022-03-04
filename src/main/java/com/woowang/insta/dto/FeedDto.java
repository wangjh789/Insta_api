package com.woowang.insta.dto;
import com.woowang.insta.domain.Feed;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FeedDto {
	private Long id;
	private String imagePath;
	private String description;
	private MemberDto member;
	private Long commentCount;
	private Long likeFeedCount;
	private List<CommentDto> comments = new ArrayList<>();

	public FeedDto(Feed feed){
		this.id = feed.getId();
		this.imagePath = feed.getImagePath();
		this.description = feed.getDescription();
		this.member = new MemberDto(feed.getMember());
	}
}
