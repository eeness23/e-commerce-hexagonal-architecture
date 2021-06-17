package com.enes.ecommerce.commons.domain.model;

public interface EventListener<T extends Event> {
    void consume(T event);
}
