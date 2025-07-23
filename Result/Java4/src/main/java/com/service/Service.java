package com.service;

import java.util.List;

public interface Service<DTO, PK> {
    List<DTO> findAll();
    DTO findById(PK id);
    boolean create(DTO entity);
    boolean update(DTO entity);
    boolean delete(PK id);
}
