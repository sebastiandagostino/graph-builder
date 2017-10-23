package com.sebastiandagostino.graph.simulator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NodeState {

    public enum Vote {
        POSITIVE,
        NEGATIVE,
        NEUTRAL; // Just for the starting case

        public static Vote fromInteger(int vote) {
            if (vote > 0) {
                return POSITIVE;
            }
            return NEGATIVE;
        }
    }

    private int nodeId;

    private int timestamp;

    private Vote state;

    public NodeState(int nodeId, int timestamp, Vote state) {
        this.nodeId = nodeId;
        this.timestamp = timestamp;
        this.state = state;
    }

    public NodeState(int nodeId) {
        this(nodeId, 1, Vote.NEUTRAL);
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public Vote getState() {
        return state;
    }

    public void updateStateIfTimeStampIsHigher(NodeState nodeState) {
        if (this.nodeId == nodeState.getNodeId() && nodeState.getTimestamp() > this.getTimestamp()) {
            this.timestamp = nodeState.getTimestamp();
            this.state = nodeState.getState();
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
        NodeState nodeState = (NodeState) o;
        return new EqualsBuilder().append(nodeId, nodeState.nodeId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(nodeId).toHashCode();
    }

    @Override
    public String toString() {
        return "NodeState{" +
                "nodeId=" + nodeId +
                ", timestamp=" + timestamp +
                ", state=" + state +
                '}';
    }

}
