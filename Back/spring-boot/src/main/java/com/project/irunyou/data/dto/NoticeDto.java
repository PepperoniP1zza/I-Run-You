
package com.project.irunyou.data.dto;

import javax.validation.constraints.NotNull;

import com.project.irunyou.data.entity.NoticeBoardEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDto {
	
	@NotNull
	private int noticeIndex;
	@NotNull
	private String noticeTitle;
	@NotNull
	private String noticeContent;
	
	public NoticeDto(NoticeBoardEntity noticeEntity) {
		this.noticeIndex = noticeEntity.getNoticeIndex();
		this.noticeTitle = noticeEntity.getNoticeTitle();
		this.noticeContent = noticeEntity.getNoticeContent();
	}
	
}
