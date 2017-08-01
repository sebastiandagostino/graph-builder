package com.sebastiandagostino.graph;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphBuilderTest {

    @Test
    public void testCliqueBuilder() {
        int cliqueSize = 20;
        int startNodeId = 15;
        int maxRandomLatency = 500;
		GraphBuilder.Params params = new GraphBuilder.Params(maxRandomLatency, maxRandomLatency);
        Clique clique = GraphBuilder.buildClique(params, cliqueSize, startNodeId);

        assertEquals(cliqueSize, clique.getNodes().size());
        for (int i = startNodeId; i < (cliqueSize + startNodeId); i++) {
            assertTrue(clique.getNodes().contains(new Node(i, 1, 1)));
        }
    }

}
