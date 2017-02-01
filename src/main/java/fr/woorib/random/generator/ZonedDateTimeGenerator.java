package fr.woorib.random.generator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

/**
 * Created by baudoin on 01/02/2017.
 * Generates random ZoneDateTime based on ZoneId.systemDefault()
 */
public class ZonedDateTimeGenerator implements Generator<ZonedDateTime> {

    private ZonedDateTime upperBound = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.MAX_VALUE), ZoneId.systemDefault());
    private ZonedDateTime lowerBound = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.MIN_VALUE), ZoneId.systemDefault());
    private Random random = new Random();

    @Override
    public ZonedDateTime get() {
        long upper = upperBound.toInstant().toEpochMilli();
        long lower = lowerBound.toInstant().toEpochMilli();
        long generated = (long) (lower + (random.nextDouble() * (upper - lower)));
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(generated), ZoneId.systemDefault());
    }

    @Override
    public ZonedDateTimeGenerator lessThan(ZonedDateTime before) {
        this.upperBound = before;
        return this;
    }

    @Override
    public ZonedDateTimeGenerator moreThan(ZonedDateTime after) {
        this.lowerBound = after;
        return this;
    }
}
