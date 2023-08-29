package com.proshore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proshore.controller.BatteryController;
import com.proshore.model.Battery;
import com.proshore.repository.BatteryRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BatteryService {

	@Autowired
	private BatteryRepository batteryRepository;
	
	/**
	 * Finds all battery entities from repository
	 * @return batterylist
	 */
	public List<Battery> getAllBatteries() {
		List<Battery> batteryList = batteryRepository.findAll();
		log.info("Total batteries found: ", batteryList.size());
		return batteryList;
	}
	
	/**
	 * Saves a {@link Battery} entity object
	 * @param {@link Battery} entity object
	 * @return saved battery
	 */
	public Battery addBattery(Battery battery) {
		log.info("Saving battery");
		return batteryRepository.save(battery);
	}
	
	/**
	 * Saves all {@link Battery} entities from the batterylist
	 * @param batteryList
	 * @return String
	 */
	public String addBatteries(List<Battery> batteryList) {
		log.info("Saving {} batteries", batteryList.size());
		batteryRepository.saveAll(batteryList);
		return "Inserted " + batteryList.size() + " battery records into the database";
	}
	
	/**
	 * Finds a {@link Battery} entity object using name
	 * @param name String value
	 * @return found battery
	 */
	public Optional<Battery> findByName(String name) {
		log.info("Searching for battery with name {}", name);
		return batteryRepository.findByName(name);
	}
	
	/**
	 * Finds multiple {@link Battery} entities using postcode range
	 * @param from String value
	 * @param to String value
	 * @return found battery list
	 */
	public List<Battery> findBatteryByPostcodeRange(String from, String to) {
		log.info("Searching for batteries with postcode range from {} to {}", from, to);
		return batteryRepository.findByPostcodeBetweenOrderByName(from, to);
	}
}
