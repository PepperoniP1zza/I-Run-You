// 2023-01-26 홍지혜
package com.project.irunyou.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.irunyou.data.entity.UserAddressDetailEntity;

@Repository
public interface UserAddressDetailRepository extends JpaRepository<UserAddressDetailEntity,Integer>{

}
