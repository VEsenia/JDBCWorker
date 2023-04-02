package org.chepkasovavv.Users;

public class Connector {

    private static final Connector instance = new Connector();

    // private constructor to avoid client applications using the constructor
    private Connector(){}

    public static Connector getInstance() {
        return instance;
    }

    static final String DB_URL = "jdbc:postgresql://localhost:5432/users";
    static final String USER = "tester";
    static final String PASS = "tester";
    static final String fileName = "error.log";
}
