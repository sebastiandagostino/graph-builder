package com.sebastiandagostino.graph.simulator;

import java.util.ArrayList;
import java.util.List;

public class Event {

    // TODO: Add Equals and HashCode?
    private List<Message> messages;

    public Event() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

}
