package com.proshore.service;

import java.util.List;
import java.util.Optional;

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
	
	public Battery addBattery(Battery battery) {
		return batteryRepository.save(battery);
	}
	
	public String addBatteries(List<Battery> batteryList) {
		batteryRepository.saveAll(batteryList);
		return "Inserted " + batteryList.size() + " battery records into the database";
	}
	
	public Optional<Battery> findByName(String name){
		return batteryRepository.findByName(name);
	}
	
	public List<Battery> findBatteryByPostcodeRange(String from, String to){
		return batteryRepository.findByPostcodeBetweenOrderByName(from, to);
	}
}
