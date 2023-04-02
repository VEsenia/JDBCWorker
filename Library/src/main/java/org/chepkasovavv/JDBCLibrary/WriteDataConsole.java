package org.chepkasovavv.JDBCLibrary;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriteDataConsole implements WriteData {
    @Override
    public void print(ResultSet rs, String[] strNames) throws SQLException {
        try {
            while (rs.next()) {
                //Display values
                for (int i = 0; i < strNames.length; i++) {
                    if (i>0)
                    {
                        System.out.print(", ");
                    }
                    System.out.print(strNames[i]+": " + rs.getString(strNames[i]));
                }
                System.out.println();
            }
        }
        catch (SQLException e) {
            throw  e;
        }
    }
}
