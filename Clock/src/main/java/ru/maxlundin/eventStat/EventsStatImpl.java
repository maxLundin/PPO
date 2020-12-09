package ru.maxlundin.eventStat;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class EventsStatImpl implements EventsStatistic {
    private final Queue<Event> evensQueue = new ArrayDeque<>();
    private final Clock clock;
    private final Map<String, Integer> eventsMap = new HashMap<>();

    public EventsStatImpl(Clock clock) {
        this.clock = clock;
    }

    void delExtra(Instant time) {
        while (!evensQueue.isEmpty()) {
            Event event = evensQueue.peek();
            final long millis_in_hour = 1000 * 60 * 60;
            if (event.getTime().toEpochMilli() + millis_in_hour <= time.toEpochMilli()) {
                evensQueue.remove();
                Integer count = eventsMap.get(event.getName());
                if (count == 1) {
                    eventsMap.remove(event.getName());
                } else {
                    eventsMap.put(event.getName(), count - 1);
                }
            } else {
                break;
            }
        }
    }

    private double getRpm(Integer count) {
        final long minutes_in_hour = 60;
        return count == null ? 0 : ((double) count) / minutes_in_hour;
    }

    @Override
    public void incEvent(String name) {
        Instant time = clock.instant();
        Event event = new Event(name, time);
        evensQueue.add(event);
        eventsMap.putIfAbsent(name, 0);
        eventsMap.put(name, eventsMap.get(name) + 1);
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant time = clock.instant();
        delExtra(time);
        return getRpm(eventsMap.get(name));
    }

    @Override
    public Map<String, Double> getAllEventStatistic() {
        Instant time = clock.instant();
        delExtra(time);
        class Pair {
            final private String str;
            final private Double dbl;

            Pair(String str, Double dbl) {
                this.str = str;
                this.dbl = dbl;
            }

            public String getStr() {
                return str;
            }

            public Double getDbl() {
                return dbl;
            }
        }
        return eventsMap.entrySet().stream().map((a) -> new Pair(a.getKey(), getRpm(a.getValue()))).collect(Collectors.toMap(Pair::getStr, Pair::getDbl));
    }

    @Override
    public void printStatistic() {
        Instant time = clock.instant();
        delExtra(time);
        eventsMap.forEach((key, value) -> System.out.println(key + " -> " + getRpm(value)));
    }
}
