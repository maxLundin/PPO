package Servers;

import Servers.db.Client;
import Servers.db.Event;
import Servers.db.Mongo;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InOutServer extends AbstractServer {

    public InOutServer(Mongo mongo, int port) {
        super(mongo, port);
    }

    @Override
    Pair act(Map<String, List<String>> queryParam, String action) {
        Pair response = new Pair();
        int id = Integer.parseInt(queryParam.get("id").get(0));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        switch (action) {
            case "in":
                response.response = in(id, now);
                response.status = HttpResponseStatus.OK;
                break;
            case "out":
                response.response = out(id, now);
                response.status = (HttpResponseStatus.OK);
                break;
            default:
                response.response = Observable.just("bad request");
                response.status = (HttpResponseStatus.BAD_REQUEST);
        }
        return response;
    }

    public Observable<String> in(int id, Timestamp now) {

        Optional<Client> client = mongo.getCurrentClient(id);
        if (client.isEmpty() || now.after(client.get().getFinish())) {
            return Observable.just("Not valid client");
        }
        Event event = new Event(id, now, Event.EventType.IN);
        mongo.addEvent(event);
        return Observable.just("Entered");
    }

    public Observable<String> out(int id, Timestamp now) {
        Optional<Client> client = mongo.getCurrentClient(id);
        if (client.isEmpty()) {
            return Observable.just("Not valid client");
        }
        Event event = new Event(id, now, Event.EventType.OUT);
        mongo.addEvent(event);
        return Observable.just("Exited");
    }

}
