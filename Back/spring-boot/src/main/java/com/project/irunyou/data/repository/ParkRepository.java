/* 작성자 : 홍지혜
 * 파일의 역할 : 공원정보 Repository Class
 * 작성날짜 : 2023-01-12
 * 
 * 업데이트 작성자 : -
 * 업데이트 날짜 : -
 * */
package com.project.irunyou.data.repository;

import com.project.irunyou.data.entity.ParkEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ParkRepository extends JpaRepository<ParkEntity, Integer> {

//    @Query("select park_idx from park p where p.name = ?1")
//    public Integer findParkName(String parkName);
	
	// 위도, 경도를 보내면 제일 가까운 공원 5개의 ParkEntity를 가져옴
	@Query(nativeQuery=true,value="select \r\n"
			+ "	*,\r\n"
			+ "	(6371 * acos( cos( radians(?1) ) * cos( radians( p.latitude ) ) * cos( radians(?2) - radians(p.longitude) ) + sin( radians(?1) ) * sin( radians( p.latitude ) ) ) ) as distance\r\n"
			+ "from park as p\r\n"
			+ "order by distance  limit 5")
	public List<ParkEntity> findAllByDistance(double latitude,double longitude);
	

}
