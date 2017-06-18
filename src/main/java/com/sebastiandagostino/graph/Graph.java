package com.sebastiandagostino.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Graph {
    
    private final List<Node> nodes;
    
    public Graph() {
        this.nodes = new LinkedList<>();
    }
    
    public Graph(Collection nodes) {
        this.nodes = new LinkedList<>(nodes);
    }
    
    public Collection<Node> getNodes() {
        return this.nodes;
    }
    
    public void calculateForkPossibility() {
        for(Node mainNode : this.getNodes()) {
            for(Node otherNode : this.getNodes()) {
                if (mainNode != otherNode && mainNode.getUniqueNodeList().getUNL().contains(otherNode)) {
                    Collection<Node> intersection = mainNode.getUniqueNodeListIntersection(otherNode);
                    System.out.print("Intersection (" + mainNode.getId() + ", " + otherNode.getId() + ") : ");
                    System.out.print(intersection.size() + " >= ");
                    System.out.print("Coeficient(" + 0.2 + ") * ");
                    System.out.print("Max (" + mainNode.getUniqueNodeList().size() + ", " + otherNode.getUniqueNodeList().size() + ") => ");
                    boolean fork = intersection.size() >= 0.2 * Math.max(mainNode.getUniqueNodeList().size(), otherNode.getUniqueNodeList().size());
                    System.out.println(fork);
                }
            }
        }
    }
    
    public DefaultDirectedGraph<Node, DefaultEdge> toJGraph() {
        DefaultDirectedGraph<Node, DefaultEdge> graph = 
                new DefaultDirectedGraph<>(DefaultEdge.class);
        this.nodes.stream().forEach(node->graph.addVertex(node));
        this.nodes.stream().forEach(node->node.getUniqueNodeList().getUNL().stream().forEach(unl->graph.addEdge(node, unl)));
        return graph;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{\n");
        this.nodes.stream().forEach((node)->stringBuilder
                .append("\t").append(node.toString()).append(", ")
                .append(node.getUniqueNodeList().toString()).append(";\n"));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
    
}
