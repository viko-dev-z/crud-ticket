package com.example.crudticket.repository;

import java.util.Collection;

/**
 * Interface that has common persistence actions. This interface is generic,
 * so it easy to use any other implementation, making the repository
 * an extensible solution.
 */
public interface CommonRepository<T> {
    public T save(T model);
    public Iterable<T> save(Collection<T> models);
    public void delete(T model);
    public T findById(String id);
    public Iterable<T> findAll();
}
