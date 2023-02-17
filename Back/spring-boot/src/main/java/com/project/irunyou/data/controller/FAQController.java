
package com.project.irunyou.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.InquiryDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.service.FAQService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("irunyou/FAQ/")
public class FAQController {
	
	@Autowired FAQService faqService;
	
	@ApiOperation(value="문의사항 등록하기",
			notes="유저는 문의유형을 택해 문의사항을 등록할 수 있다.")
	@PostMapping("")
	public ResponseDto<ResultResponseDto> inquiryFAQ (@RequestBody InquiryDto requestBody) {
		return faqService.inquiryFAQ(requestBody);
	}

}
