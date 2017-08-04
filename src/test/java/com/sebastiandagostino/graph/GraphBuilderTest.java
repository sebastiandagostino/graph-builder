package com.sebastiandagostino.graph;

import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class GraphBuilderTest {

    private static final int MAX_RANDOM = 500;

    @Test
    public void testCliqueBuilder() {
        int cliqueSize = 20;
        int startNodeId = 15;
        int maxRandomLatency = 500;
        LatencyRandomParams params = new LatencyRandomParams(maxRandomLatency, maxRandomLatency);
        Clique clique = GraphBuilder.buildClique(params, cliqueSize, startNodeId);

        assertEquals(cliqueSize, clique.getNodes().size());
        for (int i = startNodeId; i < (cliqueSize + startNodeId); i++) {
            assertTrue(clique.getNodes().contains(new Node(i, 1, 1)));
            for (Node node : clique.getNodes()) {
                assertFalse(node.getLinks().contains(new Link(node.getId(), 1)));
                assertEquals(cliqueSize - 1, node.getLinks().size());
            }
        }
    }

    @Test
    public void testGraphBuilder() {
        // TODO: Test
        int graphSize = 54;
        int cliqueSize = 3;
        int outboundLinksPerClique = 6;
        Graph graph = GraphBuilder.buildGraph(graphSize, cliqueSize, outboundLinksPerClique, MAX_RANDOM, MAX_RANDOM);
        System.out.println(graph);

        assertNotNull(graph);
        assertEquals(graphSize, graph.getNodes().size());
        System.out.println(graph.getAllMaximalCliques());
        //assertEquals(cliqueSize, graph.getAllMaximalCliques().size());
        int cliqueAmount = graphSize / cliqueSize;
        int linkAmount = (cliqueAmount - 1) * cliqueSize + outboundLinksPerClique;
        System.out.println(cliqueAmount);
        System.out.println((cliqueAmount -1) * cliqueSize);
        //assertEquals(linkAmount, graph.getNodes().stream().map(Node::getLinks).mapToInt(Collection::size).sum());
    }

}
