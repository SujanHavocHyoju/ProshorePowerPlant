package com.proshore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.proshore.TestUtils;
import com.proshore.model.Battery;
import com.proshore.repository.BatteryRepository;

@ExtendWith(MockitoExtension.class)
class BatteryServiceTest extends TestUtils{
	
	@Mock
	private BatteryRepository batteryRepository;
	
	@InjectMocks
	private BatteryService batteryService;
	
	@Test
	void findAll_should_return_battery_list() {
		List<Battery> batteryList = generateBatteryList();
		when(batteryRepository.findAll()).thenReturn(batteryList);
		List<Battery> list = batteryService.getAllBatteries();
		assertEquals(batteryList.size(), list.size());
		verify(batteryRepository).findAll();
	}
	
	@Test
	void save_should_insert_new_battery() {
		Battery newBattery = generateBattery();
		batteryService.addBattery(newBattery);
		verify(batteryRepository).save(newBattery);
	}
	
	@Test 
	void save_should_insert_all_batteries(){
		List<Battery> batteryList = generateBatteryList();
		batteryService.addBatteries(batteryList);
		verify(batteryRepository).saveAll(batteryList);
	}
	
	
	@Test
	void findByName_should_return_battery() {
		Battery newBattery = generateBattery();
		when(batteryRepository.findByName("HighWatt")).thenReturn(Optional.of(newBattery));
		Optional<Battery> returnedBattery = batteryService.findByName("HighWatt");
		assertEquals(newBattery.getName(), returnedBattery.get().getName());
		verify(batteryRepository).findByName("HighWatt");
	}
	
	@Test
	void findByPostcodeRange_should_filter_battery_list() {
		List<Battery> batteryList = generateBatteryList();
		List<Battery> filteredList = batteryList.stream()
										.filter( b -> {
											try {
								                int postcode = Integer.parseInt(b.getPostcode());
								                return postcode > 1500 && postcode < 2100;
								            } catch (NumberFormatException e) {
								                return false;
								            }
										})
										.collect(Collectors.toList());
		when(batteryRepository.findByPostcodeBetweenOrderByName("1500", "2100"))
			.thenReturn(filteredList);
		List<Battery> list = batteryService.findBatteryByPostcodeRange("1500", "2100");
		assertEquals(list.size(), filteredList.size());
		verify(batteryRepository).findByPostcodeBetweenOrderByName("1500", "2100");
	}

}
