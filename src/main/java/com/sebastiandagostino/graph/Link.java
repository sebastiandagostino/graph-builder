package com.sebastiandagostino.graph;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Link {

    private int toNodeId;

    private int latency;

    public Link(int toNodeId, int latency) {
        this.toNodeId = toNodeId;
        this.latency = latency;
    }

    public int getToNodeId() {
        return toNodeId;
    }

    public int getLatency() {
        return latency;
    }

    @Override
    public String toString() {
        return "Link to: " + this.toNodeId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Link) {
            return new EqualsBuilder().append(this.toNodeId, ((Link) obj).toNodeId).isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.toNodeId).toHashCode();
    }

}
