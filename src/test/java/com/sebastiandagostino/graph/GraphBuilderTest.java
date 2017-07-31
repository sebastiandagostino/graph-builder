package com.sebastiandagostino.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphBuilderTest {

    @Test
    public void testCliqueBuilder() {
        GraphBuilder.Params params = new GraphBuilder.Params();
        int cliqueSize = 20;
        int startNodeId = 15;
        int maxRandomLatency = 500;
        Clique clique = GraphBuilder.buildClique(params, cliqueSize, startNodeId, maxRandomLatency);

        assertEquals(cliqueSize, clique.getNodes().size());
        for (int i = startNodeId; i < (cliqueSize + startNodeId); i++) {
            assertTrue(clique.getNodes().contains(new Node(i, 1, 1)));
        }
    }

}
