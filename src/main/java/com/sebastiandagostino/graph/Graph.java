package com.sebastiandagostino.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sebastiandagostino.graph.network.Network;
import org.jgrapht.alg.BronKerboschCliqueFinder;
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
                    System.out.print("Coefficient(" + 0.2 + ") * ");
                    System.out.print("Max (" + mainNode.getUniqueNodeList().size() + ", " + otherNode.getUniqueNodeList().size() + ") => ");
                    boolean fork = intersection.size() >= 0.2 * Math.max(mainNode.getUniqueNodeList().size(), otherNode.getUniqueNodeList().size());
                    System.out.println(fork);
                }
            }
        }
    }

    /**
     * This method uses the Bron-Kerbosch clique detection algorithm as it is described in
     * [Samudrala R.,Moult J.:A Graph-theoretic Algorithm for comparative Modeling of Protein Structure; J.Mol.
     */
    public List<Clique> getAllMaximalCliques() {
        DefaultDirectedGraph<Node, DefaultEdge> jgraph = this.toJGraph();
        BronKerboschCliqueFinder cliqueFinder = new BronKerboschCliqueFinder(jgraph);
        System.out.println(cliqueFinder.getAllMaximalCliques());
        return (List<Clique>) cliqueFinder.getAllMaximalCliques()
                .stream().map(clique -> new Clique((Set<Node>) clique)).collect(Collectors.toList());
    }

    private DefaultDirectedGraph<Node, DefaultEdge> toJGraph() {
        DefaultDirectedGraph<Node, DefaultEdge> graph = 
                new DefaultDirectedGraph<>(DefaultEdge.class);
        this.nodes.stream().forEach(node->graph.addVertex(node));
        this.nodes.stream().forEach(node->node.getUniqueNodeList().getUNL().stream().forEach(unl->graph.addEdge(node, unl)));
        return graph;
    }

    public String toJsonString() {
        // TODO: Add UNL Thresh (check coefficient in method "calculateForkPossibility")
        int unlThresh = 2;
        return (new Network(this, unlThresh)).toString();
    }
    
}
