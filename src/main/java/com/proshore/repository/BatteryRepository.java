package com.proshore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proshore.model.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long>{
	List<Battery> findByPostcodeBetweenOrderByName(String from, String to);
}
