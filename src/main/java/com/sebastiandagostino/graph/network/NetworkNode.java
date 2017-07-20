package com.sebastiandagostino.graph.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class NetworkNode {

    @JsonProperty("nodeId")
    private int nodeId;

    @JsonProperty("vote")
    private int vote;

    @JsonProperty("latency")
    private int latency;

    @JsonProperty("uniqueNodeList")
    private List<Integer> uniqueNodeList;

    public NetworkNode(int nodeId, int vote, int latency) {
        this.nodeId = nodeId;
        this.vote = vote;
        this.latency = latency;
        this.uniqueNodeList = new ArrayList<>();
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public List<Integer> getUniqueNodeList() {
        return uniqueNodeList;
    }

    public void setUniqueNodeList(List<Integer> uniqueNodeList) {
        this.uniqueNodeList = uniqueNodeList;
    }

}
