package com.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();
    T findById(String id);
    List<T> findByIdLike(String partialId);
    boolean create(T entity);
    boolean update(T entity);
    boolean delete(String id);
}
