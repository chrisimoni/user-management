package com.chrisimoni.usermanagement.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomResponse {
	private int code;
	private HttpStatus status;
	private Object data;
	private String errorMessage;
}
