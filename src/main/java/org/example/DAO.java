package org.example;

import java.util.List;

public interface DAO<A> {
    void save(A entity);
    List<A> getAll();
}
