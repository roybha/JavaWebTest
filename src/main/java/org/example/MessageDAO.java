package org.example;

import java.util.List;

public interface MessageDAO extends DAO<Message> {
    void save(Message message);
    List<Message> getAll();
    List<Message> getAll(Integer fromWho, Integer toWho);
}
