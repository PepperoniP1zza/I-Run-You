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

//로그인이 되어있지 않은 경우 (JWT 토큰이 없는 경우)

// 2023-01-25 홍지혜
// 2023-01-27 login controller로직 -> 서비스에서 처리
@CrossOrigin(originPatterns = "http://localhost:3000/")
@RestController
@RequestMapping("auth/")
public class AuthController {
	
	@Autowired private AuthService authService;
	@Autowired private UserService userService;
	
	// 회원가입
	@PostMapping("signup")
	public ResponseDto<?> signUpUser (@RequestBody PostUserDto requestBody) {
		return authService.signUpUser(requestBody);
	}
	
	// 로그인
	@PostMapping("login")
	public ResponseDto<?> LoginUser(@RequestBody LoginUserDto requestBody) {
		return authService.LoginUser(requestBody);
	}
	
	
	// 홍지혜 2023-02-02 회원가입 과정 검증 기능 userController -> AuthController로 이동
	// 토큰 검증 때문에 auth/ 경로 외엔 로그인없이 접근 불가능하기 때문
	
	// 최예정 2023-02-02
	// id 찾기
	@PostMapping("findemail")
	public ResponseDto<UserRequestDto> findUserId(@RequestBody UserPhoneAndNameDto requestBody) {
		return userService.findUserId(requestBody);
	}

	// 최예정 2023-02-01
	// 아이디(이메일) 중복 체크
	@PostMapping("checkId")
	public ResponseDto<ResultResponseDto> checkId(@RequestBody UserRequestDto requestBody) {
		return userService.checkId(requestBody);
	}

	// 최예정 2023-02-02
	// 닉네임 중복 체크
	@PostMapping("checkNickname")
	public ResponseDto<ResultResponseDto> checkNickname(@RequestBody UserNicknameDto requsetBody) {
		return userService.checkNickname(requsetBody);
	}

	// pw찾기
	// request method가 Post이고 end point는 findPw
	@PostMapping("findPw")
	public ResponseDto<ResultResponseDto> findPw(@RequestBody FindPasswordDto requestBody) {
		// 비즈니스 로직에 대한 결과 반환
		return userService.findPw(requestBody);
	}
	
}
