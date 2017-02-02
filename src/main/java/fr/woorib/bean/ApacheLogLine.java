package fr.woorib.bean;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

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
    String referer;
    String userAgent;

    private static final DateTimeFormatter formatter;

    static {
        formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:kk:mm:ss X").withLocale(Locale.ENGLISH);
    }
    @Override
    public String toString() {
        return ip.toString().substring(1) + " - "
                + user
                + " [" + formatter.format(time) + "] \""
                + request + " HTTP/1.1\" "
                + statusCode + " "
                + dataSize + " "
                + referer + " "
                + userAgent;
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

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public ZonedDateTime getTime() {
        return time;
    }
}
