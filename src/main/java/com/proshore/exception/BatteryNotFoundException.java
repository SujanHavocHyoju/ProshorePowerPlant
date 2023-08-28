package com.proshore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BatteryNotFoundException extends RuntimeException{
	public BatteryNotFoundException(String message) {
		super(message);
	}
}
