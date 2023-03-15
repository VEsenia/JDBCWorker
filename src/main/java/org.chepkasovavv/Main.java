package org.chepkasovavv;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try
        {
            SQLWorker.getUsers();
            try {
                SQLWorker.deleteUser(1);
            }
            catch (DataException exp)
            {
                FileWorker.WriteError(exp.getMessage());
            }

            try {
                SQLWorker.editUser(1, "surname", "test");
            }
            catch(DataException exp)
            {
                FileWorker.WriteError(exp.getMessage());
            }
            SQLWorker.addUser("Вера", "Калинина", "Сноу", "Hellow", "123");
        }
        catch(SQLException ex)
        {
            FileWorker.WriteError(ex.getMessage());
        }
    }
}