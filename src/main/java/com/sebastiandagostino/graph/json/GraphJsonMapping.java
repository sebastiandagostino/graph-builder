package com.sebastiandagostino.graph.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastiandagostino.graph.Graph;
import com.sebastiandagostino.graph.Node;

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
    private List<NodeJsonMapping> nodes;

    @JsonProperty("links")
    private List<LinkJsonMapping> links;

    public GraphJsonMapping(@JsonProperty("numNodes") int numNodes, @JsonProperty("unlThresh") int unlThresh,
            @JsonProperty("nodes") List<NodeJsonMapping> nodes, @JsonProperty("links") List<LinkJsonMapping> links) {
        this.numNodes = numNodes;
        this.unlThresh = unlThresh;
        this.nodes = nodes;
        this.links = links;
    }

    public GraphJsonMapping(Graph graph, int unlThresh) {
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
        Collection<Node> nodes = graph.getNodes();
        this.numNodes = nodes.size();
        this.unlThresh = unlThresh;
        nodes.stream().forEach(node -> {
            NodeJsonMapping nodeJsonMapping = new NodeJsonMapping(node.getId(), node.getVote(), node.getLatency());
            nodeJsonMapping.getUniqueNodeList()
                    .addAll(node.getUniqueNodeList().getUNL().stream().map(unl -> unl.getId()).collect(Collectors.toList()));
            this.nodes.add(nodeJsonMapping);
            this.links.addAll(node.getLinks()
                    .stream().map(link -> new LinkJsonMapping(node.getId(), link.getToNodeId(), link.getLatency()))
                    .collect(Collectors.toList()));
        });

    }

    public int getUnlThresh() {
        return unlThresh;
    }

    public List<NodeJsonMapping> getNodes() {
        return nodes;
    }

    public List<LinkJsonMapping> getLinks() {
        return links;
    }

    @JsonIgnore
    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
