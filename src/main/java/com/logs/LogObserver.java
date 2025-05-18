package com.logs;

/**
 * LogObserver interface to update the log buffer.
 * Implementing classes will define how they update their log entries when notified.
 */
public interface LogObserver {
    /**
     * Updates the log buffer with new log entries.
     * This method is called when the LogEntryBuffer is updated, and it allows observers
     * to perform actions such as writing to a file, displaying the log, etc.
     *
     * @param logBuffer the LogEntryBuffer containing the log entries to be processed
     */
    void update(LogEntryBuffer logBuffer);
}
