package com.proshore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatteryDTO {
	@JsonProperty("name")
	private String name;
	@JsonProperty("postcode")
	private String postcode;
	@JsonProperty("capacity")
	private Long capacity;
}
