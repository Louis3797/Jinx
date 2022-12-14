package org.jinx.logging_file_handler;

import org.jinx.logging_formatter.LogFileFormatter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Class implements the Singleton pattern
 */
public class LogFileHandler {
    private static final LogFileHandler instance = new LogFileHandler();
    private Logger l = Logger.getLogger(LogFileHandler.class.getName());
    private static FileHandler fh;
    private static final String file = "logs.log";

    /**
     * Standard Constructor
     */
    private LogFileHandler() {
        try {
            fh = new FileHandler(file, true);
            fh.setFormatter(new LogFileFormatter());
        } catch (IOException e) {
            l.severe(e.getMessage());
        }
    }


    public static LogFileHandler getInstance() {
        return instance;
    }

    public FileHandler getFileHandler() {
        return fh;
    }
}
