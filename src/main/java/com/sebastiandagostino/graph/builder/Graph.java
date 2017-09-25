package com.sebastiandagostino.graph.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastiandagostino.graph.json.GraphJsonMapping;
import com.sebastiandagostino.graph.json.LinkJsonMapping;
import com.sebastiandagostino.graph.json.NodeJsonMapping;
import org.apache.commons.lang3.Validate;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Graph {

    public static final int DEFAULT_UNL_THRESH = 3;

    private final List<Node> nodes;

    private int unlThresh;

    public Graph(int unlThresh) {
        this.unlThresh = unlThresh;
        this.nodes = new ArrayList();
    }

    public Graph() {
        this.unlThresh = DEFAULT_UNL_THRESH;
        this.nodes = new ArrayList();
    }

    public Graph(Collection nodes) {
        this(nodes, DEFAULT_UNL_THRESH);
    }

    public Graph(Collection nodes, int unlThresh) {
        this.unlThresh = unlThresh;
        this.nodes = new LinkedList(nodes);
    }

    public Graph(String jsonString) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final GraphJsonMapping readValue = mapper.readValue(jsonString, GraphJsonMapping.class);
            this.unlThresh = readValue.getUnlThresh();
            this.nodes = new LinkedList<>();
            for (NodeJsonMapping networkNode : readValue.getNodes()) {
                Node node = new Node(networkNode.getNodeId(), networkNode.getVote(), networkNode.getLatency());
                for (Integer nodeId : networkNode.getUniqueNodeList()) {
                    node.getUniqueNodeList().add(new Node(nodeId, 1, 1)); // default unnecessary values
                }
                this.nodes.add(node);
            }
            for (LinkJsonMapping networkLink : readValue.getLinks()) {
                this.nodes.stream().forEach(node -> {
                    if (node.getId() == networkLink.getFrom()) {
                        node.addLink(new Link(networkLink.getTo(), networkLink.getLatency()));
                    }
                });
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public int getUnlThresh() {
        return unlThresh;
    }

    public Collection<Node> getNodes() {
        return this.nodes;
    }

    /**
     * This method uses the Bron-Kerbosch clique detection algorithm
     */
    public List<Clique> getAllMaximalCliques() {
        DefaultDirectedGraph<Node, DefaultEdge> jGraph = this.toJGraph();
        BronKerboschCliqueFinder cliqueFinder = new BronKerboschCliqueFinder(jGraph);
        return (List<Clique>) cliqueFinder.getAllMaximalCliques().stream().map(clique -> new Clique((Collection<Node>) clique))
                .collect(Collectors.toList());
    }

    public static final int LINK_RANDOM_SEED = 222222222;

    private static Random linkRandom = new Random(LINK_RANDOM_SEED);

    public static final int LINK_LATENCY_SEED = 333333333;

    private static Random linkLatencyRandom = new Random(LINK_LATENCY_SEED);

    /**
     * This applies the algorithm
     */
    public void improveConnectivity(int nodeAmount, int latency) {
        Validate.isTrue(0 <= latency);
        Validate.isTrue(0 < nodeAmount && nodeAmount < this.getNodes().size());
        List<Clique> cliques = this.getAllMaximalCliques();
        List<Clique> filteredCliques = Clique.filterIntersectingNodesFromCliques(cliques);
        int graphSize = this.getNodes().size();
        for (int i = 0; i < nodeAmount; i++) {
            int vote = i % 2 == 0 ? 1 : -1;
            Node node = new Node(this.getNodes().size(), vote, latency);
            for (Clique clique : filteredCliques) {
                node.getUniqueNodeList().addAll(clique.getNodes());
            }
            int unlSize = node.getUniqueNodeList().size();
            for (int j = 0; j < unlSize; j++) {
                node.getLinks().add(new Link(linkRandom.nextInt(graphSize), linkLatencyRandom.nextInt(latency)));
            }
            this.nodes.add(node);
        }
    }

    /**
     * Convert to JGraph considering UNL as connections
     */
    private DefaultDirectedGraph<Node, DefaultEdge> toJGraph() {
        DefaultDirectedGraph<Node, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.nodes.stream().forEach(graph::addVertex);
        this.nodes.stream().forEach(node -> node.getUniqueNodeList().getUNL().stream().forEach(unl -> graph.addEdge(node, unl)));
        return graph;
    }

    @Override
    public String toString() {
        return (new GraphJsonMapping(this, this.getUnlThresh())).toString();
    }

    public static final double UNL_INTERSECTION_PERCENTAGE = 0.2;

    public void calculateForkPossibility() {
        // TODO: Needs refactoring - unused method
        for (Node mainNode : this.getNodes()) {
            for (Node otherNode : this.getNodes()) {
                if (mainNode != otherNode && mainNode.getUniqueNodeList().getUNL().contains(otherNode)) {
                    Collection<Node> intersection = mainNode.getUniqueNodeListIntersection(otherNode);
                    System.out.print("Intersection (" + mainNode.getId() + ", " + otherNode.getId() + ") : ");
                    System.out.print(intersection.size() + " >= ");
                    System.out.print("Coefficient(" + UNL_INTERSECTION_PERCENTAGE + ") * ");
                    System.out.print("Max (" + mainNode.getUniqueNodeList().size() + ", " + otherNode.getUniqueNodeList().size() + ") => ");
                    boolean fork = intersection.size() >= UNL_INTERSECTION_PERCENTAGE * Math
                            .max(mainNode.getUniqueNodeList().size(), otherNode.getUniqueNodeList().size());
                    System.out.println(fork);
                }
            }
        }
    }

}