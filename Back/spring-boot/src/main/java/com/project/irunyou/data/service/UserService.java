/* 작성자 : 문경원
 * 파일의 역할 : UserSerivce함수 담기
 * 작성날짜 : 2023-01-12
 * 
 * 업데이트 작성자 : 황석민
 * 업데이트 날짜 : 2023-01-15
 * 업데이트 내용 : signUp, read, deleteUser 메소드 업데이트
 * */

package com.project.irunyou.data.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import com.project.irunyou.data.dto.FindPasswordDto;
import com.project.irunyou.data.dto.GetUserResponseDto;
import com.project.irunyou.data.dto.PatchUserDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.ResultResponseDto;
import com.project.irunyou.data.dto.UserNicknameDto;
import com.project.irunyou.data.dto.UserPhoneAndNameDto;
import com.project.irunyou.data.dto.UserRequestDto;
import com.project.irunyou.data.entity.CodeEntity;
import com.project.irunyou.data.entity.UserEntity;
import com.project.irunyou.data.repository.CodeRepository;
import com.project.irunyou.data.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CodeRepository codeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ResgisterMailService mailService;

	@Autowired
	private AuthService authService;

	// 회원 정보조회, email, pw확인후 pw제외 정보제공
	public ResponseDto<GetUserResponseDto> readUser(String email) {
		UserEntity user = findByEmail(email);
		if (user == null)
			return ResponseDto.setFailed("Not Exist User");

		return ResponseDto.setSuccess("Get User Success", new GetUserResponseDto(user));
	}

	// 회원 정보수정
	public ResponseDto<GetUserResponseDto> updateUser(PatchUserDto dto) {
		// email, pw확인후 정보수정가능하게
		String email = dto.getUserEmail();

		UserEntity user = findByEmail(email);
		if (user == null)
			return ResponseDto.setFailed("Not Exist User");

		user.setUserAddress(dto.getUserAddress());
		user.setUserAddressDetail(dto.getUserAddressDetail());
		user.setUserPhoneNumber(dto.getUserPhoneNumber());

		userRepository.save(user);

		return ResponseDto.setSuccess("정보수정이 완료 되었습니다.", new GetUserResponseDto(user));
	}

	// 회원 탈퇴
	public ResponseDto<ResultResponseDto> deleteUser(String email, String password) {
		// 비밀번호로 회원 검증
		UserEntity user = authService.getByCredentials(email, password, passwordEncoder);

		if (user == null) {
			return ResponseDto.setFailed("회원정보가 일치하지 않습니다.");
		}

		userRepository.delete(user);

		return ResponseDto.setSuccess("탈퇴되었습니다.", new ResultResponseDto(true));
	}

	// id찾기
	public ResponseDto<UserRequestDto> findUserId(UserPhoneAndNameDto dto) {

		String phone = dto.getUserPhoneNumber();
		String name = dto.getUserName();

		String userPhone = phone.replace("-", "");

		if (!StringUtils.hasText(userPhone) || !StringUtils.hasText(name)) {
			return ResponseDto.setFailed("입력한 정보를 다시 확인하세요");
		}
		UserEntity user = userRepository.findByUserPhoneNumberAndUserName(phone, name);
		if (user == null)
			return ResponseDto.setFailed("가입된 정보가 없습니다.");
		
		// 최예정, 문경원 2023-02-03
		// 이메일 @ 앞 3자리 숨기기(* 처리)
		String userEmail = user.getUserEmail();
		int hiddenId =  userEmail.indexOf("@");
		
		// hiddenId 앞에 있는 아이디
		String IdExceptEmail = userEmail.substring(0, hiddenId-2);
	
		// @ 앞에 있는 세 자리 * 표시
		String star = "***";
		
		String encryptedId = IdExceptEmail.concat(star);
		
		// hiddenId 뒤에 있는 메일 주소
		String emailEnd = userEmail.substring(hiddenId, userEmail.length());
		
		// 세 자리 숨긴 아이디와 이메일 주소 붙여서 출력
		String encryptedEmail = encryptedId.concat(emailEnd);
				
		return ResponseDto.setSuccess("회원님의 email 입니다.", new UserRequestDto(encryptedEmail));
	}

	

	// 홍지혜 2023-02-07 정규표현식으로 이메일 포맷 확인 추가 
	// 홍지혜 2023-02-02 로직수정 : 입력창이 빈값인지 먼저 검증
	// 최예정 2023-02-01
	// 아이디(이메일) 중복 체크
	public ResponseDto<ResultResponseDto> checkId(UserRequestDto data) {
		
		String email = data.getUserEmail();
		String regex = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
		
		if(email.isEmpty()) {
			return ResponseDto.setFailed("이메일을 입력해 주세요.");
		}
		
		boolean regexCheck = Pattern.matches(regex, email);
		
		if(!regexCheck) {
			return ResponseDto.setFailed("이메일 형식이 올바르지 않습니다.");
		}
		
		boolean checkUserEmailDupe = userRepository.existsByUserEmail(email);

		if (checkUserEmailDupe) {
			return ResponseDto.setFailed(String.format("'%s'는 이미 가입된 이메일 입니다.", email));
		}
		return ResponseDto.setSuccess("사용가능한 이메일 입니다.", null);
	}
	
	// 홍지헤 2023-02-02 로직수정 : 입력창이 빈값인지 먼저 검증
	// 최예정 2023-02-02
	// 닉네임 중복 체크
	public ResponseDto<ResultResponseDto> checkNickname(UserNicknameDto data) {
		
		String nickname = data.getUserNickname();
		
		if(nickname.isEmpty()) {
			return ResponseDto.setFailed("닉네임을 입력해 주세요.");
		}

		log.debug(nickname);
		boolean checkUserNicknameDupe = userRepository.existsByUserNickname(nickname);

		if (checkUserNicknameDupe) {
			return ResponseDto.setFailed(String.format("'%s'는 이미 가입된 닉네임 입니다.", nickname));
		}
		return ResponseDto.setSuccess("사용가능한 닉네임 입니다.", null);
	}

	// pw찾기 0126 황석민
	// 2023-02-07 홍지혜 로직 변경 - 임시비밀번호 발급 후 해당 비밀번호로 유저 비밀번호 변경
	// 2023-02-06 홍지혜 : 로직 변경 - 유효한 request인지 먼저 확인 
	// pw 찾기 : 회원 이름과 이메일 입력 -> 임시 비밀번호 발급
	public ResponseDto<ResultResponseDto> findPw(FindPasswordDto dto) {
//		// 전화번호 하고 이메일 입력 검증
//		String email = dto.getUserEmail();
//		String phoneNumber = dto.getUserPhoneNumber();
		String email = dto.getUserEmail();
		String code = "";
		
		// 넘어온 값이 있는지 먼저 검증
		if(email.isEmpty()) {
			return ResponseDto.setFailed("입력정보가 존재하지 않습니다.");
		}
		// 이메일 DB 검증
		log.info(email);
		
		UserEntity user = userRepository.findByUserEmail(email);
		
		if(user == null) {
			return ResponseDto.setFailed("해당 유저정보가 존재하지 않습니다.");
		}
//		// 둘중 하나라도 정상이 아니면 ResponseDto Failed 반환
//		if (!StringUtils.hasText(email) || !StringUtils.hasText(phoneNumber))
//			return ResponseDto.setFailed("빈값입니다.");

//		// 데이터베이스에서 해당 이메일과 전화번호를 조건으로 검색
//		UserEntity userEntity = userRepository.findByUserEmailAndUserPhoneNumber(email, phoneNumber);
//		// 존재하지 않으면 존재하지 않는 다는 ResponseDto 반환
//		if (userEntity == null)
//			return ResponseDto.setFailed("입력정보가 존재하지 않습니다.");

		// sendMail 하고 결과로 받은 code를 데이터베이스에 저장
		try {
			code = mailService.sendMail(email);
			// TODO : 보낸 코드를 데이터베이스에 저장
			// 1. Code Entity 생성 (email, code 기준으로)
			CodeEntity codeEtt = new CodeEntity(code, email);
			// 2. 생성된 Entity를 CodeRepository에 save
			codeRepository.save(codeEtt);
		} catch (Exception exception) {
			return ResponseDto.setFailed("코드 전송 또는 저장에 실패했습니다.");
		}
		
		try {
			// 임시비밀번호 인코딩 후 유저 정보에서 비밀번호 수정후 저장
			String EncodingTemporaryPassword = passwordEncoder.encode(code);
			
			user.setUserPassword(EncodingTemporaryPassword);
			userRepository.save(user);
			
		} catch(Exception e) {
			return ResponseDto.setFailed("오류가 발생했습니다.");
		}
		
		// 성공하면 ResponseDto Successed 반환
		return ResponseDto.setSuccess("메일 전송에 성공했습니다.", new ResultResponseDto(true));
	}

	// user엔터티 내에 이메일찾는 메서드
	private UserEntity findByEmail(String email) {
		UserEntity user;
		try {
			user = userRepository.findByUserEmail(email);
		} catch (Exception e) {
			return null;
		}
		return user;
	}

	// user엔터티 내에 전화번호찾는 메서드
	// Phone_num 언더바 인식 못하므로 나중에 수정 요망(해결완료)
	private UserEntity findByPhoneNum(String userPhone) {

		UserEntity user;
		try {
			user = userRepository.findByUserPhoneNumber(userPhone);
		} catch (Exception e) {
			return null;
		}
		return user;
	}

}
