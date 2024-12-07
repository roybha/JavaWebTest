package org.example;

import java.util.List;

public interface ChoiceDAO extends DAO<Choice> {
    void save(Choice choice);
    List<Choice> getAll();
}
