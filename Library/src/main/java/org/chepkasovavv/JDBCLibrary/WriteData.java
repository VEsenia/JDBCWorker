package org.chepkasovavv.JDBCLibrary;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface WriteData {
    public void print(ResultSet rs, String[] strNames) throws SQLException;
}
