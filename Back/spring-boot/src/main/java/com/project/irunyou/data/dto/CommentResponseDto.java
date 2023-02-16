package com.project.irunyou.data.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.project.irunyou.data.entity.CommentEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommentResponseDto {
	
	@NotNull
    private int commentIndex;
    @NotNull
    private int commentScheduleIndex;
    @NotNull
    private String commentWriter;	// 데이터를 넘겨주는 dto에는 유저의 닉네임으로 들어갑니다
    @NotNull
    private String commentContent;

    private LocalDateTime commentDatetime;
    @NotNull
    private int commentLike;
    
// 	0131 commentService 빌더 -> 생성자로 대체 - 황석민
    public CommentResponseDto(CommentEntity comment) {
    	this.commentIndex = comment.getCommentIndex();
    	this.commentScheduleIndex = comment.getCommentIndex();
//    	this.commentWriterIndex = comment.getCommentWriterIndex();
    	this.commentContent = comment.getCommentContent();
    }
    
}
