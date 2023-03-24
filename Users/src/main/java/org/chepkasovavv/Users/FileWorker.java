package org.chepkasovavv.Users;

import java.io.*;
import java.util.Date;

public class FileWorker {
    public static void WriteError(String error, Throwable cause)
    {
        try(FileWriter writer = new FileWriter(Connector.fileName, true))
        {
            Date curDate = new Date();
            writer.write(curDate.toString() + " " + error);
            writer.write(System.lineSeparator());
            if(cause!=null) {
                writer.write(cause.getMessage());
                writer.write(System.lineSeparator());
            }
            writer.write(System.lineSeparator());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
