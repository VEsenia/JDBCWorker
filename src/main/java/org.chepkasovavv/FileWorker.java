package org.chepkasovavv;

import java.io.*;
import java.util.Date;

public class FileWorker {
    public static void WriteError(String error)
    {
        try(FileWriter writer = new FileWriter(Connector.fileName, true))
        {
            Date curDate = new Date();
            writer.write(curDate.toString() + " " + error);
            writer.write(System.lineSeparator());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
