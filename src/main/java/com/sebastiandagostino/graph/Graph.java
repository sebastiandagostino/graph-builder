package com.sebastiandagostino.graph;

import com.sebastiandagostino.graph.network.Network;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    public static final int DEFAULT_UNL_THRESH = 2;

    public static final double UNL_INTERSECTION_PERCENTAGE = 0.2;

    private final List<Node> nodes;

    private int unlThresh;

    public Graph() {
        this.unlThresh = DEFAULT_UNL_THRESH;
        this.nodes = new ArrayList();
    }
    
    public Graph(Collection nodes) {
        this(nodes, DEFAULT_UNL_THRESH);
    }

    public Graph(Collection nodes, int unlThresh) {
        this.unlThresh = unlThresh;
        this.nodes = new LinkedList<>(nodes);
    }

    public int getUnlThresh() {
        return unlThresh;
    }

    public Collection<Node> getNodes() {
        return this.nodes;
    }
    
    /**
     * This method uses the Bron-Kerbosch clique detection algorithm as it is described in
     * Samudrala R.,Moult J.:A Graph-theoretic Algorithm for comparative Modeling of Protein Structure
     */
    public List<Clique> getAllMaximalCliques() {
        DefaultDirectedGraph<Node, DefaultEdge> jgraph = this.toJGraph();
        BronKerboschCliqueFinder cliqueFinder = new BronKerboschCliqueFinder(jgraph);
        return (List<Clique>) cliqueFinder.getAllMaximalCliques()
                .stream().map(clique -> new Clique((Collection<Node>) clique)).collect(Collectors.toList());
    }

    /**
     * Convert to JGraph considering UNL as connections
     */
    private DefaultDirectedGraph<Node, DefaultEdge> toJGraph() {
        DefaultDirectedGraph<Node, DefaultEdge> graph = 
                new DefaultDirectedGraph<>(DefaultEdge.class);
        this.nodes.stream().forEach(node->graph.addVertex(node));
        this.nodes.stream().forEach(node->node.getUniqueNodeList().getUNL()
                .stream().forEach(unl->graph.addEdge(node, unl)));
        return graph;
    }

    @Override
    public String toString() {
        return (new Network(this, this.getUnlThresh())).toString();
    }

    public void calculateForkPossibility() {
        // TODO: Needs refactoring - unused method
        for(Node mainNode : this.getNodes()) {
            for(Node otherNode : this.getNodes()) {
                if (mainNode != otherNode && mainNode.getUniqueNodeList().getUNL().contains(otherNode)) {
                    Collection<Node> intersection = mainNode.getUniqueNodeListIntersection(otherNode);
                    System.out.print("Intersection (" + mainNode.getId() + ", " + otherNode.getId() + ") : ");
                    System.out.print(intersection.size() + " >= ");
                    System.out.print("Coefficient(" + UNL_INTERSECTION_PERCENTAGE + ") * ");
                    System.out.print("Max (" + mainNode.getUniqueNodeList().size() + ", "
                            + otherNode.getUniqueNodeList().size() + ") => ");
                    boolean fork = intersection.size() >= UNL_INTERSECTION_PERCENTAGE *
                            Math.max(mainNode.getUniqueNodeList().size(), otherNode.getUniqueNodeList().size());
                    System.out.println(fork);
                }
            }
        }
    }

}
