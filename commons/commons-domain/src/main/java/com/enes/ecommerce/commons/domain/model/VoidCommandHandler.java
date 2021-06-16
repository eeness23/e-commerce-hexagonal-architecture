package com.enes.ecommerce.commons.domain.model;

public interface VoidCommandHandler<T extends Command> {
    void handle(T command);
}
