
package com.project.irunyou.data.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.PageResponseDto;
import com.project.irunyou.data.dto.ParkInfoDto;
import com.project.irunyou.data.dto.ParkRunScheduleDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.RunScheduleDto;
import com.project.irunyou.data.dto.SliceResponseDto;
import com.project.irunyou.data.dto.UserLocationDto;
import com.project.irunyou.data.entity.RunScheduleEntity;
import com.project.irunyou.data.service.ParkService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("irunyou/park/")
public class ParkController {

	@Autowired ParkService parkService;
	
//	@GetMapping("")
//	public ResponseDto<ParkInfoDto> searchParkById (@RequestParam Integer parkNum) {
//		return parkService.searchParkById(parkNum);
//	}
	
	// 사용자 위치기반 가까운 공원 5개
	@ApiOperation(value="사용자의 위치기반 가까운 공원 5개 불러오기",
			notes="유저의 경도와 위도정보를 받아, 유저의 위치를 기반으로 가장 가까운 공원 5개의 정보를 Response한다.")
	@PostMapping("")
	public ResponseDto<List<ParkInfoDto>> findClosePark(@RequestBody UserLocationDto dto) {
		return parkService.findClosePark(dto);
	}
	
	// 공원에 등록된 런스케쥴 리스트 가져오기 (페이지네이션 처리)
	@ApiOperation(value="공원에 등록된 RUN일정 불러오기",
			notes="유저는 공원 세부정보 페이지에서 공원에 잡힌 RUN일정을 확인할 수 있다. RUN일정정보는 Slice처리되어 Resposne된다.")
	@GetMapping("runSchedule")
	public ResponseDto<SliceResponseDto<ParkRunScheduleDto>> getParkRunScheduleList(@RequestParam int parkIndex, int page) {
		return parkService.getParkRunScheduleList(page, 5, parkIndex);	// 사이즈 5로 고정
	}
	
	
}
