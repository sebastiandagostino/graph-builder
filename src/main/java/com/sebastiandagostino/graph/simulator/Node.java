package com.sebastiandagostino.graph.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    public static final int NUM_MALICIOUS_NODES = 15;

    // extra time we delay a message to coalesce/suppress
    public static final int BASE_DELAY = 1;

    // how many UNL votes you give yourself
    public static final int SELF_WEIGHT = 1;

    // how many packets can be "on the wire" per link per direction
    // simulates non-infinite bandwidth
    public static final int PACKETS_ON_WIRE = 3;

    private int id;

    private int latency; // E2C - End to core latency, the latency from a node to a nearby

    private NodeState.Vote vote;

    private int timestamp;

    private List<Integer> uniqueNodeList;

    private List<Link> links;

    private int messagesSent;

    private int messagesReceived;

    public Node(int id, int latency, NodeState.Vote vote) {
        this.id = id;
        this.latency = latency;
        this.vote = vote;
        this.timestamp = 0;
        this.uniqueNodeList = new ArrayList<>();
        this.links = new ArrayList<>();
        this.messagesSent = 0;
        this.messagesReceived = 0;
    }

    public int getId() {
        return id;
    }

    public int getLatency() {
        return latency;
    }

    public int getMessagesSent() {
        return messagesSent;
    }

    public void decreaseMessagesSent() {
        this.messagesSent--;
    }

    public List<Integer> getUniqueNodeList() {
        return uniqueNodeList;
    }

    public List<Link> getLinks() {
        return links;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void incrementTimestamp() {
        this.timestamp++;
    }

    public int getMessagesReceived() {
        return messagesReceived;
    }

    public boolean isOnUNL(int nodeId) {
        for (int v : this.uniqueNodeList) {
            if (v == nodeId) {
                return true;
            }
        }
        return false;
    }

    public boolean hasLinkTo(int nodeId) {
        for (Link link : links) {
            if (link.getToNodeId() == nodeId) {
                return true;
            }
        }
        return false;
    }

    public NodeState.Vote getVote() {
        return this.vote;
    }

    public void setVote(NodeState.Vote vote) {
        this.vote = vote;
    }

    public void receiveMessage(Message message, Network network, int unlThresh) {
        messagesReceived++;

        Map<Integer, Node> nodes = network.getNodes();

        // If we were going to send any of this data to that node, skip it
        for (Link link : links) {
            if ((link.getToNodeId() == message.getFromNodeId()) && (link.getSendTime() >= network.getMasterTime())) {
                // We can still update a waiting outbound message
                link.getMessages().stream().findFirst().get().subPositions(message.getData());
                break;
            }
        }

        // 1) Update our knowledge
        Map<Integer, NodeState> changes = new HashMap<>();

        for (Map.Entry<Integer, NodeState> change : message.getData().entrySet()) {
            if ((change.getKey() != id)
                    && (nodes.get(change.getKey()).getVote() != change.getValue().getState())
                    && (change.getValue().getTimestamp() > nodes.get(change.getKey()).getTimestamp())) {
                // This gives us new information about a node
                nodes.get(change.getKey()).setVote(change.getValue().getState());
                nodes.get(change.getKey()).setTimestamp(change.getValue().getTimestamp());
                changes.put(change.getKey(), change.getValue());
            }
        }

        if (changes.isEmpty()) {
            return; // nothing changed
        }

        // 2) Choose our position change, if any
        int unlCount = 0;
        int unlBalance = 0;
        for (int node : uniqueNodeList) {
            if (nodes.get(node).getVote() == NodeState.Vote.POSITIVE) {
                unlCount++;
                unlBalance++;
            }
            if (nodes.get(node).getVote() == NodeState.Vote.NEGATIVE) {
                unlCount++;
                unlBalance--;
            }
        }

        if (id < NUM_MALICIOUS_NODES)  {
            // if we are a malicious node, be contrarian
            unlBalance = -unlBalance;
        }

        // add a bias in favor of 'no' as time passes (agree to disagree)
        unlBalance -= network.getMasterTime() / 250;

        boolean positionChange = false;
        if (unlCount >= unlThresh) { // We have enough data to make decisions
            if ((nodes.get(id).getVote() == NodeState.Vote.POSITIVE) && (unlBalance < (-SELF_WEIGHT))) {
                // we switch to -
                nodes.get(id).setVote(NodeState.Vote.NEGATIVE);
                nodes.get(id).incrementTimestamp();
                changes.put(id, new NodeState(id, nodes.get(id).getTimestamp(), NodeState.Vote.NEGATIVE));
                positionChange = true;
            } else if ((nodes.get(id).getVote() == NodeState.Vote.NEGATIVE) && (unlBalance > SELF_WEIGHT)) {
                // we switch to +
                nodes.get(id).setVote(NodeState.Vote.POSITIVE);
                nodes.get(id).incrementTimestamp();
                changes.put(id, new NodeState(id, nodes.get(id).getTimestamp(), NodeState.Vote.POSITIVE));
                positionChange = true;
            }
        }

        // 3) Broadcast the message
        for (Link link : links) {
            if (positionChange || (link.getToNodeId() != message.getFromNodeId())) {
                int sendTime = network.getMasterTime();
                // can we update an unsent message?
                if (link.getSendTime() > sendTime) {
                    link.getMessages().stream().findFirst().get().addPositions(changes);
                } else {
                    // No, we need a new message
                    if (!positionChange) {
                        // delay the message a bit to permit coalescing and suppression
                        sendTime += BASE_DELAY;
                        if (link.getReceiveTime() > sendTime) // a packet is on the wire
                            sendTime += link.getTotalLatency() / PACKETS_ON_WIRE; // wait a bit extra to send
                    }
                    network.sendMessage(new Message(id, link.getToNodeId(), changes), link, sendTime);
                    messagesSent++;
                }
            }
        }

    }

}
