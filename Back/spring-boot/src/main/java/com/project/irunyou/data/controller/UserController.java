
package com.project.irunyou.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.DeleteUserPasswordDto;
import com.project.irunyou.data.dto.FindPasswordDto;
import com.project.irunyou.data.dto.GetUserResponseDto;
import com.project.irunyou.data.dto.PatchUserDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.dto.UserNicknameDto;
import com.project.irunyou.data.dto.UserPhoneAndNameDto;
import com.project.irunyou.data.dto.UserRequestDto;
import com.project.irunyou.data.service.ResgisterMailService;
import com.project.irunyou.data.service.UserService;

import io.swagger.annotations.ApiOperation;

// 로그인이 되어있는 경우 (JWT 토큰을 가지고 있는 상태)

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("irunyou/")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	ResgisterMailService mailService;

	// Read (회원정보 읽기)
	@ApiOperation(value="유저의 마이페이지 정보",
			notes="유저는 마이페이지에서 자신의 정보를 확인할 수 있다.")
	@GetMapping("mypage")
	public ResponseDto<GetUserResponseDto> readUser(@AuthenticationPrincipal String email) { // 로그인 되어있는 상태! -> 마이페이지
		return userService.readUser(email);
	}

	// Update (회원정보 수정)
	@ApiOperation(value="회원정보 수정하기",
			notes="유저는 자신의 회원정보를 수정할 수 있다. 비밀번호로 검증 후, 이름, 아이디(이메일)을 제외한 정보를 수정할 수 있다.")
	@PatchMapping("patchuser")
	public ResponseDto<GetUserResponseDto> updateUser(@RequestBody PatchUserDto requestBody) {
		return userService.updateUser(requestBody);
	}

	// 홍지혜
	// Delete (회원탈퇴)
	@ApiOperation(value="탈퇴하기",
			notes="로그인 되어있는 유저는 비밀번호 검증 후, 탈퇴를 진행할 수 있다. 탈퇴시 유저정보는 삭제된다.")
	@PostMapping("dropuser") // deleteMapping의 경우 RequestBody를 받지 않기 때문에 Post로 처리함.
	public ResponseDto<ResultResponseDto> deleteUser(@AuthenticationPrincipal String email,
			@RequestBody DeleteUserPasswordDto dto) {
		// password의 경우 프론트에서 json형태가 아닌 text로 처리해야 합니다!
		return userService.deleteUser(email,dto.getUserPassword());
	}



}
