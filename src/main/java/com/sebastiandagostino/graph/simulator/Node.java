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

    private int nodeId;

    private int latency; // E2C - End to core latency, the latency from a node to a nearby node

    private List<Integer> uniqueNodeList;

    private List<Link> links;

    private List<Integer> nodeTimeStamps;

    private List<Integer> nodeStates;

    private int messagesSent;

    private int messagesReceived;

    private int vote;

    public Node(int nodeId, int numNodes, int latency) {
        this.nodeId = nodeId;
        this.latency = latency;
        this.uniqueNodeList = new ArrayList<>();
        this.links = new ArrayList<>();
        this.nodeTimeStamps = new ArrayList<>(numNodes);
        this.nodeStates = new ArrayList<>(numNodes);
        this.messagesSent = 0;
        this.messagesReceived = 0;
        this.vote = 0;
    }

    public int getNodeId() {
        return nodeId;
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

    public List<Integer> getNodeTimeStamps() {
        return nodeTimeStamps;
    }

    public List<Integer> getNodeStates() {
        return nodeStates;
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

    public int getVote() {
        return this.vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String toJsonString() {
        // Begin
        String string = "{ ";
        // NodeId
        string += "\"nodeId\": " + nodeId + ", ";
        // Vote
        string += "\"vote\": " + vote + ", ";
        // Latency
        string += "\"latency\": " + latency + ", ";
        // UNL
        int size = uniqueNodeList.size();
        string += "\"uniqueNodeList\": [ ";
        for (int i = 0; i < size; i++) {
            string += uniqueNodeList.get(i);
            if (i != size - 1) {
                string += ", ";
            }
        }
        string += "] ";
        // End
        string += " }";
        return string;
    }

    public String toLinkString() {
        String string = "{ ";
        int size = links.size();
        string += "\"links\": [";
        for (int i = 0; i < size; i++) {
            string += " { \"nodeId\": " + links.get(i).getToNodeId() + ", ";
            string += "\"totalLatency\": " + links.get(i).getTotalLatency() + ", ";
            string += "\"sendTime\": " + links.get(i).getSendTime() + " }";
            if (i != size - 1) {
                string += ", ";
            } else {
                string += " ";
            }
        }
        string += "] ";
        string += " }";
        return string;
    }

    public void receiveMessage(Message message, Network network, int unlThresh) {
        messagesReceived++;

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

        for (Map.Entry<Integer, NodeState> chgIt : message.getData().entrySet()) {
            if ((chgIt.getKey() != nodeId)
                    && (nodeStates.get(chgIt.getKey()) != chgIt.getValue().getState())
                    && (chgIt.getValue().getTimeStamp() > nodeTimeStamps.get(chgIt.getKey()))) {
                // This gives us new information about a node
                nodeStates.set(chgIt.getKey(), chgIt.getValue().getState());
                nodeTimeStamps.set(chgIt.getKey(), chgIt.getValue().getTimeStamp());
                changes.put(chgIt.getKey(), chgIt.getValue());
            }
        }

        if (changes.isEmpty()) {
            return; // nothing changed
        }

        // 2) Choose our position change, if any
        int unlCount = 0;
        int unlBalance = 0;
        for (int node : uniqueNodeList) {
            if (nodeStates.get(node) == 1) {
                unlCount++;
                unlBalance++;
            }
            if (nodeStates.get(node) == -1) {
                unlCount++;
                unlBalance--;
            }
        }

        if (nodeId < NUM_MALICIOUS_NODES)  {
            // if we are a malicious node, be contrarian
            unlBalance = -unlBalance;
        }

        // add a bias in favor of 'no' as time passes (agree to disagree)
        unlBalance -= network.getMasterTime() / 250;

        boolean positionChange = false;
        if (unlCount >= unlThresh) { // We have enough data to make decisions
            if ((nodeStates.get(nodeId) == 1) && (unlBalance < (-SELF_WEIGHT))) {
                // we switch to -
                nodeStates.set(nodeId, -1);
                vote = -1;
                nodeTimeStamps.set(nodeId, nodeTimeStamps.get(nodeId) + 1);
                changes.put(nodeId, new NodeState(nodeId, nodeTimeStamps.get(nodeId), -1));
                positionChange = true;
            } else if ((nodeStates.get(nodeId) == -1) && (unlBalance > SELF_WEIGHT)) {
                // we switch to +
                nodeStates.set(nodeId, 1);
                vote = 1;
                nodeTimeStamps.set(nodeId, nodeTimeStamps.get(nodeId) + 1);
                changes.put(nodeId, new NodeState(nodeId, nodeTimeStamps.get(nodeId), +1));
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
                    network.sendMessage(new Message(nodeId, link.getToNodeId(), changes), link, sendTime);
                    messagesSent++;
                }
            }
        }

    }

}
