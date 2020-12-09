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
    private static final double DOUBLE_DELTA = 1e-12;

    @Before
    public void before() {
        clock = new TestClock(Instant.now());
        eventsStatistic = new EventsStatImpl(clock);
    }

    private void checkEventStatistic(String name, double expected) {
        Assert.assertEquals(eventsStatistic.getEventStatisticByName(name), expected, DOUBLE_DELTA);
    }

    private void checkAllEventStatistics(Map<String, Double> expected) {
        Map<String, Double> events = eventsStatistic.getAllEventStatistic();
        Assert.assertEquals(events.size(), expected.size());
        expected.forEach((name, res) -> Assert.assertEquals(events.get(name), res, DOUBLE_DELTA));
    }

    @Test
    public void emptyTest() {
        checkEventStatistic("1", 0.0);
        checkAllEventStatistics(Map.of());
    }

    @Test
    public void test1Event() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        checkEventStatistic("1", 2.0 / 60);
        checkAllEventStatistics(Map.of("1", 2.0 / 60));
    }

    @Test
    public void test2Events() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        checkEventStatistic("1", 1.0 / 60);
        checkEventStatistic("2", 1.0 / 60);
        checkAllEventStatistics(Map.of("1", 1.0 / 60, "2", 1.0 / 60));
    }

    @Test
    public void testExpiredOneEvent() {
        eventsStatistic.incEvent("1");
        clock.addTime(Duration.ofMinutes(60));
        checkEventStatistic("1", 0);
        checkAllEventStatistics(Map.of());
    }

    @Test
    public void testExpiredTwoEvents() {
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("2");
        clock.addTime(Duration.ofMinutes(60));
        checkEventStatistic("1", 0);
        checkEventStatistic("2", 0);
        checkAllEventStatistics(Map.of());
    }

    @Test
    public void testManyEvents1() {
        eventsStatistic.incEvent("1");
        checkEventStatistic("1", 1.0 / 60);
        clock.addTime(Duration.ofMinutes(30));
        eventsStatistic.incEvent("2");
        checkEventStatistic("2", 1.0 / 60);
        clock.addTime(Duration.ofMinutes(30));
        checkEventStatistic("1", 0.0);
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        eventsStatistic.incEvent("1");
        checkEventStatistic("1", 4.0 / 60);
        clock.addTime(Duration.ofMinutes(59));
        checkEventStatistic("1", 4.0 / 60);
        clock.addTime(Duration.ofMinutes(1));
        checkEventStatistic("1", 0.0);
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
        checkAllEventStatistics(
                Map.of("1", 1.0 / 60,
                        "2", 1.0 / 60,
                        "3", 1.0 / 60,
                        "4", 1.0 / 60,
                        "5", 1.0 / 60,
                        "6", 1.0 / 60,
                        "7", 1.0 / 60));
        clock.addTime(Duration.ofMinutes(60));
        checkAllEventStatistics(Map.of());
    }

}