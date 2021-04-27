package de.arimond.demo.demorestapi.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable> {

    T load(Long id);

    void save(T object);

    void delete(T object);

    List<T> loadAll();

    T merge(T object);

    void update(T object);
}

