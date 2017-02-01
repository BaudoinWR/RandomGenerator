package fr.woorib.bean;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by baudoin on 01/02/2017.
 */
public class ApacheLogLine {
    InetAddress ip;
    String user;
    Integer statusCode;
    ZonedDateTime time;
    String request;
    Integer dataSize;

    private static final DateTimeFormatter formatter;

    static {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();

        formatter = builder.appendValue(ChronoField.YEAR)
                .appendLiteral("-")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral("-")
                .appendValue(ChronoField.DAY_OF_MONTH, 2)
                .appendLiteral(" ")
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .toFormatter();
    }
    @Override
    public String toString() {
        return ip.toString().substring(1) + " - "
                + user
                + " [" + formatter.format(time) + "] \""
                + request + "\" "
                + statusCode + " "
                + dataSize;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }
}
