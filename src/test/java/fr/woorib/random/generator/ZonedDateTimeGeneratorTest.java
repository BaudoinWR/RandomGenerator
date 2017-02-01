package fr.woorib.random.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ZonedDateTimeGeneratorTest {

    private ZonedDateTimeGenerator generator;
    private static final ZonedDateTime before;
    private static final ZonedDateTime after;
    private static final DateTimeFormatter formatter;
    static {
        before = ZonedDateTime.of(1986, 9, 5, 0, 0, 0, 0, ZoneId.systemDefault());
        after = ZonedDateTime.of(1980, 6, 2, 0, 0, 0, 0, ZoneId.systemDefault());
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();

        formatter = builder.appendValue(ChronoField.YEAR)
                        .appendLiteral("-")
                        .appendValue(ChronoField.DAY_OF_MONTH, 2)
                        .appendLiteral("-")
                        .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                        .appendLiteral(" ")
                        .appendValue(ChronoField.HOUR_OF_DAY, 2)
                        .appendLiteral(":")
                        .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                        .appendLiteral(":")
                        .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                        .appendLiteral(":")
                        .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                        .toFormatter();
        System.out.println(after.format(formatter));
    }


    @BeforeEach
    public void init() {
        generator = new ZonedDateTimeGenerator();
    }

    @Test
    public void testGenerateZonedDateTime() {
        ZonedDateTime timestamp = generator.generate();
        Assertions.assertNotNull(timestamp);
    }

    @Test
    public void testGenerateZonedDateTimeBefore() {
        ZonedDateTime timestamp = generator.lessThan(before).generate();
        System.out.println(timestamp.format(formatter));
        Assertions.assertTrue(timestamp.toOffsetDateTime().isBefore(before.toOffsetDateTime()));
    }

    @Test
    public void testGenerateZonedDateTimeAfter() {
        ZonedDateTime timestamp = generator.moreThan(after).generate();
        System.out.println(timestamp.format(formatter));
        Assertions.assertTrue(timestamp.toOffsetDateTime().isAfter(after.toOffsetDateTime()));
    }

    @Test
    public void testGenrateZonedDateTimeBetween() {
        ZonedDateTime timestamp = generator.moreThan(after).lessThan(before).generate();
        System.out.println(timestamp.format(formatter));
        Assertions.assertTrue(timestamp.toOffsetDateTime().isAfter(after.toOffsetDateTime())
                && timestamp.toOffsetDateTime().isBefore(before.toOffsetDateTime()));
    }
}