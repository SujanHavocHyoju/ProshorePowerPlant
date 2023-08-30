package com.proshore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proshore.model.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long>{
	/**
	 * Uses native SQL query that queries for battery records casts String from and
	 * to values into integer and orders results by name in ascending order
	 * 
	 * @param from String value
	 * @param to   String value
	 * @return list of battery entity
	 */
	@Query(value = 
			"SELECT * FROM Battery WHERE CAST(postcode AS INTEGER) BETWEEN CAST(:from AS INTEGER) AND CAST(:to AS INTEGER) ORDER BY name ASC", 
			nativeQuery = true)
	List<Battery> findByPostcodeBetweenOrderByName(String from, String to);

	/**
	 * Finds battery using name value
	 * @param name String value
	 * @return optional of battery
	 */
	Optional<Battery> findByName(String name);

}
