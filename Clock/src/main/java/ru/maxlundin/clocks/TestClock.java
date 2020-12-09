package ru.maxlundin.clocks;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class TestClock extends Clock {

    private Instant instant;

    public TestClock(Instant instant) {
        this.instant = instant;
    }

    @Override
    public ZoneId getZone() {
        return null;
    }

    @Override
    public Clock withZone(ZoneId zoneId) {
        return null;
    }

    @Override
    public Instant instant() {
        return instant;
    }

    public void addTime(Duration duration) {
        instant = instant.plus(duration);
    }
}
