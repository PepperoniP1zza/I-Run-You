
package com.project.irunyou.data.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.FindRunScheduleDto;
import com.project.irunyou.data.dto.GetUserRunScheduleDto;
import com.project.irunyou.data.dto.PatchScheduleDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.dto.RunScheduleDto;
import com.project.irunyou.data.service.RunScheduleService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/irunyou/runschedule/")
public class RunScheduleController {

	@Autowired RunScheduleService scheduleService;
	
	//유저가 직접 일정 생성
	@ApiOperation(value="RUN일정 생성하기",
			notes="유저는 직접 RUN일정을 생성할 수 있다.")
	@PostMapping("create")
	public ResponseDto<ResultResponseDto> registSchedule(@AuthenticationPrincipal String writer, @RequestBody RunScheduleDto requestBody) {
		return scheduleService.registSchedule(writer,requestBody);
	}
	

	@ApiOperation(value="RUN일정 수정하기",
			notes="유저는 자신의 생성한 RUN일정을 수정할 수 있다.")
	@PatchMapping("modify")
	ResponseDto<ResultResponseDto> patchSchedule(@RequestBody PatchScheduleDto requestBody) {
		return scheduleService.patchSchedule(requestBody);
	}
	
	// 유저가 직접 생성한 일정 삭제
	@ApiOperation(value="RUN일정 삭제하기",
			notes="유저는 자신이 생성한 RUN일정을 삭제할 수 있다.")
	@PatchMapping("delete")
	public ResponseDto<ResultResponseDto> deleteSchedule(@RequestBody FindRunScheduleDto requestBody) {
		return scheduleService.deleteSchedule(requestBody);
	}
	
	
	// 유저가 기존에 존재하는 일정에 참여
	@ApiOperation(value="RUN일정 참여하기",
			notes="유저는 타 유저가 생성한 RUN일정에 참여할 수 있다.")
	@PostMapping("participate")
	public ResponseDto<ResultResponseDto> participateRunSchedule(@AuthenticationPrincipal String user, @RequestBody FindRunScheduleDto requestBody) {
		return scheduleService.participateRunSchedule(user, requestBody);
	}
	
	// 일정 참여 취소
	@ApiOperation(value="RUN일정 참여 취소하기",
			notes="유저는 참여한 RUN일정을 취소할 수 있다.")
	@PatchMapping("cancel")
	public ResponseDto<ResultResponseDto> cancelRunSchedule(@AuthenticationPrincipal String user, @RequestBody FindRunScheduleDto requestBody) {
		return scheduleService.cancelRunSchedule(user, requestBody);
	}
	
	//R (일정조회(유저가 직접 만든 일정, 참여하는 일정 모두)
	@ApiOperation(value="RUN일정 조회하기",
			notes="유저는 마이페이지에서 자신이 만든 RUN일정과, 자신이 참여한 RUN일정을 조회할 수 있다.")
	@GetMapping("list")
	public ResponseDto<Map<String,List<GetUserRunScheduleDto>>> readSchedule(@AuthenticationPrincipal String userEmail) {
		return scheduleService.readSchedule(userEmail);
	}
	
	// 로그인한 유저의 일정 참여 여부
	@ApiOperation(value="유저의 RUN일정 참여 여부",
			notes="유저가 현재 보고있는 RUN일정에 참여중인지를 response한다.")
	@GetMapping("isParticipate")
	public ResponseDto<ResultResponseDto> isParticipate(@AuthenticationPrincipal String userEmail, @RequestParam int schIdx) {
		return scheduleService.getIsParticipate(userEmail, schIdx);
	}

}
	
	

