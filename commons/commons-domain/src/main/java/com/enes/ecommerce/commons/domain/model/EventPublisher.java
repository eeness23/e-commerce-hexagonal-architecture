package com.enes.ecommerce.commons.domain.model;

public interface EventPublisher<T extends Event> {
    void publish(T event);
}
