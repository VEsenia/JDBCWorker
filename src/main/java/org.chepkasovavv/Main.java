package org.chepkasovavv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Main {
    private static int addUser(String name, String surname, String thirdName, String login, String password)
    {
        String SQL = String.format("INSERT INTO users (name, surname, thirdname, login, password) " +
                                   "VALUES (?, ?, ?, ?, ?)",
                                    name, surname, thirdName, login, password);

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3,thirdName);
            pstmt.setString(4,login);
            pstmt.setString(5,password);
            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }

    public static Connection connect() throws SQLException {
        Connector connector = Connector.getInstance();
        return DriverManager.getConnection(connector.DB_URL, connector.USER, connector.PASS);
    }


    private static int editUser(int id, String columnNameToChange, String valueToChange)
    {
        String SQL = "UPDATE users  " +
                     " SET " + columnNameToChange + " = ? " +
                     " WHERE id_user" + " = ?";

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, valueToChange);
            pstmt.setInt(2, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;

    }


    public static int deleteUser(int id) {
        String SQL = "DELETE FROM users WHERE id_user = ?";

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
            }
            return null;
        }
        catch (SQLException e) {
            return e;
        }
    }

    //Получить всех пользователей
    private static void getUsers()
    {
        String query = "select name, surname, thirdname, login, password  from users;";
        String[] strNames = {"name", "surname", "thirdname","login", "password"};


        // Open a connection
        try(Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            SQLException ex = consoleOutput(rs, strNames);
            if(ex!=null) throw  ex;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        getUsers();

        deleteUser(1);
        editUser(1, "surname", "test");
        addUser("Вера", "Калинина", "Сноу", "Hellow", "123");
    }
}