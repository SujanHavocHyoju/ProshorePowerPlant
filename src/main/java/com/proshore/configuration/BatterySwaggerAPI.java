package com.proshore.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.proshore.dto.BatteryDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Interface used to define swagger definitions for Controller to implement
 */
@Tag(name = "Proshore Power Plant API", description = "Technical Assessment Task for Proshore")
public interface BatterySwaggerAPI {

	@Operation(summary = "Retrieve all batteries", description = "Retrieves all the battery entities from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "batteries found") })
	ResponseEntity<List<BatteryDTO>> getAllBatteries();

	@Operation(summary = "Creates a list of batteries", description = "Creates a list of battery entity in data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "batteries created") })
	ResponseEntity<String> addBatteries(List<BatteryDTO> batteryDtoList);

	@Operation(summary = "Creates a new battery", description = "Creates a battery entity in data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "battery created") })
	ResponseEntity<BatteryDTO> addBattery(BatteryDTO batteryDto);

	@Operation(summary = "Retrieves batteries", description = "Retrieves batteries matching the provided parameters from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "batteries found") })
	ResponseEntity<Map<String, Object>> searchBatteries(String fromPostcode, String toPostcode);

	@Operation(summary = "Retrieves a battery by name", description = "Retrieves a battery entity using name value from data source")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "battery found") })
	ResponseEntity<BatteryDTO> findBatteryByName(String name);

}
