package Servers;

import Servers.db.Client;
import Servers.db.Mongo;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ManagerServer extends AbstractServer {
    public ManagerServer(Mongo mongo, int port) {
        super(mongo, port);
    }

    @Override
    Pair act(Map<String, List<String>> queryParam, String action) {
        Pair response = new Pair();
        switch (action) {
            case "get":
                response.response = getClientInfo(queryParam);
                response.status = HttpResponseStatus.OK;
                break;
            case "add":
            case "prolong":
                response.response = addClient(queryParam);
                response.status = (HttpResponseStatus.OK);
                break;
            default:
                response.response = Observable.just("bad request");
                response.status = (HttpResponseStatus.BAD_REQUEST);
        }
        return response;
    }

    Observable<String> getClientInfo(Map<String, List<String>> queryParam) {
        int id = Integer.parseInt(queryParam.get("id").get(0));
        Optional<Client> ticket = mongo.getCurrentClient(id);
        return ticket.map(client -> Observable.just(client.toString())).orElseGet(() -> Observable.just("No such client"));
    }

    Observable<String> addClient(Map<String, List<String>> queryParam) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date start, finish;
        try {
            start = format.parse(queryParam.get("start").get(0));
            finish = format.parse(queryParam.get("finish").get(0));
        } catch (ParseException e) {
            return Observable.just("Date Format Error");
        }
        if (finish.before(start)) {
            return Observable.just("Date Format Error. Start after finish");
        }

        int id = Integer.parseInt(queryParam.get("id").get(0));
        mongo.addClient(new Client(id, start, finish));
        return Observable.just("Client added/updated");
    }
}
