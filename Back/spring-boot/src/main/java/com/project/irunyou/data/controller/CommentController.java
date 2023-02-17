
package com.project.irunyou.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.CommentDto;
import com.project.irunyou.data.dto.CommentIndexDto;
import com.project.irunyou.data.dto.CommentLikeDto;
import com.project.irunyou.data.dto.CommentResponseDto;
import com.project.irunyou.data.dto.PatchCommentDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.service.CommentService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("irunyou/comment/")
public class CommentController {

	@Autowired CommentService commentService;
	
	@ApiOperation(value="댓글 목록 불러오기",
			notes="해당하는 RUN일정에 달린 모든 댓글을 불러온다.")
	@GetMapping("")
	public ResponseDto<List<CommentResponseDto>> getCommentList (@RequestParam int schIdx) {	// RequestParam 쓰실경우 파라미터 이름 알기쉽게 정해야 합니다
		return commentService.getCommentList(schIdx);
	}
	
	@ApiOperation(value="댓글 작성하기",
			notes="유저는 RUN일정에 댓글을 달 수 있다.")
	@PutMapping("")
	public ResponseDto<List<CommentResponseDto>> registComment (@AuthenticationPrincipal String email, @RequestBody CommentDto requestBody){
		return commentService.registComment(email, requestBody);
	}
	
	@ApiOperation(value="댓글 삭제하기",
			notes="유저는 자신이 작성한 댓글만 삭제할 수 있다.")
	@DeleteMapping("")
	public ResponseDto<List<CommentResponseDto>> deleteComment (@AuthenticationPrincipal String email, @RequestParam int cmtIdx, int schIdx){
		return commentService.deleteComment(email,cmtIdx, schIdx);
	}
	
	@ApiOperation(value="댓글 수정하기",
			notes="유저는 자신이 작성한 댓글을 수정할 수 있다.")
	@PatchMapping("")
	public ResponseDto<ResultResponseDto> modifyComment(String email,PatchCommentDto dto) {
		return commentService.modifyComment(email, dto);
	}
	
	@ApiOperation(value="댓글 좋아요",
			notes="유저는 마음에 드는 댓글에 좋아요를 누르고, 취소할 수 있다.")
	@PostMapping("")
	public ResponseDto<CommentLikeDto> commentLike (@AuthenticationPrincipal String email, @RequestBody CommentIndexDto requestBody) {
		return commentService.commentLike(email, requestBody);
	}
	
}
