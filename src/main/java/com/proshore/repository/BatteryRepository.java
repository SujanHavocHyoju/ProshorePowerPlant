package com.proshore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proshore.model.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long>{
	//List<Battery> findByPostcodeBetweenOrderByName(String from, String to);
	
	@Query(value = 
			"SELECT * FROM Battery WHERE CAST(postcode AS INTEGER) BETWEEN CAST(:from AS INTEGER) AND CAST(:to AS INTEGER) ORDER BY name ASC", 
			nativeQuery = true)
	List<Battery> findByPostcodeBetweenOrderByName(String from, String to);

	Optional<Battery> findByName(String name);

}
