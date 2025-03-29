package com.logs;

/**
 * LogObserver interface to update the log buffer.
 */
public interface LogObserver {
    void update(LogEntryBuffer logBuffer);
}
