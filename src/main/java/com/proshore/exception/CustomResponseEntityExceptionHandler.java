package com.proshore.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom ResponseEntity exception handler
 * to make it applicable/shared across all other controllers
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	//override method of ResponseEntityExceptionHandler class : for all Exceptions
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        //creating exception response structure
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        //returning exception structure and Internal Server status
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(BatteryNotFoundException.class)
    //override method of ResponseEntityExceptionHandler class : only for BatteryNotFoundException 
    public final ResponseEntity<Object> handleUserNotFoundException(BatteryNotFoundException ex, WebRequest request) {
        //creating exception response structure
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        //returning exception structure and Not Found status
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//creating exception response structure
        ExceptionResponse exceptionResponse
        = new ExceptionResponse(new Date(), "Validation Failed. " +ex.getMessage(), ex.getBindingResult().toString());
        //returning exception structure and Bad Request status
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
}
