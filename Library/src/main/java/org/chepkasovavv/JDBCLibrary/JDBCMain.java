package org.chepkasovavv.JDBCLibrary;

import org.chepkasovavv.Users.ErrorStruct;
import org.chepkasovavv.Users.FileWorker;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.osgi.PGDataSourceFactory;

import java.sql.SQLException;

public class JDBCMain {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5432;
        String databaseName = "users";
        String user = "tester";
        String password = "tester";

        JDBCDataSource jdbcDataSource = new JDBCDataSource(host, port, databaseName, user, password);
        PGSimpleDataSource dataSorce = jdbcDataSource.dataSource();
        JDBCWorker jdbcWorker = new JDBCWorker();

        //Удаление
        int idDel = 15;
        String queryDelete = "DELETE FROM users WHERE id_user = " + idDel;
        String strErrDel = String.format("Нет записи для удаления по id=%d\n",
                idDel);
        ErrorStruct errorDel = new ErrorStruct(strErrDel, 0);
        jdbcWorker.MakeQuery(dataSorce, queryDelete, errorDel);

        //Вставка
        String name = "tes666";
        String surname = "Калинина";
        String thirdName = "Сноу";
        String login = "test";
        String pass = "123";
        String queryAdd = String.format("INSERT INTO users (name, surname, thirdname, login, password) " +
                        "VALUES ('%s', '%s', '%s', '%s', '%s')",
                name, surname, thirdName, login, pass);

        String strErrAdd = String.format("Не возможно добавить пользователя с данными %s, %s, %S, %s. %s \n",
                name, surname, thirdName, login, password);
        ErrorStruct errorAdd = new ErrorStruct(strErrAdd, 3);
        jdbcWorker.MakeQuery(dataSorce, queryAdd, errorAdd);

        //Редактирование
        int idEdit = 7;
        String columnNameToChange = "name";
        String strValueToChange = "Маша";
        String queryEdit = String.format("UPDATE users  " +
                " SET " + columnNameToChange + " = '%s' " +
                " WHERE id_user" + " = %s", strValueToChange, idEdit);;

        String strErrEdit = String.format("Не найдена запись для обновления по id=%s\n",
                idEdit);
        ErrorStruct errorEdit = new ErrorStruct(strErrEdit, 1);
        jdbcWorker.MakeQuery(dataSorce, queryEdit, errorEdit);

        //Вывод
        String query = "select id_user as id, name, surname, thirdname, login, password  from users;";
        String[] strNames = {"id", "name", "surname", "thirdname","login", "password"};
        WriteDataConsole writeDataConsole = new WriteDataConsole();
        jdbcWorker.ReceiveData(dataSorce, query, strNames, writeDataConsole);
    }
}
