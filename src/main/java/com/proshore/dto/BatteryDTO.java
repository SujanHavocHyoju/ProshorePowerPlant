package com.proshore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatteryDTO {
	@NotBlank (message = "Battery name cannot be blank")
	private String name;
	@NotBlank (message = "Battery postcode cannot be blank")
	private String postcode;
	@Min(value = 100, message = "Battery capacity should be greater or equal to 100")
	private Long capacity;
}
