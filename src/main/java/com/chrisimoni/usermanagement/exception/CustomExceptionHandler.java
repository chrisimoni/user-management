package com.chrisimoni.usermanagement.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.chrisimoni.usermanagement.response.CustomResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			  MethodArgumentNotValidException ex, 
			  HttpHeaders headers, 
			  HttpStatus status, 
			  WebRequest request) {
			    String errors = ex.getBindingResult().getFieldErrors().stream()
		                .map(p -> p.getDefaultMessage()).collect(Collectors.joining("\n"));
			    
			    CustomResponse build = CustomResponse.builder()
			    		.code(400).status(status)
			    		.data(null)
			    		.errorMessage("Information missing: " +errors)
			    		.build();
			    System.out.println("Error thrown - "+ex);
			    return handleExceptionInternal(
			      ex, build, headers, build.getStatus(), request);
			}
}
