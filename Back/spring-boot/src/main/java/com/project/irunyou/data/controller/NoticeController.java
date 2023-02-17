package com.project.irunyou.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.NoticeDto;
import com.project.irunyou.data.dto.PageResponseDto;
import com.project.irunyou.data.dto.PageResponseDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("irunyou/notice")
public class NoticeController {

	@Autowired NoticeService noticeService;

	@ApiOperation(value="공지사항 등록하기",
			notes="관리자는 공지사항을 작성한 후 등록할 수 있다.")
	@PostMapping("")
	public ResponseDto<ResultResponseDto> createNotice(@RequestBody NoticeDto requestBody) {
		return noticeService.createNotice(requestBody);
	}

	@ApiOperation(value="공지사항 수정하기",
			notes="관리자는 이미 존재하는 공지사항을 수정할 수 있다.")
	@PatchMapping("")
	public ResponseDto<NoticeDto> updateNotice(@RequestBody NoticeDto requestBody) {
		return noticeService.updateNotice(requestBody);
	}

	@ApiOperation(value="공지사항 삭제하기",
			notes="관리자는 공지사항을 삭제할 수 있다.")
	@DeleteMapping("")
	public ResponseDto<ResultResponseDto> deleteNotice(@RequestParam("noticeIndex")int noticeIndex) {
		return noticeService.deleteNotice(noticeIndex);
	}
	
	// 공지사항 목록 페이징처리 해서 불러오기
	//?page=()
	@ApiOperation(value="공지사항 목록 불러오기",
			notes="유저는 공지사항 페이지에 접속하면 공지사항 목록을 확인할 수 있다. 공지사항 목록은 페이지네이션처리되어 출력된다.")
	@GetMapping("")
	public ResponseDto<PageResponseDto<NoticeDto>> getNoticeList(@RequestParam int page) {
		return noticeService.getNoticeList(page,6);	// 한 페이지당 6개 씩 뜨게 고정
	}
	
}
