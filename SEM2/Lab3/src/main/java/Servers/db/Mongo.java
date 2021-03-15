package Servers.db;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.Success;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Mongo {
    private final MongoClient client;
    public final Queue<Event> events;

    public MongoClient getClient() {
        return client;
    }

    private final String name;
    private static final String EVENT_COLLECTION = "event";
    private static final String CLIENT_COLLECTION = "client";

    public Mongo(MongoClient client, String name) {
        this.name = name;
        this.client = client;
        this.events = new ConcurrentLinkedQueue<>();
        client.getDatabase(this.name).getCollection(EVENT_COLLECTION).find()
                .maxTime(10, TimeUnit.SECONDS).toObservable()
                .map(doc -> new Event(doc.getInteger("id"),
                        new Timestamp(doc.getInteger("time")),
                        doc.getString("type").equals("IN") ? Event.EventType.IN : Event.EventType.OUT))
                .forEach(events::add);
    }

    private <T extends Documented> Success add(T obj, String collection) {
        return client.getDatabase(name).getCollection(collection).insertOne(obj.doc())
                .timeout(10, TimeUnit.SECONDS).toBlocking().single();
    }

    public void drop() {
        this.getClient().getDatabase(name).drop().toBlocking().single();
        events.clear();
    }

    public Success addEvent(Event event) {
        events.add(event);
        return add(event, EVENT_COLLECTION);
    }

    public Success addClient(Client client) {
        return add(client, CLIENT_COLLECTION);
    }

    public List<Client> getAllClients(Integer id) {
        List<Client> tickets = new ArrayList<>();
        client.getDatabase(name).getCollection(CLIENT_COLLECTION).find(Filters.eq("id", id))
                .maxTime(10, TimeUnit.SECONDS).toObservable()
                .map(doc -> new Client(doc.getInteger("id"), doc.getDate("start"), doc.getDate("finish")))
                .toBlocking().forEach(tickets::add);
        return tickets;
    }

    public Optional<Client> getCurrentClient(Integer id) {
        return getAllClients(id).stream().max(Comparator.comparing(Client::getStart));
    }

}
