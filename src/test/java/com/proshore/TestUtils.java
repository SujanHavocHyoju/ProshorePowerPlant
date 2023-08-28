package com.proshore;

import java.util.List;

import com.proshore.dto.BatteryDTO;
import com.proshore.model.Battery;

public abstract class TestUtils {
	protected BatteryDTO generateBatteryDTO() {
		return new BatteryDTO("HighWatt", "1550", 2000L);
	}
	
	protected Battery generateBattery() {
		return new Battery(1L, "HighWatt", "1550", 2000L);
	}
	
	protected List<BatteryDTO> generateBatteryDTOList(){
		BatteryDTO firstBatteryDTO = new BatteryDTO("HighWatt", "1550", 2000L);
		BatteryDTO secondBatteryDTO = new BatteryDTO("Duracell", "2100", 4000L);
		BatteryDTO thirdBatteryDTO = new BatteryDTO("Everready", "1250", 3000L);
		return List.of(firstBatteryDTO, secondBatteryDTO, thirdBatteryDTO);
	}
	
	protected List<Battery> generateBatteryList(){
		Battery firstBattery = new Battery(1L, "HighWatt", "1550", 2000L);
		Battery secondBattery = new Battery(2L, "Duracell", "2100", 4000L);
		Battery thirdBattery = new Battery(3L, "Everready", "1250", 3000L);
		return List.of(firstBattery, secondBattery, thirdBattery);
	}

}
