package Servers.db;

import org.bson.Document;

import java.sql.Timestamp;

public class Event implements Documented {

    public int getId() {
        return id;
    }

    public Timestamp getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

    private final int id;
    private final Timestamp time;
    private final EventType type;

    public enum EventType {
        IN, OUT
    }

    public Event(int id, Timestamp time, EventType type) {
        this.id = id;
        this.time = time;
        this.type = type;
    }


    @Override
    public Document doc() {
        return new Document().append("id", id).append("time", time.getNanos()).append("type", type.toString());
    }
}
