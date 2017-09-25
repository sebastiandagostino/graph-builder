package com.sebastiandagostino.graph.simulator;

import java.util.Map;
import java.util.TreeMap;

public class Network {

    private int masterTime;

    private Map<Integer, Event> messages;

    public Network() {
        this.masterTime = 0;
        this.messages = new TreeMap<>();
    }

    public int getMasterTime() {
        return masterTime;
    }

    public void setMasterTime(int masterTime) {
        this.masterTime = masterTime;
    }

    public void sendMessage(Message message, Link link, int sendTime) {
        assert(message.getToNodeId() == link.getToNodeId());

        link.setSendTime(sendTime);

        Event event = new Event();
        event.addMessage(message);

        messages.put(link.getReceiveTime(), event);

        link.getMessages().addAll(event.getMessages());
    }

    public int countMessages() {
        return messages.size();
    }

    public int countMessagesOnTheWire() {
        int mc = 0;
        for (Map.Entry<Integer, Event> entry : messages.entrySet()) {
            mc += entry.getValue().getMessages().size();
        }
        return mc;
    }

    public Map<Integer, Event> getMessages() {
        return messages;
    }

}
