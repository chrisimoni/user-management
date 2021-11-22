package com.chrisimoni.usermanagement.response;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RestResponse {
	public ResponseEntity<?> reponse(Map.Entry<Boolean, Object> result) {
		HttpStatus status = null;
		CustomResponse customResponse = null;
		if(!result.getKey()) {
			status = HttpStatus.NOT_FOUND;
			String errorMessage = (String) result.getValue();
			customResponse = CustomResponse.builder().code(status.value()).data(errorMessage).build();
			
		}else {
			status = HttpStatus.OK;
			customResponse = CustomResponse.builder().code(status.value()).data(result.getValue()).build();
		}
		
		return ResponseEntity.status(status).body(customResponse);
	}
}
