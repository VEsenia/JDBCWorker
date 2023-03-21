package org.chepkasovavv;

public class DataErrors {
    private static final DataErrors instance = new DataErrors();

    // private constructor to avoid client applications using the constructor
    private DataErrors(){}

    public static DataErrors getInstance() {
        return instance;
    }

    //почему нельзя сделать private?

    public static ErrorStruct deleteError = new ErrorStruct("Нет записи для удаления по id=", 0);
    public static ErrorStruct updateError = new ErrorStruct("Не найдена запись для обновления по id=", 1);;
}
