package com.service;

import java.util.List;

public interface Service<T> {
    List<T> getAll();
    T getById(String id);
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(String id);
}
