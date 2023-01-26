/* 
 * 업데이트 작성자 : 홍지혜
 * 업데이트 날짜 : 2023-01-20 
 * 업데이트 내용 : parkEntity 매개변수 생성자 추가
 * */
package com.project.irunyou.data.dto;

import com.project.irunyou.data.entity.ParkEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkInfoDto {

		@NotNull
    	private int parkIndex;
	    @NotNull
	    private String name;
	    @NotNull
	    private String address;
	    @NotNull
	    private double latitude;
	    @NotNull
	    private double longitude;
	    @NotNull
	    private int area;
	    
	    public ParkInfoDto(ParkEntity parkEntity) {
	    	this.parkIndex = parkEntity.getParkIndex();
	    	this.name = parkEntity.getParkName();
	    	this.address = parkEntity.getParkAddress();
	    	this.latitude = parkEntity.getParkLatitude();
	    	this.longitude = parkEntity.getParkLongitude();
	    	this.area = parkEntity.getParkArea();
	    }
	    
}
