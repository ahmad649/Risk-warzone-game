package com.logs;

public interface LogObserver {
    void update(LogEntryBuffer logBuffer);
}
