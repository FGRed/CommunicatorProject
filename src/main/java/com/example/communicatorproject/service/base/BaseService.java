package com.example.communicatorproject.service.base;

import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BaseService<T>{
    List<T> findAll();

    List<T> findAll(Sort sort);

    List<T> findAllById(List<Long> ids);

    <S extends T> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends T> S saveAndFlush(S entity);

    <S extends T> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<T> entities);

    void deleteAllByIdInBatch(Iterable<Long> ids);

    void deleteAllInBatch();

    T getReferenceById(Long id);

    <S extends T> List<S> findAll(Example<S> example);

    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    T save(T entity);
}
