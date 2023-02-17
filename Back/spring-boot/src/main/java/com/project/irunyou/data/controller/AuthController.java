package com.project.irunyou.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.irunyou.data.dto.FindPasswordDto;
import com.project.irunyou.data.dto.LoginUserDto;
import com.project.irunyou.data.dto.PostUserDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.dto.UserNicknameDto;
import com.project.irunyou.data.dto.UserPhoneAndNameDto;
import com.project.irunyou.data.dto.UserRequestDto;
import com.project.irunyou.data.entity.UserEntity;
import com.project.irunyou.data.service.AuthService;
import com.project.irunyou.data.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(originPatterns = "http://localhost:3000/")
@RestController
@RequestMapping("auth/")
public class AuthController {
	
	@Autowired private AuthService authService;
	@Autowired private UserService userService;
	
	@ApiOperation(value="회원가입",
			notes="유저에게 정보(이름,닉네임,아이디(이메일),비밀번호,비밀번확인,주소,휴대전화번호)를 받아 회원가입을 진행한다.")
	@PostMapping("signup")
	public ResponseDto<?> signUpUser (@RequestBody PostUserDto requestBody) {
		return authService.signUpUser(requestBody);
	}
	
	@ApiOperation(value="로그인",
			notes="유저에게 아이디(이메일),비밀번호를 받아 로그인을 진행한다.")
	@PostMapping("login")
	public ResponseDto<?> LoginUser(@RequestBody LoginUserDto requestBody) {
		return authService.LoginUser(requestBody);
	}
	
	
	@ApiOperation(value="아이디 찾기",
			notes="유저에게 이름과 휴대폰번호를 받아 유저의 이메일을 일부만 돌려준다.")
	@PostMapping("findemail")
	public ResponseDto<UserRequestDto> findUserId(@RequestBody UserPhoneAndNameDto requestBody) {
		return userService.findUserId(requestBody);
	}

	@ApiOperation(value="아이디(이메일) 중복 체크",
			notes="유저가 회원가입시 입력한 이메일이 이미 존재하는 이메일인지 확인한다.")
	@PostMapping("checkId")
	public ResponseDto<ResultResponseDto> checkId(@RequestBody UserRequestDto requestBody) {
		return userService.checkId(requestBody);
	}

	@ApiOperation(value="닉네임 중복 체크",
			notes="유저가 회원가입시 입력한 닉네임이 이미 존재하는 이메일인지 확인한다.")
	@PostMapping("checkNickname")
	public ResponseDto<ResultResponseDto> checkNickname(@RequestBody UserNicknameDto requsetBody) {
		return userService.checkNickname(requsetBody);
	}

	@ApiOperation(value="비밀번호 찾기",
			notes="유저의 이메일을 받아 랜덤한 8자리 코드의 임시 비밀번호를 유저의 이메일로 전송한다.")
	@PostMapping("findPw")
	public ResponseDto<ResultResponseDto> findPw(@RequestBody FindPasswordDto requestBody) {
		return userService.findPw(requestBody);
	}
	
}
