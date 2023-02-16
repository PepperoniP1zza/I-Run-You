package com.project.irunyou.data.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.irunyou.data.dto.CommentDto;
import com.project.irunyou.data.dto.CommentIndexDto;
import com.project.irunyou.data.dto.CommentLikeDto;
import com.project.irunyou.data.dto.CommentResponseDto;
import com.project.irunyou.data.dto.PatchCommentDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.entity.CommentEntity;
import com.project.irunyou.data.entity.CommentLikeEntity;
import com.project.irunyou.data.repository.CommentLikeRepository;
import com.project.irunyou.data.repository.CommentRepository;
import com.project.irunyou.data.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentService {

	@Autowired CommentRepository commentRepository;
	@Autowired UserRepository userRepository;
	@Autowired CommentLikeRepository commentLikeRepository;
	
	// 댓글 리스트 불러오는 메서드
	public List<CommentResponseDto> getCommentResponseDtoList(int schIdx) {
		
		List<CommentEntity> commentEntityList;	
		List<CommentResponseDto> commentListResult = new ArrayList<>();
		
		try {
			
			commentEntityList = commentRepository.findAllByCommentScheduleIndex(schIdx);
			
			for(CommentEntity c : commentEntityList) {
				commentListResult.add(CommentResponseDto.builder()
						.commentIndex(c.getCommentIndex())
						.commentScheduleIndex(c.getCommentScheduleIndex())
						.commentWriter(userRepository.findUserNicknameByUserEmail(c.getCommentWriter()))
						.commentContent(c.getCommentContent())
						.commentDatetime(c.getCommentDatetime())
						.commentLike(commentLikeRepository.countByCommentIndex(c.getCommentIndex()))
						.build());
			}
						
		} catch(Exception e) {
			return commentListResult = null;
		}

		return commentListResult;
	}
	
	
	
	// 2023-02-12 수정 : 홍지혜
	//	댓글 리스트 불러오는 메서드
	public ResponseDto<List<CommentResponseDto>> getCommentList (int schIndx) {
		
		List<CommentResponseDto> data;
		
		try {
			
			data = getCommentResponseDtoList(schIndx);
						
		} catch(Exception e) {
			return ResponseDto.setFailed("데이터베이스 조회 실패");
		}
//		for (int i = 0; i < commentEntityList.size(); i++) {
//			commentListResult.add(new CommentResponseDto(commentEntityList.get(i)));
//		}
		return ResponseDto.setSuccess("댓글 조회에 성공했습니다.", data);
	}
	
	// 댓글 작성후 댓글 리스트 반환
	public ResponseDto<List<CommentResponseDto>> registComment(String email, CommentDto dto) {
		
		List<CommentResponseDto> data;
		
		try {
			
		CommentEntity comment;
		
//		CommentResponseDto result;
		
//	 	0131 result 빌더 -> 생성자로 대체 - 황석민
		
//		result = CommentResponseDto
//				.builder()
//				.commentIndex(0)
//				.commentScheduleIndex(0)
//				.commentWriterIndex(dto.getCommentWriterIndex())
//				.commentContent(dto.getCommentContent())
//				.commentDatetime(timestamp)
//				.build();
		
		
		if(dto.getCommentContent().isEmpty()) {	// 내용이 비어있지 않은지 먼저 검토
			return ResponseDto.setFailed("내용을 입력하세요.");
		}
		
		comment = CommentEntity
				.builder()
//				.commentIndex(0)	// 인덱스는 autoIncreament이므로 넣을 필요 없음
				.commentScheduleIndex(dto.getCommentScheduleIndex())
				.commentWriter(email)	// 유저 이메일
				.commentContent(dto.getCommentContent())
				.commentDatetime(LocalDateTime.now())	// 현재 시간
				.build();
		
//		result = new CommentResponseDto(comment);		
		
		commentRepository.save(comment);
		
		data = getCommentResponseDtoList(dto.getCommentScheduleIndex());
		
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseDto.setFailed("댓글 등록 중 오류가 발생했습니다.");
		}
		
		return ResponseDto.setSuccess("댓글이 등록되었습니다.", data);
	}
	
	// 2023-02-12 로직 수정 홍지혜
	// 댓글 삭제
	public ResponseDto<List<CommentResponseDto>> deleteComment(String email,int cmtIdx, int schIdx) {
		
		CommentEntity comment;
		List<CommentResponseDto> data;		
		try {
			comment = commentRepository.findById(cmtIdx).get(); // 댓글 인덱스로 댓글 엔티티 가져옴

//		int comIdx = comment.getCommentIndex();
//		int schIdx = comment.getCommentScheduleIndex();
//		String writerUser = comment.getCommentWriter();
//		
//		int delcomIdx = dto.getCommentIndex();
//		int delschIdx = dto.getCommentScheduleIndex();
//		String delwriterUser = dto.getCommentWriter();

			if (!email.equals(comment.getCommentWriter())) { // 댓글의 유저 이메일과 삭제 요청한 유저의 이메일이 동일한지 확인
				return ResponseDto.setFailed("자신이 작성한 댓글만 삭제 가능합니다.");
			}

//		if((comIdx == delcomIdx) & (schIdx == delschIdx) & (writerUser == delwriterUser)) {
//			commentRepository.deleteById(delcomIdx);
//		} else {
//			return ResponseDto.setFailed("작성자, 댓글번호, 일정번호를 확인하세요");
//		}
			commentRepository.delete(comment); // 해당 엔티티 제거
			
			data = getCommentResponseDtoList(schIdx);
			

		}catch (Exception e) {
			return ResponseDto.setFailed("Error");
		}
		
		
		return ResponseDto.setSuccess("댓글이 삭제되었습니다",data);
	}
	
	
	// 2023-02-12 홍지혜
	// 댓글 수정
	public ResponseDto<ResultResponseDto> modifyComment(String email,PatchCommentDto dto) {
		
		try {
			CommentEntity comment = commentRepository.findById(dto.getCommentIndex()).get(); // 댓글 인덱스로 댓글 엔티티 가져옴

			if (!email.equals(comment.getCommentWriter())) { // 댓글의 유저 이메일과 수정 요청한 유저의 이메일이 동일한지 확인
				return ResponseDto.setFailed("자신이 작성한 댓글만 수정 가능합니다.");
			}

			comment.setCommentContent(dto.getCommentContent()); // request받은 데이터로 댓글 내용 수정

			commentRepository.save(comment); // 저장
		} catch (Exception e) {
			return ResponseDto.setFailed("modifyComment Error");
		}
		
		return ResponseDto.setSuccess("수정이 완료되었습니다.", new ResultResponseDto(true));
		
	}
	
	// 2023-02-14 최예정
	// 사용자 한 명이 좋아요 누르면 1개의 좋아요만 올라가거나 내려감
	public ResponseDto<CommentLikeDto> commentLike (String email, CommentIndexDto dto) {
		
		CommentLikeEntity checkLike = commentLikeRepository.findByCommentIndexAndUserEmail(dto.getCommentIndex(), email);
		String message = "";
		// 성공과 실패의 메세지를 messge라는 변수명을 선언해 그 안에 메세지를 한꺼번에 출력할 수 있게 반환해줌
		
		try {
			
			if (checkLike != null) {
//				commentLikeRepository.deleteByCommentIndexAndUserEmail(dto.getCommentIndex(), email);
				// delete는 Dto를 선언해서 하는 것이 아닌
				commentLikeRepository.delete(checkLike);
				// delete할 변수를 안에 넣어줄 수 있음
				message = "cancel liked";
			} else {
				CommentLikeEntity commentLike = new CommentLikeEntity(0, email, dto.getCommentIndex());
				commentLikeRepository.save(commentLike);
				message = "push liked";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.setFailed("db error");
		}
		
		// 중복된 리턴된 값들을 위에서 선언하는 것이 아닌 밑으로 빼서 리턴 해줌
		int result = commentLikeRepository.countByCommentIndex(dto.getCommentIndex());
		return ResponseDto.setSuccess(message, new CommentLikeDto(result));
	}
	public ResponseDto<List<CommentLikeDto>> getLike(List<CommentIndexDto> dto) {
		List<CommentLikeDto> result = new ArrayList<>();
		try {
			for (int i = 0; i < dto.size(); i++) {
				int likeNumber = commentLikeRepository.countByCommentIndex(dto.get(i).getCommentIndex());
				result.add(new CommentLikeDto(likeNumber));
			}
		} catch (Exception e) {
			return ResponseDto.setFailed("Database Error");
		}
		return ResponseDto.setSuccess("Successfully loaded data", result);
	}
}
