package com.sebastiandagostino.graph.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class NodeJsonMapping {

    @JsonProperty("nodeId")
    private int nodeId;

    @JsonProperty("vote")
    private int vote;

    @JsonProperty("latency")
    private int latency;

    @JsonProperty("uniqueNodeList")
    private List<Integer> uniqueNodeList;

    public NodeJsonMapping(@JsonProperty("nodeId") int nodeId, @JsonProperty("vote") int vote, @JsonProperty("latency") int latency,
            @JsonProperty("uniqueNodeList") List<Integer> uniqueNodeList) {
        this.nodeId = nodeId;
        this.vote = vote;
        this.latency = latency;
        this.uniqueNodeList = uniqueNodeList;
    }

    public NodeJsonMapping(int nodeId, int vote, int latency) {
        this.nodeId = nodeId;
        this.vote = vote;
        this.latency = latency;
        this.uniqueNodeList = new ArrayList<>();
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getVote() {
        return vote;
    }

    public int getLatency() {
        return latency;
    }

    public List<Integer> getUniqueNodeList() {
        return uniqueNodeList;
    }

}
