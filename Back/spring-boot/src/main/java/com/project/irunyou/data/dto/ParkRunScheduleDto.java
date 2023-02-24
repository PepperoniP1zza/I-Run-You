package com.project.irunyou.data.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.irunyou.data.entity.RunScheduleEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ParkRunScheduleDto {
		@NotNull
		private int runScheduleIndex;
		@NotNull
		private int runSchedulePark;
		@NotNull
		private String runScheduleTitle;
		@NotNull
		private String runScheduleWriter;	// 작성자 닉네임
		@NotNull
		private LocalDateTime runScheduleDatetime;
	    @NotNull
	    private String runScheduleContent;
	    
}
