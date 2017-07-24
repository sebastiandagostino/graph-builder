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

    public Network() {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public Network(Graph graph, int unlThresh) {
        this();
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

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public int getUnlThresh() {
        return unlThresh;
    }

    public void setUnlThresh(int unlThresh) {
        this.unlThresh = unlThresh;
    }

    public List<NetworkNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<NetworkNode> nodes) {
        this.nodes = nodes;
    }

    public List<NetworkLink> getLinks() {
        return links;
    }

    public void setLinks(List<NetworkLink> links) {
        this.links = links;
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
