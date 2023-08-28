package com.proshore.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;
}