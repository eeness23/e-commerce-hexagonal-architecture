package com.enes.ecommerce.commons.domain.model;

public interface QueryHandler<E, T extends Query> {
    E handle(T query);
}
