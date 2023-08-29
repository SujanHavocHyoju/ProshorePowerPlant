package com.proshore.util;

import org.springframework.stereotype.Component;

import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;

/**
 * Custom Entity to DTO mapping Alternative: MapStruct or ModelMapper
 */
@Component
public class BatteryMapper {
	/**
	 * Converts {@link Battery} entity object to {@link BatteryDTO} dto object
	 * 
	 * @param entity
	 * @return {@link BatteryDTO} dto object
	 */
	public BatteryDTO toDto(Battery entity) {
		BatteryDTO dto = new BatteryDTO();
		dto.setName(entity.getName());
		dto.setPostcode(entity.getPostcode());
		dto.setCapacity(entity.getCapacity());
		return dto;
	}

	/**
	 * Converts {@link BatteryDTO} dto object to {@link Battery} entity object
	 * 
	 * @param dto
	 * @return {@link Battery} entity object
	 */
	public Battery toEntity(BatteryDTO dto) {
		Battery entity = new Battery();
		entity.setName(dto.getName());
		entity.setPostcode(dto.getPostcode());
		entity.setCapacity(dto.getCapacity());
		return entity;
	}
}
