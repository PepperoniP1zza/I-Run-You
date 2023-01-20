/* 
 * 업데이트 작성자 : 홍지혜
 * 업데이트 날짜 : 2023-01-19
 * 업데이트 내용 : 사용자 위치 기반 가장 가까운 공원 5개 respose service
 * */
package com.project.irunyou.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.irunyou.data.dto.MyLocationDto;
import com.project.irunyou.data.dto.ParkInfoDto;
import com.project.irunyou.data.dto.ParkListDto;
import com.project.irunyou.data.dto.ResponseDto;
import com.project.irunyou.data.dto.UserLocationDto;
import com.project.irunyou.data.entity.ParkEntity;
import com.project.irunyou.data.repository.ParkRepository;


@Service
public class ParkService {
	
	@Autowired ParkRepository parkRepository;
	
	public ResponseDto<ParkInfoDto> searchParkById (Integer parkNum) {
		
		ParkEntity park = findById(parkNum);
		if (park == null) {
			return ResponseDto.setFailed("망했어요");
		}
	
		
		ParkInfoDto result;
		result = ParkInfoDto.builder()
				.address(park.getAddress())
				.area(park.getArea())
				.latitude(park.getLatitude())
				.longitude(park.getLongitude())
				.name(park.getName())
				.build();
		
		return ResponseDto.setSuccess("Load Success", result);
		
	}
	
	public ParkEntity findById(Integer parkNum) {
		ParkEntity park;
		try {
			park = parkRepository.findById(parkNum).get();
		} catch (Exception e) {
			return null;
		}
		return park;
	}
		
	// 가까운 공원 5개 가져오기
	// request 값 위도, 경도
	public ResponseDto<List<ParkInfoDto>> findClosePark(UserLocationDto dto) {
		List<ParkInfoDto> ClosePark = new ArrayList<>();
		try {
			List<ParkEntity> CloseDistancePark = parkRepository.findAllByDistance(dto.getLatitude(),
					dto.getLongitude());
			for (ParkEntity p : CloseDistancePark) {
				ClosePark.add(new ParkInfoDto(p));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseDto.setSuccess("Load Success", ClosePark);
	}

// 	public ResponseDto<List<ParkListDto>> searchParkByLocation (MyLocationDto dto) {
		
// 		ParkEntity parkentity;
		
// 		List<ParkEntity> parkL = parkRepository.findAll();
// 		List<ParkEntity> parkList = parkL.subList(0, 9);
		
// 		List<Double> distanceList = new ArrayList<Double>();
// //		double[] distanceList = new double[parkList.size()];
// 		List<ParkListDto> data = new ArrayList<ParkListDto>();
		
// 		double lat = dto.getLatitude();
// 		double lng = dto.getLongitude();
// //		distanceList에 거리 담는 반복문
// 		for (int i = 0 ; i<parkList.size() ; i++) {
// 			ParkEntity park = parkList.get(i);
			
// 			double parkLat = park.getLatitude();
// 			double parkLng = park.getLongitude();
			
// 			double dx = Math.abs(lat - parkLat);
// 			double dy = Math.abs(lng - parkLng);
			
// 			double dist = Math.sqrt(dx*dx + dy*dy);
			
// 			distanceList.add(dist);
// 		}
		
// //		data에 거리순으로 parkdto 넣기
// 		data.add(new ParkListDto(parkList.get(0), distanceList.get(0)));
// 		for (int i = 1; i < parkList.size(); i++) {
// 			ParkListDto park = new ParkListDto(parkList.get(i), distanceList.get(i));
// 			for (int j = 0; j < data.size(); j++) {
// 				if (park.getDistance() < data.get(j).getDistance()) {
// 					data.add(j, park);
// 					break;
// 				}
// 				if (j == data.size()-1) {
// 					data.add(park);
// 					break;
// 				}
// 			}
// 		}
// 		return ResponseDto.setSuccess("공원정보 불러오기에 성공했습니다.", data);
// 	}	
}
		
//		for (ParkEntity park : parkList) {
//			for(int j = 0; j < distanceList.size(); j++) {
//				data.add(j, ParkListDto.builder()
//							.address(park.getAddress())
//							.distance(distanceList.get(j))
//							.park_idx(park.getPark_idx())
//							.name(park.getName())
//							.build());
			
		
		
		
		
//		Math.pow(3, 2); = 3^2
//		Math.sqrt(a);
		
		
//		List<ParkListDto> datalist = new ArrayList<ParkListDto>();
//		
//		for (ParkEntity park : parkList) {
//			datalist.add(new ParkListDto(park));
//		}
				
	
//	public void distSort(List<ParkListDto> arr, ParkEntity park) {
//		
//		for (int i = 0; i < arr.size()-1; i++) {
//			
//			if ()
//			ParkListDto result; 
//			
//			arr.add(result);
//		}
//	}
