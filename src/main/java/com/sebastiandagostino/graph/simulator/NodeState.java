package com.sebastiandagostino.graph.simulator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class NodeState {

    private int nodeId;

    private int timeStamp;

    private int state;

    public NodeState(int nodeId, int timeStamp, int state) {
        this.nodeId = nodeId;
        this.timeStamp = timeStamp;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public int getState() {
        return state;
    }

    public void updateStateIfTimeStampIsHigher(NodeState nodeState) {
        if (this.nodeId == nodeState.getNodeId() && nodeState.getTimeStamp() > this.getTimeStamp()) {
            this.timeStamp = nodeState.getTimeStamp();
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

}
