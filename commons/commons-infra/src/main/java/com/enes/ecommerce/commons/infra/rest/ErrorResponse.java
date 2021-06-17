package com.enes.ecommerce.commons.infra.rest;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse<T> {
    private final String path;
    private final String code;
    private final T description;
}