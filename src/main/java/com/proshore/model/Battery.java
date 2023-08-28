package com.proshore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Battery {

	@Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Battery name must not be blank.")
	private String name;
	@NotBlank(message = "Battery postcode must not be blank.")
	private String postcode;
	private Long capacity;
}
