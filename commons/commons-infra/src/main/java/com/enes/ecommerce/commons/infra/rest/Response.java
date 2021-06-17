package com.enes.ecommerce.commons.infra.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Response<T> {
    private final T data;
}