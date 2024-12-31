package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
    private static Log instance; // Singleton instance
    private StringBuilder logBuffer; // Buffer to store log messages
    private final String logFileName = "log.txt"; // Filename

    private Log() {
        logBuffer = new StringBuilder();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void log(String message) {
        logBuffer.append(message).append("\n");
        System.out.println(message); 
        appendToLogFile(message);    
    }

    private void appendToLogFile(String message) {
        try (FileWriter writer = new FileWriter(new File(logFileName), true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getLogBufferContents() {
        return logBuffer.toString();
    }

   
}

