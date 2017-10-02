package com.sebastiandagostino.graph.simulator;

import org.apache.commons.lang3.Validate;

import java.util.*;

public class Network {

    private final int consensusPercent;

    private int masterTime;

    private Map<Integer, Node> nodes;

    private List<Event> messages;

    public Network(Map<Integer, Node> nodes, int consensusPercent) {
        Validate.isTrue(0 <= consensusPercent && consensusPercent <= 100);
        this.consensusPercent = consensusPercent;
        this.masterTime = 0;
        this.nodes = nodes;
        this.messages = new LinkedList();
    }

    public int getConsensusPercent() {
        return consensusPercent;
    }

    public int getMasterTime() {
        return masterTime;
    }

    public void setMasterTime(int masterTime) {
        this.masterTime = masterTime;
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public int countVotes(NodeState.Vote vote) {
        int count = 0;
        for (Node node : nodes.values()) {
            if (node.getVote() == vote) {
                count++;
            }
        }
        return count;
    }

    public boolean isConsensusReached() {
        int countLimit = getNodes().size() * consensusPercent / 100;
        if (countVotes(NodeState.Vote.POSITIVE) > countLimit || countVotes(NodeState.Vote.NEGATIVE) > countLimit) {
            return true;
        }
        return false;
    }

    public void sendMessage(Message message, Link link) {
        this.sendMessage(message, link, 0);
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

    public int countMessagesSent() {
        int count = 0;
        for (Node node : getNodes().values()) {
            count += node.getMessagesSent();
        }
        return count;
    }

    public Collection<Event> getMessages() {
        return messages;
    }

}
