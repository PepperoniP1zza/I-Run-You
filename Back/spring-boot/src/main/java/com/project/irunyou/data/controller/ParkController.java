/* 
 * 업데이트 작성자 : 홍지혜
 * 업데이트 날짜 : 사용자 위치 기반 가장 가까운 공원 5개 respose 기능
 * */
package com.project.irunyou.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.MyLocationDto;
import com.project.irunyou.data.dto.ParkInfoDto;
import com.project.irunyou.data.dto.ParkListDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.UserLocationDto;
import com.project.irunyou.data.service.ParkService;

import antlr.collections.List;

@RestController
@RequestMapping("irunyou/park/")
public class ParkController {
	
	@Autowired ParkService parkService;
	
	@PostMapping("{parkNum}")
	public ResponseDto<ParkInfoDto> searchParkById(@PathVariable("parkNum") int parkNum){
		return parkService.searchParkById(parkNum);
	}

}
