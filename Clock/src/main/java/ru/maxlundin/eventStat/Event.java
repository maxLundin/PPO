package ru.maxlundin.eventStat;

import java.time.Instant;

public class Event {
    final private String name;
    final private Instant time;

    public Event(String name, Instant time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public Instant getTime() {
        return time;
    }
}
