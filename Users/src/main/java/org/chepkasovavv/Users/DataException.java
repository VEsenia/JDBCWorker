package org.chepkasovavv.Users;

public class DataException extends Exception{
    private int number;
    public int getNumber(){return number;}

    public DataException(String message, Throwable cause, int num){

        super(message, cause);
        number=num;
    }

    public DataException(String message, int num){

        super(message);
        number=num;
    }
}