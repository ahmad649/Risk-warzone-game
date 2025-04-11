package com.logs;

import com.orders.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * LogEntryBuffer class to store the log entries and notify the observers.
 */
public class LogEntryBuffer{
    /**
     * List of observers.
     */
    public List<LogObserver> observers = new ArrayList<>();
    /**
     * List of log entries.
     */
    public List<Order> logbuffer = new ArrayList<>();

    /**
     * Add observer.
     *
     * @param observer the observer
     */
    public void addObserver(LogObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove observer.
     *
     * @param observer the observer
     */
    public void removeObserver(LogObserver observer) {
        observers.remove(observer);
    }

    /**
     * Add entry.
     *
     * @param entry the entry
     */
    public void addEntry(Order entry) {
        logbuffer.add(entry);
        notifyObservers();
    }

    /**
     * Notify observers.
     */
    private void notifyObservers() {
        for (LogObserver observer : observers) {
            observer.update(this);
        }
    }

    /**
     * Get entries list.
     *
     * @return the list of log entries
     */
    public List<Order> getEntries() {
        return logbuffer;
    }

    /**
     * Clear entries.
     */
    public void clearEntries(){
        logbuffer.clear();
    }
}
