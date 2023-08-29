package com.proshore.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.proshore.configuration.BatterySwaggerAPI;
import com.proshore.dto.BatteryDTO;
import com.proshore.exception.BatteryNotFoundException;
import com.proshore.model.Battery;
import com.proshore.service.BatteryService;
import com.proshore.util.BatteryMapper;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/batteries")
@Log4j2
public class BatteryController implements BatterySwaggerAPI {
	
	@Autowired
	private BatteryService batteryService;
	
	@Autowired
	private BatteryMapper batteryMapper;
	
    @GetMapping
    public ResponseEntity<List<BatteryDTO>> getAllBatteries() {
    	// Converting retrieved batteries into battery dto list 
    	List<BatteryDTO> batteries = batteryService.getAllBatteries()
				.stream()
				.map(batteryItem -> {
					return batteryMapper.toDto(batteryItem);
				})
				.collect(Collectors.toList());
		if(batteries.isEmpty()) {
			log.error("No batteries were retrieved");
			throw new BatteryNotFoundException("No Battery records found.");
		}
		log.info("Batteries retrieved", batteries.toString());
		/* Alternatively, can use: */
		// return new ResponseEntity<List<BatteryDTO>>(batteries, HttpStatus.OK);
		return ResponseEntity.ok().body(batteries);
    }
    
    @PostMapping
    public ResponseEntity<String> addBatteries(@RequestBody List<BatteryDTO> batteryDtoList) {
    	// Converting battery dto list from request body into battery entity list
    	List<Battery> batteryList = batteryDtoList.stream()
				.map(batteryDto -> {
					return batteryMapper.toEntity(batteryDto);
				})
				.collect(Collectors.toList());
    	// Inserting converted battery entity list
    	log.info("Converted battery dto list into battery entity list");
		batteryService.addBatteries(batteryList);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		/* Alternatively, can use: */
		// return new ResponseEntity<>(String.format("Inserted %d battery records into database", batteryList.size()), HttpStatus.CREATED);
		log.info("Inserted batteries into the database");
		return ResponseEntity
				.created(location)
				.body(String.format("Inserted %d battery records into database", batteryList.size()));
    }
    
    @PostMapping("/add")
    public ResponseEntity<BatteryDTO> addBattery(@Valid @RequestBody BatteryDTO batteryDto) {
    	// Converting battery dto from request body into battery entity
    	Battery battery = batteryMapper.toEntity(batteryDto);
    	log.info("Converted battery dto into battery entity");
    	// Inserting converted battery entity
    	Battery batteryResult = batteryService.addBattery(battery);
    	log.info("Converted inserted battery entity into battery dto");
    	/* Alternatively, can use: */
    	// return new ResponseEntity<>(batteryMapper.toDto(battery), HttpStatus.CREATED);
    	return ResponseEntity.created(null).body(batteryMapper.toDto(batteryResult));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBatteries(@RequestParam String fromPostcode, @RequestParam String toPostcode) {
    	// Retrieving batteries from data source
    	List<Battery> batteriesList = batteryService.findBatteryByPostcodeRange(fromPostcode, toPostcode);
        if(batteriesList.isEmpty()) {
        	log.error("No batteries found matching the parameters");
    		throw new BatteryNotFoundException("No Battery records found matching your parameters");
        }
        log.info("Batteries found matching the parameters");
        // Extracting names of batteries
        List<String> names = batteriesList.stream()
        							.map(Battery::getName)
        							.sorted()
        							.collect(Collectors.toList());
        // Calculating the total watt capacity of extracted batteries
        double totalWattCapacity = batteriesList.stream()
        								.mapToLong(Battery::getCapacity)
        								.sum();
        // Calculating the average watt capacity of extracted batteries
        double avgWattCapacity = batteriesList.stream()
        								.mapToDouble(Battery::getCapacity)
        								.average()
        								.orElse(0.0);
        // Generating custom response
        Map<String, Object> searchResult = Map.of(
								                "batteries", names,
								                "totalWattCapacity", totalWattCapacity,
								                "avgWattCapacity", avgWattCapacity
									        );
        log.info("Calculated total watt capacity and average watt capacity");
        /* Alternatively, can use: */
        // return new ResponseEntity<>(searchResult, HttpStatus.OK);
        return ResponseEntity.ok().body(searchResult);
    }
    
    @GetMapping("/searchByName")
    public ResponseEntity<BatteryDTO> findBatteryByName(@RequestParam String name) {
    	// Retrieving battery using name
    	Battery battery = batteryService.findByName(name)
    						.orElseThrow(() -> 
    							new BatteryNotFoundException("No Battery records found matching given name"));
    	log.info("Battery found matching the name");
    	return ResponseEntity.ok().body(batteryMapper.toDto(battery));
    }

}
