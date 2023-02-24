package com.project.irunyou.data.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.irunyou.data.dto.LoginTokenDto;
import com.project.irunyou.data.dto.LoginUserDto;
import com.project.irunyou.data.dto.PostUserDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.entity.UserEntity;
import com.project.irunyou.data.repository.UserRepository;
import com.project.irunyou.security.TokenProvider;

@Service
public class AuthService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private TokenProvider tokenProvider;
	
	// 2023-01-25 홍지혜
	// 유저의 이메일과 비밀번호로 유저 엔티티 생성 -> rawPassword / encodedPassword 같은지 확인
	public UserEntity getByCredentials(String email, String password, PasswordEncoder encoder) {
		/*
		 * BCryp- 인코더는 같은 값을 인코딩하더라도 할 떄마다 값이 다름 -> 의미 없는 값 랜덤 Salt -> Salting
		 * 유저에게 받은 패스워드를 인코딩해도 데이터베이스에 저장한 패스워드와는 다를 확률이 높음
		 * 전용 일치 여부 메서드 matches() : Salt고려 두 값 비교
		 */
		UserEntity originalUser = userRepository.findByUserEmail(email);	// 이메일로 유저 정보 찾음 (이때 유저의 패스워드는 암호화된 패스워드임)
		if(originalUser != null && encoder.matches(password, originalUser.getUserPassword())) {	
			return originalUser;	// 해당 이메일을 가진 유저가 존재하고, 해당 유저의 암호화된 패스워드와 입력된 패스워드가 일치하면 해당 유저 반환
		}
		return null;
	}
	
	// 홍지혜 2023-01-30
	// 회원가입 로직 수정
	public ResponseDto<?> signUpUser(PostUserDto dto) {

		boolean checkUserEmailDupe = userRepository.existsByUserEmail(dto.getUserEmail());
		// 이메일 중복여부 체크
		if (checkUserEmailDupe) {
			return ResponseDto.setFailed(String.format("'%s'는 이미 가입된 이메일 입니다.", dto.getUserEmail()));
		}

		boolean checkUserPhoneNumberDupe = userRepository.existsByUserPhoneNumber(dto.getUserPhoneNumber());
//		// 휴대폰번호 중복여부 체크
//		if (checkUserPhoneNumberDupe) {
//			return ResponseDto.setFailed(String.format("'%s'는 이미 가입된 휴대폰 번호 입니다.", dto.getUserPhoneNumber()));
//		}

		String password = dto.getUserPassword();
		String password2 = dto.getUserPassword2();

		// 비밀번호 일치여부 확인
		if (!password.equals(password2)) {
			return ResponseDto.setFailed("비밀번호를 다시 확인해주세요");
		}

		// 가입 시 전화번호 " - " 삭제
		String userPhone = dto.getUserPhoneNumber().replace("-", "");

		// 비밀번호 암호화
		String encryptedPassword = passwordEncoder.encode(password);

		UserEntity user = UserEntity.builder()
				.userName(dto.getUserName())	// 유저 이름
				.userEmail(dto.getUserEmail())	// 유저 이메일
				.userPassword(encryptedPassword) // 암호화된 비밀번호로 저장
				.postNumber(dto.getPostNumber())
				.userAddress(dto.getUserAddress())	
				.userAddressDetail(dto.getUserAddressDetail())	// 유저의 주소 정보
				.userPhoneNumber(userPhone) // 하이픈 제외한 휴대전화번호 저장
				.userNickname(dto.getUserNickname()) // 닉네임
				.userJoinDate(LocalDate.now()) 	// 가입날짜
				.build();

		userRepository.save(user);

		return ResponseDto.setSuccess("회원가입에 성공했습니다.", new ResultResponseDto(true));
	}
	
	
	// 로그인
	public ResponseDto<?> LoginUser(LoginUserDto dto) {
		
		UserEntity user = getByCredentials(dto.getUserEmail(), dto.getUserPassword(), passwordEncoder);
		
		if (user == null) {	// 해당 유저 정보 없음
			return ResponseDto.setFailed("해당하는 유저정보가 없습니다. 이메일과 비밀번호를 확인해 주세요.");
		} else { // 유저정보가 존재함
			String token = tokenProvider.create(user); 	// 토큰 생성
			long expiration = tokenProvider.GetExpiration(token);	// 토큰 유효기간
			
			LoginTokenDto tokenResponse = LoginTokenDto.builder()	
					.token(token)
					.expiration(expiration)
					.userNickname(user.getUserNickname())	// 유저 닉네임 반환
					.build();	// LoginTokenDto에 토큰과 토큰 유효기간 담아 response
			
			return ResponseDto.setSuccess("로그인 되었습니다.", tokenResponse);
		}

	}
	

	
	
}
