package org.chepkasovavv;

import java.sql.*;

public class SQLWorker {

    private static Connection connect() throws SQLException {
        Connector connector = Connector.getInstance();
        return DriverManager.getConnection(connector.DB_URL, connector.USER, connector.PASS);
    }
    public static int addUser(String name, String surname, String thirdName, String login, String password)
            throws SQLException
    {
        String SQL = String.format("INSERT INTO users (name, surname, thirdname, login, password) " +
                        "VALUES (?, ?, ?, ?, ?)",
                name, surname, thirdName, login, password);

        int affectedrows = 0;
        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1, name);
        pstmt.setString(2, surname);
        pstmt.setString(3,thirdName);
        pstmt.setString(4,login);
        pstmt.setString(5,password);
        affectedrows = pstmt.executeUpdate();
        return affectedrows;
    }

    public static int editUser(int id, String columnNameToChange, String valueToChange) throws SQLException, DataException
    {
        String SQL = "UPDATE users  " +
                " SET " + columnNameToChange + " = ? " +
                " WHERE id_user" + " = ?";

        int affectedrows = 0;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL);

        pstmt.setString(1, valueToChange);
        pstmt.setInt(2, id);
        affectedrows = pstmt.executeUpdate();
        String err = DataErrors.updateError.name + String.valueOf(id);
        DataException exp = new DataException(err, DataErrors.updateError.number);
        if(affectedrows == 0) throw exp;

        return affectedrows;

    }

    public static int deleteUser(int id) throws SQLException, DataException{
        String SQL = "DELETE FROM users WHERE id_user = ?";

        int affectedrows = 0;

        Connection conn = connect();
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.setInt(1, id);
        affectedrows = pstmt.executeUpdate();
        String err = DataErrors.deleteError.name + String.valueOf(id);
        DataException exp = new DataException(err, DataErrors.deleteError.number);
        if(affectedrows == 0) throw exp;
        return affectedrows;
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
        String query = "select name, surname, thirdname, login, password  from users;";
        String[] strNames = {"name", "surname", "thirdname","login", "password"};
        Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        SQLException ex = consoleOutput(rs, strNames);
        if(ex!=null) throw  ex;
    }
}
