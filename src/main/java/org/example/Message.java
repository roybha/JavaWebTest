package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {
    private final Integer id;
    private final String text;
    private final LocalDateTime time;
    private final Integer from;
    private final Integer to;
    public Message(Integer id, String text, LocalDateTime time, Integer from, Integer to) {
        this.id = id;
        this.text = text;
        this.time = time;
        this.from = from;
        this.to = to;
    }
    public Integer getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public Integer getFrom() {
        return from;
    }
    public Integer getTo() {
        return to;
    }
    public String sendMessage(String author) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm");
        return String.format("%s: %s %s", author, text, time.format(formatter));
    }

}
