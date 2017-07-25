package com.sebastiandagostino.graph;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GraphJsonMapping {

    @JsonProperty("numNodes")
    private int numNodes;

    @JsonProperty("unlThresh")
    private int unlThresh;

    @JsonProperty("nodes")
    private List<NetworkNode> nodes;

    @JsonProperty("links")
    private List<NetworkLink> links;

    public GraphJsonMapping(Graph graph, int unlThresh) {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
        Collection<Node> nodes = graph.getNodes();
        this.numNodes = nodes.size();
        this.unlThresh = unlThresh;
        nodes.stream().forEach(node -> {
            NetworkNode networkNode = new NetworkNode(node.getId(), node.getVote(), node.getLatency());
            networkNode.getUniqueNodeList().addAll(
                    node.getUniqueNodeList().getUNL().stream().map(unl -> unl.getId()).collect(Collectors.toList()));
            this.nodes.add(networkNode);
            this.links.addAll(
                    node.getLinks().stream().map(link -> new NetworkLink(node.getId(), link.getToNodeId(),
                            link.getLatency())).collect(Collectors.toList()));
        });

    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public class NetworkLink {

        @JsonProperty("from")
        int from;

        @JsonProperty("to")
        int to;

        @JsonProperty("latency")
        int latency;

        public NetworkLink(int from, int to, int latency) {
            this.from = from;
            this.to = to;
            this.latency = latency;
        }

    }

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

}
