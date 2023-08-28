package com.proshore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proshore.TestUtils;
import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;
import com.proshore.service.BatteryService;
import com.proshore.util.BatteryMapper;

//@RunWith(SpringRunner.class)
@WebMvcTest(BatteryController.class) //Specifying the controller we want to test.
public class BatteryControllerTests extends TestUtils{
	
	private static final String API_ENDPOINT = "/api/v1/batteries";
	private static final String API_ENDPOINT_ADD = "/api/v1/batteries/add";
	private static final String API_ENDPOINT_SEARCHBY_POSTCODERANGE = "/api/v1/batteries/search";
	private static final String API_ENDPOINT_SEARCHBY_NAME = "/api/v1/batteries/searchByName";
	
	@MockBean
	private BatteryService batteryService;
	// Creates a mock of BatteryService and adds it into the application context 
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BatteryMapper batteryMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void get_request_should_return_battery_list() throws Exception {
		List<BatteryDTO> batteryDtoList = generateBatteryDTOList();
		when(batteryService.getAllBatteries()).thenReturn(generateBatteryList());
		when(batteryMapper.toDto(any(Battery.class)))
						.thenReturn(batteryDtoList.get(0),batteryDtoList.get(1),batteryDtoList.get(2));
		
		mockMvc.perform(get(API_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(3)))
				.andDo(print())
				.andExpect(jsonPath("$[0].name", is("HighWatt")))
				.andExpect(jsonPath("$[0].postcode", is("1550")));
	}
	
	@Test
	void get_request_should_get_filtered_battery_list() throws Exception {
		List<Battery> batteryList = generateBatteryList();
		List<Battery> filteredList = batteryList.stream()
												.filter( b -> {
													try {
										                int postcode = Integer.parseInt(b.getPostcode());
										                return postcode > 1000 && postcode < 2000;
										            } catch (NumberFormatException e) {
										                return false;
										            }
												})
												.collect(Collectors.toList());
		when(batteryService.findBatteryByPostcodeRange(anyString(), anyString()))
			.thenReturn(filteredList);
		
		mockMvc.perform(get(API_ENDPOINT_SEARCHBY_POSTCODERANGE)
						.param("fromPostcode", "1000")
						.param("toPostcode", "2000"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.batteries[0]", is("Everready")))
                .andExpect(jsonPath("$.batteries[1]", is("HighWatt")))
                .andExpect(jsonPath("$.totalWattCapacity", is(5000.0)))
                .andExpect(jsonPath("$.avgWattCapacity", is(2500.0))); 
		
	}
	
	@Test
	void get_request_should_return_battery_by_name() throws Exception {
		BatteryDTO batteryDto = generateBatteryDTO();
		when(batteryService.findByName("HighWatt")).thenReturn(Optional.of(generateBattery()));
//		when(batteryMapper.toDto(battery)).thenReturn(batteryDto);
		when(batteryMapper.toDto(any(Battery.class))).thenReturn(batteryDto);

		mockMvc.perform(get(API_ENDPOINT_SEARCHBY_NAME).param("name", "HighWatt"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.name", is("HighWatt")));
	}
	
	@Test
	void post_request_should_add_battery() throws Exception {
		BatteryDTO batteryDto = generateBatteryDTO();
		Battery battery = generateBattery();
		when(batteryService.addBattery(any(Battery.class))).thenReturn(battery);
        when(batteryMapper.toEntity(any(BatteryDTO.class))).thenReturn(battery);
		when(batteryMapper.toDto(any(Battery.class))).thenReturn(batteryDto);
		
		mockMvc.perform(post(API_ENDPOINT_ADD)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(batteryDto)))
				.andExpect(status().isCreated())
				.andDo(print())
				.andExpect(jsonPath("$.name", is("HighWatt")));
	}

}
