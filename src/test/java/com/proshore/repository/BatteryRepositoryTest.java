package com.proshore.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.proshore.TestUtils;
import com.proshore.model.Battery;

@DataJpaTest
class BatteryRepositoryTest extends TestUtils{

	@Autowired
	private BatteryRepository repository;
	
	List<Battery> insertDummyData() {
		List<Battery> batteryList = generateBatteryList();
		return repository.saveAll(batteryList);
	}
	
	@Test
	void saveAll_should_insert_entire_battery_list() {
		List<Battery> storedBatteries = insertDummyData();
		assertNotNull(storedBatteries);
		assertTrue(storedBatteries.size() > 0);
	}
	
	@Test
	void save_should_insert_new_battery() {
		Battery newBattery = generateBattery();
		Battery storedBattery = repository.save(newBattery);
		assertNotNull(storedBattery);
		assertEquals("HighWatt", storedBattery.getName());
	}
	
	@Test
	void findAll_should_return_battery_list() {
		insertDummyData();
		List<Battery> batteryList = repository.findAll();
		assertEquals(3, batteryList.size());
	}
	
	@Test
	void findByPostcodeRange_should_filter_battery_list() {
		insertDummyData();
		List<Battery> batteryList = repository.findByPostcodeBetweenOrderByName("1500","2100");
		assertEquals(2, batteryList.size());
	}
	
	@Test
	void findByName_should_return_battery() {
		insertDummyData();
		Optional<Battery> battery = repository.findByName("Duracell");
		assertTrue(battery.isPresent());
	}

}
