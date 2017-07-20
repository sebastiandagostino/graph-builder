package com.sebastiandagostino.graph;

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

}
