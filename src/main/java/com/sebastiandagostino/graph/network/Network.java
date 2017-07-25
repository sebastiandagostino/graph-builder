package com.sebastiandagostino.graph.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastiandagostino.graph.Graph;
import com.sebastiandagostino.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Network {

    @JsonProperty("numNodes")
    private int numNodes;

    @JsonProperty("unlThresh")
    private int unlThresh;

    @JsonProperty("nodes")
    private List<NetworkNode> nodes;

    @JsonProperty("links")
    private List<NetworkLink> links;

    public Network(Graph graph, int unlThresh) {
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

}
