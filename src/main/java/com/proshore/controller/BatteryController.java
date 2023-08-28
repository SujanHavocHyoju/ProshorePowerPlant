package com.proshore.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.proshore.dto.BatteryDTO;
import com.proshore.exception.BatteryNotFoundException;
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
    public ResponseEntity<List<BatteryDTO>> getAllBatteries(){
    	List<BatteryDTO> batteries = batteryService.getAllBatteries()
				.stream()
				.map(batteryItem -> {
					return batteryMapper.toDto(batteryItem);
				})
				.collect(Collectors.toList());
		if(batteries.isEmpty())
			throw new BatteryNotFoundException("No Battery records found.");
//			return new ResponseEntity<List<BatteryDTO>>(batteries, HttpStatus.OK);
		return ResponseEntity.ok().body(batteries);
    }
    
    @PostMapping
    public ResponseEntity<String> addBatteries(@RequestBody List<BatteryDTO> batteryDtoList) {
    	List<Battery> batteryList = batteryDtoList.stream()
				.map(batteryDto -> {
					return batteryMapper.toEntity(batteryDto);
				})
				.collect(Collectors.toList());
		batteryService.addBatteries(batteryList);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
//      	return new ResponseEntity<>(String.format("Inserted %d battery records into database", batteryList.size()), HttpStatus.CREATED);
		return ResponseEntity
				.created(location)
				.body(String.format("Inserted %d battery records into database", batteryList.size()));
    }
    
    @PostMapping("/add")
    public ResponseEntity<BatteryDTO> addBattery(@RequestBody BatteryDTO batteryDto) {
    	Battery battery = batteryService.addBattery(batteryMapper.toEntity(batteryDto));
//        return new ResponseEntity<>(batteryMapper.toDto(battery), HttpStatus.CREATED);
    	return ResponseEntity.created(null).body(batteryMapper.toDto(battery));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBatteries(@RequestParam String fromPostcode, @RequestParam String toPostcode){
    	List<Battery> batteriesList = batteryService.findBatteryByPostcodeRange(fromPostcode, toPostcode);
        if(batteriesList.isEmpty())
    		throw new BatteryNotFoundException("No Battery records found matching your parameters.");
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
        Map<String, Object> searchResult = Map.of(
								                "batteries", names,
								                "totalWattCapacity", totalWattCapacity,
								                "avgWattCapacity", avgWattCapacity
									        );
//        return new ResponseEntity<>(searchResult, HttpStatus.OK);
        return ResponseEntity.ok().body(searchResult);
    }
    
    @GetMapping("/searchByName")
    public ResponseEntity<BatteryDTO> findBatteryByName(@RequestParam String name){
    	Battery battery = batteryService.findByName(name)
    						.orElseThrow(() -> 
    							new BatteryNotFoundException("No Battery records found matching given name."));
        return ResponseEntity.ok().body(batteryMapper.toDto(battery));
    }

}
