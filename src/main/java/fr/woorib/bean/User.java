package fr.woorib.bean;

import java.net.InetAddress;
import java.time.ZonedDateTime;

/**
 * Created by baudoin on 01/02/2017.
 */
public class User {
    private String firstName;
    private String lastName;
    private InetAddress ip;
    private ZonedDateTime dateOfBirth;
    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "name='" + firstName + " " + lastName + '\'' +
                ", ip=" + ip +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setDateOfBirth(ZonedDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}