package com.proshore.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;
import com.proshore.service.BatteryService;
import com.proshore.util.BatteryMapper;



@RestController
@RequestMapping("/api/v1/batteries")
public class BatteryController {
	
	@Autowired
	private BatteryService batteryService;
	
	@Autowired
	private BatteryMapper batteryMapper;
	
    @GetMapping
    public List<BatteryDTO> getAllPersons() {
    	 return batteryService.getAllBatteries().stream()
				.map(batteryItem -> {
					return batteryMapper.toDto(batteryItem);
				})
				.collect(Collectors.toList());
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addBatteries(@RequestBody List<BatteryDTO> batteryDtoList) {
    	//batteryRepository.saveAll(batteryList);
    	List<Battery> batteryList = batteryDtoList.stream()
    				.map(batteryDto -> {
    					return batteryMapper.toEntity(batteryDto);
    				})
    				.collect(Collectors.toList());
    	batteryService.addBatteries(batteryList);
    	return "Inserted " + batteryList.size() + " battery records into the database";
    }
    
    @GetMapping("/search")
    public Map<String, Object> getBatteries(@RequestParam String fromPostcode, @RequestParam String toPostcode) {
        List<Battery> batteriesList = batteryService.findBatteryByPostcodeRange(fromPostcode, toPostcode);
        List<String> names = batteriesList.stream()
        							.map(Battery::getName)
        							.sorted()
        							.collect(Collectors.toList());
        double totalWattCapacity = batteriesList.stream()
        								.mapToLong(Battery::getCapacity)
        								.sum();
        double avgWattCapacity = batteriesList.stream()
        								.mapToDouble(Battery::getCapacity)
        								.average()
        								.orElse(0.0);
        return Map.of(
                "batteries", names,
                "totalWattCapacity", totalWattCapacity,
                "avgWattCapacity", avgWattCapacity
        );
    }

}
