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

    public List<Integer> getUniqueNodeList() {
        return uniqueNodeList;
    }

}
