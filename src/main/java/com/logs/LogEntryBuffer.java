package com.logs;

import com.gameplay.Order;

import java.util.ArrayList;
import java.util.List;

public class LogEntryBuffer{
    public List<LogObserver> observers = new ArrayList<>();
    public List<Order> logbuffer = new ArrayList<>();

    public void addObserver(LogObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LogObserver observer) {
        observers.remove(observer);
    }

    public void addEntry(Order entry) {
        logbuffer.add(entry);
        System.out.println(entry);
        notifyObservers();
    }

    private void notifyObservers() {
        for (LogObserver observer : observers) {
            observer.update(this);
        }
    }

    public List<Order> getEntries() {
        return logbuffer;
    }

    public void clearEntries(){
        logbuffer.clear();
    }
}
