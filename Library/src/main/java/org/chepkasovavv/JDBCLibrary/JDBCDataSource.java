package org.chepkasovavv.JDBCLibrary;

import org.postgresql.ds.PGSimpleDataSource;

public class JDBCDataSource {

    static final String fileName = "error.log";
    private String host;
    private int port;
    private String databaseName;
    private String userLogin;
    private String password;

    public JDBCDataSource(String host, int port, String databaseName, String userLogin, String password) {
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
        this.userLogin = userLogin;
        this.password = password;
    }

    public PGSimpleDataSource dataSource()  {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName(host);
        ds.setDatabaseName(databaseName);
        ds.setPortNumber(port);
        ds.setPassword(password);
        ds.setUser(userLogin);
        return ds;
    }
}
