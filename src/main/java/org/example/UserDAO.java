package org.example;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAO<User> {
    void save(User user);
    List<User> getAll();
    Optional<User> getByID(int id);
    List<User> getUsersByGender(String gender);
    Integer getMaxID();
}
