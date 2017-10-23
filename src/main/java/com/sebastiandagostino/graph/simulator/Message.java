package com.sebastiandagostino.graph.simulator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private int fromNodeId;

    private int toNodeId;

    private Map<Integer, NodeState> data;

    public Message(int fromNodeId, int toNodeId) {
        this(fromNodeId, toNodeId, new HashMap<>());
    }

    public Message(int fromNodeId, int toNodeId, Map<Integer, NodeState> data) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.data = data;
    }

    public int getFromNodeId() {
        return fromNodeId;
    }

    public int getToNodeId() {
        return toNodeId;
    }

    public Map<Integer, NodeState> getData() {
        return data;
    }

    public boolean hasEmptyData() {
        return data.isEmpty();
    }

    public void insertData(int nodeId) {
        this.data.put(nodeId, new NodeState(nodeId));
    }

    void addPositions(Map<Integer, NodeState> update) {
        for (Map.Entry<Integer, NodeState> updateState : update.entrySet()) {
            if (updateState.getKey() != this.toNodeId) {
                // don't tell a node about itself
                NodeState msgIt = data.get(updateState.getKey());
                if (msgIt != null) {
                    // we already had data about this node going in this message
                    msgIt.updateStateIfTimeStampIsHigher(updateState.getValue());
                } else {
                    data.put(updateState.getKey(), updateState.getValue());
                }
            }
        }
    }

    void subPositions(Map<Integer, NodeState> received) {
        // we received this information from this node, so no need to send it
        for (Map.Entry<Integer, NodeState> receivedState : received.entrySet()) {
            if (receivedState.getKey() != this.toNodeId) {
                NodeState msgIt = data.get(receivedState.getKey());
                if (msgIt != null && (receivedState.getValue().getTimestamp() >= msgIt.getTimestamp())) {
                    data.remove(receivedState.getKey()); // The node doesn't need the data: msgIt
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return new EqualsBuilder().append(fromNodeId, message.fromNodeId).append(toNodeId, message.toNodeId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(fromNodeId).append(toNodeId).toHashCode();
    }

    @Override
    public String toString() {
        return "Message{" +
                "fromNodeId=" + fromNodeId +
                ", toNodeId=" + toNodeId +
                ", data=" + data +
                '}';
    }

}
