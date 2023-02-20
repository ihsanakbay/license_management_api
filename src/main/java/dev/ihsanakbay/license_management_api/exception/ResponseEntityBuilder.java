package dev.ihsanakbay.license_management_api.exception;

import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity<Object> build(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.status());
    }
}
