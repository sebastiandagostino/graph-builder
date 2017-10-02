package com.sebastiandagostino.graph.simulator;

import java.util.*;

public class Event implements Comparable<Event> {

    private int receiveTime;

    private List<Message> messages;

    public Event(int receiveTime) {
        this.receiveTime = receiveTime;
        this.messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public int getReceiveTime() {
        return receiveTime;
    }

    @Override
    public int compareTo(Event other) {
        return this.receiveTime - other.receiveTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "receiveTime=" + receiveTime +
                ", messages=" + messages +
                '}';
    }

}
