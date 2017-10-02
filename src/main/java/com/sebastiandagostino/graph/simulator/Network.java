package com.sebastiandagostino.graph.simulator;

import java.util.*;

public class Network {

    private int masterTime;

    private List<Event> messages;

    public Network() {
        this.masterTime = 0;
        this.messages = new LinkedList();
    }

    public int getMasterTime() {
        return masterTime;
    }

    public void setMasterTime(int masterTime) {
        this.masterTime = masterTime;
    }

    public void sendMessage(Message message, Link link, int sendTime) {
        if (message.getToNodeId() == link.getToNodeId()) {
            link.setSendTime(sendTime);
            Event event = new Event(link.getReceiveTime());
            event.getMessages().add(message);
            messages.add(event);
            // TODO: Improve the performance of this line
            Collections.sort(messages);
            link.getMessages().addAll(event.getMessages());
        }
    }

    public int countMessages() {
        return messages.size();
    }

    public int countMessagesOnTheWire() {
        int messageCount = 0;
        for (Event event : messages) {
            messageCount += event.getMessages().size();
        }
        return messageCount;
    }

    public Collection<Event> getMessages() {
        return messages;
    }

}
