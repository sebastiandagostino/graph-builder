package com.sebastiandagostino.graph.simulator;

import java.util.List;

public class Link {

    private int toNodeId;

    private int totalLatency;

    private int sendTime;

    private List<Message> messages;

    public Link(int toNodeId, int totalLatency) {
        this.toNodeId = toNodeId;
        this.totalLatency = totalLatency;
        this.sendTime = 0;
    }

    public int getToNodeId() {
        return toNodeId;
    }

    public int getTotalLatency() {
        return totalLatency;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public int getReceiveTime() {
        return sendTime + totalLatency;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
