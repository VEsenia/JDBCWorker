package org.chepkasovavv.Users;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try
        {
            try {
                SQLWorker.deleteUser(20);
            }
            catch (DataException ex)
            {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            }
            try {
                SQLWorker.editUser(15, "surname", "test777");
            }
            catch(DataException ex)
            {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            }
            try {
                SQLWorker.addUser("tes666", "Калинина", "Сноу", "test", "123");
            }
            catch(DataException ex)
            {
                FileWorker.WriteError(ex.getMessage(), ex.getCause());
            }
            SQLWorker.getUsers();
        }
        catch(SQLException ex)
        {
            FileWorker.WriteError(ex.getMessage(), ex.getCause());
        }
    }
}