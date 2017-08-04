package com.sebastiandagostino.graph;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class GraphBuilderTest {

    private static final int MAX_RANDOM = 500;

    @Test
    public void testCliqueBuilder() {
        int cliqueSize = 20;
        int startNodeId = 15;
        LatencyRandomParams params = new LatencyRandomParams(MAX_RANDOM, MAX_RANDOM);
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

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCliqueBuilderParameterCliqueSize() {
        LatencyRandomParams params = new LatencyRandomParams(MAX_RANDOM, MAX_RANDOM);
        GraphBuilder.buildClique(params, -1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCliqueBuilderParameterStartNodeId() {
        LatencyRandomParams params = new LatencyRandomParams(MAX_RANDOM, MAX_RANDOM);
        GraphBuilder.buildClique(params, 10, -1);
    }

    @Test
    public void testGraphBuilder() {
        int graphSize = 54;
        int cliqueSize = 3;
        int outboundLinksPerClique = 6;
        Graph graph = GraphBuilder.buildGraph(graphSize, cliqueSize, outboundLinksPerClique, MAX_RANDOM, MAX_RANDOM);

        assertNotNull(graph);
        assertEquals(graphSize, graph.getNodes().size());
        assertTrue(graph.getAllMaximalCliques().size() > outboundLinksPerClique);
        assertEquals((graphSize / cliqueSize - 1) * graphSize + outboundLinksPerClique,
                graph.getNodes().stream().map(Node::getLinks).mapToInt(Collection::size).sum());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphBuilderParameterGraphSize() {
        GraphBuilder.buildGraph(0, 1, 1, MAX_RANDOM, MAX_RANDOM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphBuilderParameterCliqueSize() {
        GraphBuilder.buildGraph(1, 0, 1, MAX_RANDOM, MAX_RANDOM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphBuilderParameterOutboundLinksPerClique() {
        GraphBuilder.buildGraph(1, 1, 0, MAX_RANDOM, MAX_RANDOM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphBuilderParameterMaxNodeLatency() {
        GraphBuilder.buildGraph(10, 2, 3, -1, MAX_RANDOM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphBuilderParameterMaxLinkLatency() {
        GraphBuilder.buildGraph(10, 2, 3, MAX_RANDOM, -1);
    }

}
