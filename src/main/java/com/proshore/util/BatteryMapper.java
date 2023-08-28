package com.proshore.util;

import org.springframework.stereotype.Component;

import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;

@Component
public class BatteryMapper {
	//Manual Mapper logic instead of MapStruct or ModelMapper 
	public BatteryDTO toDto(Battery entity) {
		BatteryDTO dto = new BatteryDTO();
        dto.setName(entity.getName());
        dto.setPostcode(entity.getPostcode());
        dto.setCapacity(entity.getCapacity());
        return dto;
    }

    public Battery toEntity(BatteryDTO dto) {
    	Battery entity = new Battery();
//        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPostcode(dto.getPostcode());
        entity.setCapacity(dto.getCapacity());
        return entity;
    }
}
