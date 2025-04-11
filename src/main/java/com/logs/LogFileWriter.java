package com.logs;

import com.orders.Order;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFileWriter implements LogObserver {
    String filename;

    /**
     * Instantiates a new Log file writer.
     *
     * @param filename the filename
     */
    public LogFileWriter(String filename) {
        this.filename = filename;
    }

    /**
     * Update the log buffer.
     *
     * @param logBuffer the log buffer
     */
    @Override
    public void update(LogEntryBuffer logBuffer) {

        File file = new File(filename);

        try {
            // Check if the file exists
            if (!file.exists()) {
                // Create the file if it doesn't exist
                file.createNewFile();
            }

            // Write to the file in append mode
            try (FileWriter writer = new FileWriter(filename, true)) {
                for (Order entry : logBuffer.getEntries()) {
                    writer.write(entry.getLogInfo() + "\n");
                }
                logBuffer.clearEntries();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
