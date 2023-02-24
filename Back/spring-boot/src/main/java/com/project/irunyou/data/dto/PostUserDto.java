
package com.project.irunyou.data.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUserDto {
	@NotNull
	private String userEmail;
	@NotNull
	private String userPassword;
	@NotNull
	private String userPassword2;
	@NotNull
	private String userAddress;
	@NotNull
	private String userAddressDetail;
	@NotNull
	private String userPhoneNumber;
	@NotNull
	private String userName;
	@NotNull
	private String userNickname;
	@NotNull
	private String postNumber;
	
}
