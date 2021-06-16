package com.enes.ecommerce.commons.domain.model;

public interface CommandHandler<E, T extends Command> {
    E handle(T command);
}
