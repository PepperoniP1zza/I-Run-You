/* 작성자 : 문경원
 * 파일의 역할 : 댓글 작성 컨트롤러 클래스
 * 작성날짜 : 2023-01-16
 * 
 * */
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

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(originPatterns = "http://localhost:3000")
@Slf4j
@RestController
@RequestMapping("irunyou/comment/")
public class CommentController {

	@Autowired CommentService commentService;
	
	// Read (댓글 불러오기)
	// http://localhost:4040/irunyou/comment&schIdx=?
	@GetMapping("")
	public ResponseDto<List<CommentResponseDto>> getCommentList (@RequestParam int schIdx) {	// RequestParam 쓰실경우 파라미터 이름 알기쉽게 정해야 합니다
		return commentService.getCommentList(schIdx);
	}
	
	// Create (댓글작성)
	@PutMapping("")
	public ResponseDto<List<CommentResponseDto>> registComment (@AuthenticationPrincipal String email, @RequestBody CommentDto requestBody){
		return commentService.registComment(email, requestBody);
	}
	
	// Delete (댓글삭제)
	@DeleteMapping("")
	public ResponseDto<List<CommentResponseDto>> deleteComment (@AuthenticationPrincipal String email, @RequestParam int cmtIdx, int schIdx){
		return commentService.deleteComment(email,cmtIdx, schIdx);
	}
	
	// 2023-02-12 홍지혜
	// 댓글 수정
	@PatchMapping("")
	public ResponseDto<ResultResponseDto> modifyComment(String email,PatchCommentDto dto) {
		return commentService.modifyComment(email, dto);
	}
	
	// 2023-02-14 최예정
	// 사용자 한 명이 좋아요 누르면 1개의 좋아요만 올라가거나 내려감
	@PostMapping("")
	public ResponseDto<CommentLikeDto> commentLike (@AuthenticationPrincipal String email, @RequestBody CommentIndexDto requestBody) {
		return commentService.commentLike(email, requestBody);
	}
	
}
