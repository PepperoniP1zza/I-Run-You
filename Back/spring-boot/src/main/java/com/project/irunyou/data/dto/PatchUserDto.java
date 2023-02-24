
package com.project.irunyou.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchUserDto {
	private String userEmail;
	private String userAddress;
	private String userAddressDetail;
	private String userPhoneNumber;
}
