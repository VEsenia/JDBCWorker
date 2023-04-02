package org.chepkasovavv.JDBCLibrary;

import org.chepkasovavv.Users.DataException;
import org.chepkasovavv.Users.ErrorStruct;
import org.chepkasovavv.Users.FileWorker;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;

public class JDBCWorker {

    //Если коннекта нет, перепоключиться
    private Connection getConnection(DataSource ds) throws SQLException {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            while (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
                Thread.sleep(4000);
            }
        }
        catch (InterruptedException ex) {
            FileWorker.WriteError(ex.getMessage(), ex.getCause());
        }
        return  connection;
    }

    public int MakeQuery(DataSource ds, String query, ErrorStruct error){
        int affectedrows = 0;
        try(Connection conn = getConnection(ds);) {
            try (PreparedStatement pstmt = conn.prepareStatement(query);) {
                affectedrows = pstmt.executeUpdate();

                String err = error.name + String.valueOf(error.number);
                DataException dataExp = new DataException(err, error.number);
                if (affectedrows == 0) {
                    FileWorker.WriteError(dataExp.getMessage(), null);
                }
            } catch (SQLException ex) {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            }
        }
        catch(SQLException ex)
        {
            FileWorker.WriteError(ex.getMessage(), ex.getCause());
        }
        return affectedrows;
    }

    public void ReceiveData(DataSource ds, String query, String[] strNames, WriteData writeData)
    {
        try(Connection conn = getConnection(ds);) {
            try (Statement stmt = conn.createStatement();) {
                ResultSet rs = stmt.executeQuery(query);
                writeData.print(rs, strNames);
            }
            catch (SQLException ex)
            {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            }
        }
        catch (SQLException ex)
        {
            FileWorker.WriteError(ex.getMessage(), ex.getCause());
        }
    }
}
