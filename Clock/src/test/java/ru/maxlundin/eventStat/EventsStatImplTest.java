package ru.maxlundin.eventStat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.maxlundin.clocks.TestClock;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

public class EventsStatImplTest {
    private TestClock clock;
    private EventsStatistic eventsStatistic;
    private static final double DELTA = 1e-20;

    @Before
    public void before() {
        clock = new TestClock(Instant.ofEpochMilli(0));
        eventsStatistic = new EventsStatImpl(clock);
    }

    private void checkEventStat(String name, double expected) {
        Assert.assertEquals(eventsStatistic.getEventStatisticByName(name), expected, DELTA);
    }

    private void checkAllEventStats(Map<String, Double> expected) {
        Map<String, Double> events = eventsStatistic.getAllEventStatistic();
        Assert.assertEquals(events.size(), expected.size());
        expected.forEach((name, res) -> Assert.assertEquals(events.get(name), res, DELTA));
    }

    @Test
    public void emptyTest() {
        checkEventStat("1", 0.0);
        checkAllEventStats(Map.of());
    }

    @Test
    public void test1Event() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        checkEventStat("1", 2.0 / 60);
        checkAllEventStats(Map.of("1", 2.0 / 60));
    }

    @Test
    public void test2Events() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        checkEventStat("1", 1.0 / 60);
        checkEventStat("2", 1.0 / 60);
        checkAllEventStats(Map.of("1", 1.0 / 60, "2", 1.0 / 60));
    }

    @Test
    public void testExpiredOneEvent() {
        eventsStatistic.incEvent("1");
        clock.addTime(Duration.ofMinutes(60));
        checkEventStat("1", 0);
        checkAllEventStats(Map.of());
    }

    @Test
    public void testExpiredTwoEvents() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        clock.addTime(Duration.ofMinutes(60));
        checkEventStat("1", 0);
        checkEventStat("2", 0);
        checkAllEventStats(Map.of());
    }

    @Test
    public void testManyEvents1() {
        eventsStatistic.incEvent("1");
        checkEventStat("1", 1.0 / 60);
        clock.addTime(Duration.ofMinutes(30));
        eventsStatistic.incEvent("2");
        checkEventStat("2", 1.0 / 60);
        clock.addTime(Duration.ofMinutes(30));
        checkEventStat("1", 0.0);
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        checkEventStat("1", 4.0 / 60);
        clock.addTime(Duration.ofMinutes(59));
        checkEventStat("1", 4.0 / 60);
        clock.addTime(Duration.ofMinutes(1));
        checkEventStat("1", 0.0);
    }

    @Test
    public void testManyEvents2() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        eventsStatistic.incEvent("3");
        eventsStatistic.incEvent("4");
        eventsStatistic.incEvent("5");
        eventsStatistic.incEvent("6");
        eventsStatistic.incEvent("7");
        checkAllEventStats(
                Map.of("1", 1.0 / 60,
                        "2", 1.0 / 60,
                        "3", 1.0 / 60,
                        "4", 1.0 / 60,
                        "5", 1.0 / 60,
                        "6", 1.0 / 60,
                        "7", 1.0 / 60));
        clock.addTime(Duration.ofMinutes(60));
        checkAllEventStats(Map.of());
    }

}