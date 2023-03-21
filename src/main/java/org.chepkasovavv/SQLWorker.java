package org.chepkasovavv;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class SQLWorker {

    private static Connection cnn;
    private static Connection connect() throws SQLException {
        Connector connector = Connector.getInstance();
        while(cnn==null || cnn.isClosed()) {
            try {
                Thread.sleep(3000);
                cnn = DriverManager.getConnection(connector.DB_URL, connector.USER, connector.PASS);
            }
            catch(SQLException ex) {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return cnn;
    }

    public static int workWithDBString(String SQL, ErrorStruct error, ListParams lstParams) throws SQLException, DataException
    {
        int affectedrows = 0;
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);) {
            int i=1;
            int j=0;
            for(String param : lstParams.lstStrings) {
                pstmt.setString(i, lstParams.lstStrings.get(j));
                i++;
                j++;
            }
            j=0;
            for(Integer param : lstParams.lstInts) {
                pstmt.setInt(i, lstParams.lstInts.get(j));
                i++;
                j++;
            }

            affectedrows = pstmt.executeUpdate();

            String err = error.name + String.valueOf(error.number);
            DataException exp = new DataException(err, error.number);
            if (affectedrows == 0) throw exp;
        }
        catch(SQLException ex)
        {
            throw  ex;
        }
        return affectedrows;
    }

    public static int addUser(String name, String surname, String thirdName, String login, String password)
            throws SQLException, DataException
    {
        String SQL = String.format("INSERT INTO users (name, surname, thirdname, login, password) " +
                        "VALUES (?, ?, ?, ?, ?)",
                name, surname, thirdName, login, password);

        String strErr = String.format("Не возможно добавить пользователя с данными %s, %s, %S, %s. %s \n",
                name, surname, thirdName, login, password);
        ErrorStruct error = new ErrorStruct(strErr, 3);
        ArrayList<String> lstStr = new ArrayList<>(Arrays.asList(name, surname, thirdName, login, password));
        ArrayList<Integer> lstInt = new ArrayList<>();
        ListParams lstParams = new ListParams(lstStr, lstInt);
        int affectedRows = workWithDBString(SQL, error, lstParams);
        return affectedRows;
    }

    public static int editUser(int id, String columnNameToChange, String valueToChange) throws SQLException, DataException
    {
        String SQL = "UPDATE users  " +
                " SET " + columnNameToChange + " = ? " +
                " WHERE id_user" + " = ?";

        String strErr = String.format("Не найдена запись для обновления по id=%s\n",
                id);
        ErrorStruct error = new ErrorStruct(strErr, 1);
        ArrayList<String> lstStr = new ArrayList<>(Arrays.asList(valueToChange));
        ArrayList<Integer> lstInt = new ArrayList<>(Arrays.asList(id));
        ListParams lstParams = new ListParams(lstStr, lstInt);
        int affectedRows = workWithDBString(SQL, error, lstParams);
        return affectedRows;
    }

    public static int deleteUser(int id) throws SQLException, DataException{
        String SQL = "DELETE FROM users WHERE id_user = ?";

        String strErr = String.format("Нет записи для удаления по id=%d\n",
                id);
        ErrorStruct error = new ErrorStruct(strErr, 0);
        ArrayList<String> lstStr = new ArrayList<>();
        ArrayList<Integer> lstInt = new ArrayList<>(Arrays.asList(id));
        ListParams lstParams = new ListParams(lstStr, lstInt);
        int affectedRows = workWithDBString(SQL, error, lstParams);
        return affectedRows;
    }

    private static SQLException consoleOutput(ResultSet rs, String[] strNames)
    {
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
            return null;
        }
        catch (SQLException e) {
            return e;
        }
    }

    //Получить всех пользователей
    public static void getUsers() throws SQLException
    {
        String query = "select id_user as id, name, surname, thirdname, login, password  from users;";
        String[] strNames = {"id", "name", "surname", "thirdname","login", "password"};
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        SQLException ex = consoleOutput(rs, strNames);
        if(ex!=null) throw  ex;
    }
}
