package Servers;

import Servers.db.Event;
import Servers.db.Mongo;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.*;

public class ReportServer extends AbstractServer {

    List<Integer> day_count = new ArrayList<>();
    long sum_in = 0;
    long num = 0;
    Map<Integer, Timestamp> cur_in = new HashMap<>();
    Queue<Event> events;

    public ReportServer(Mongo mongo, int port, Queue<Event> events) {
        super(mongo, port);
        for (int i = 0; i < 7; ++i) {
            day_count.add(i, 0);
        }
        this.events = events;
    }

    void updateStats() {
        while (!events.isEmpty()) {
            Event event = events.poll();
            if (event.getType() == Event.EventType.IN) {
                cur_in.put(event.getId(), event.getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTime(event.getTime());
                int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
                day_count.set(day, day_count.get(day) + 1);
            } else {
                Timestamp start = cur_in.remove(event.getId());
                sum_in += (event.getTime().getTime() - start.getTime());
                num += 1;
            }
        }
    }

    @Override
    Pair act(Map<String, List<String>> queryParam, String action) {
        Pair response = new Pair();
        updateStats();
        switch (action) {
            case "day_stat":
                response.response = dayStat();
                response.status = HttpResponseStatus.OK;
                break;
            case "long_stat":
                response.response = longStat();
                response.status = (HttpResponseStatus.OK);
                break;
            default:
                response.response = Observable.just("bad request");
                response.status = (HttpResponseStatus.BAD_REQUEST);
        }
        return response;
    }

    Observable<String> longStat() {
        if (num != 0)
            return Observable.just("Average time spent " + Duration.ofMillis(sum_in / num).toString());
        return Observable.just("No users yet");
    }

    Observable<String> dayStat() {
        return Observable.just("Day stat:" + System.lineSeparator() + day_count.stream().map(day -> day + " ").reduce(String::concat).orElse(null) + System.lineSeparator());
    }
}
