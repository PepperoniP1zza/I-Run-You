/* 작성자 : 홍지혜
 * 파일의 역할 : 유저 일정 정보 response DTO 
 * 작성날짜 : 2023-01-17
 * 
 * 업데이트 작성자 :
 * 업데이트 날짜 : 
 * 업데이트 내용 :
 * */
package com.project.irunyou.data.dto;

import java.time.LocalDateTime;

import com.project.irunyou.data.entity.RunScheduleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRunScheduleDto {
	
	private int runScheduleIndex;
	private String runSchedulePark;
	private int parkIndex;
	private String runScheduleWriter;
	private String runScheduleTitle;
	private LocalDateTime runScheduleDatetime;
	private String runScheduleContent;
	
	
}
