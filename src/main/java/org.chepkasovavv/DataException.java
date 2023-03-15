package org.chepkasovavv;

public class DataException extends Exception{
    private int number;
    public int getNumber(){return number;}

    public DataException(String message, int num){

        super(message);
        number=num;
    }
}