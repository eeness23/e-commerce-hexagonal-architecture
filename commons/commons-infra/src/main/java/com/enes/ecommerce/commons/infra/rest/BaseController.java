package com.enes.ecommerce.commons.infra.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    protected <T> ResponseEntity<Response<T>> buildResponse(T body, HttpStatus status) {
        Response<T> response = new Response<>(body);
        return new ResponseEntity<>(response, status);
    }
}