package com.proshore.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proshore.TestUtils;
import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;

/* 
 * Indicates that this is a Spring Boot test 
 * and loads the BatteryMapper class into the application context.
 */
@SpringBootTest(classes = BatteryMapper.class)
class BatteryMapperTest extends TestUtils{
	@Autowired
	private  BatteryMapper batteryMapper;

	@Test
	void should_map_dto_to_entity() {
		BatteryDTO newDto = generateBatteryDTO();
		Battery mappedBattery = batteryMapper.toEntity(newDto);
		assertEquals(newDto.getName(), mappedBattery.getName());
	}
	
	@Test
	void should_map_entity_to_dto() {
		Battery newBattery = generateBattery();
		BatteryDTO mappedBatteryDto = batteryMapper.toDto(newBattery);
		assertEquals(newBattery.getPostcode(), mappedBatteryDto.getPostcode());
	}

}
