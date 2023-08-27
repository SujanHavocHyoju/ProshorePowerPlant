package com.proshore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proshore.model.Battery;
import com.proshore.repository.BatteryRepository;

@Service
public class BatteryService {

	@Autowired
	private BatteryRepository batteryRepository;
	
	public List<Battery> getAllBatteries() {
		List<Battery> batteryList = batteryRepository.findAll();
		return batteryList;
	}
	
	public String addBatteries(List<Battery> batteryList) {
		batteryRepository.saveAll(batteryList);
		return "Inserted " + batteryList.size() + " battery records into the database";
	}
	
	public List<Battery> findBatteryByPostcodeRange(String from, String to){
		return batteryRepository.findByPostcodeBetweenOrderByName(from, to);
	}
}
