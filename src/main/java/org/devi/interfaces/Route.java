package org.devi. interfaces;

public interface Route {
    void build();

    default void init() {

    }

    default void dispose() {
    }

}
