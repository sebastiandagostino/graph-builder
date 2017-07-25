package com.sebastiandagostino.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CliqueTest {

    private Clique clique1, clique2, clique3;

    private Node node1, node2, node3, node4, node5, node6, node7, node8;

    @Before
    public void setUp() {
        node1 = new Node(1, 1, 1);
        node2 = new Node(2, 1, 1);
        node3 = new Node(3, 1, 1);
        node4 = new Node(4, 1, 1);
        node5 = new Node(5, 1, 1);
        node6 = new Node(6, 1, 1);
		node7 = new Node(7, 1, 1);
		node8 = new Node(8, 1, 1);

        clique1 = new Clique(new ArrayList(Arrays.asList(node1, node2, node3, node4)));
        clique2 = new Clique(new ArrayList(Arrays.asList(node2, node4, node5, node6)));
        clique3 = new Clique(new ArrayList(Arrays.asList(node4, node6, node7, node8)));
    }

    @Test
    public void testGetNodesInCommon() {
        Collection<Node> nodesInCommon = clique1.getNodesInCommon(clique2);

        assertEquals(2, nodesInCommon.size());
        assertTrue(nodesInCommon.contains(node2));
        assertTrue(nodesInCommon.contains(node4));
    }


    @Test
    public void testGetNodesNotInCommon() {
        Collection<Node> nodesNotInCommon = clique1.getNodesNotInCommon(clique2);

        assertEquals(2, nodesNotInCommon.size());
        assertTrue(nodesNotInCommon.contains(node1));
        assertTrue(nodesNotInCommon.contains(node3));
    }

    @Test
    public void testGetNodesNotInCommonFromBoth() {
        Collection<Node> nodesNotInCommon = clique1.getNodesNotInCommonFromBoth(clique2);

        assertEquals(4, nodesNotInCommon.size());
        assertTrue(nodesNotInCommon.contains(node1));
        assertTrue(nodesNotInCommon.contains(node3));
        assertTrue(nodesNotInCommon.contains(node5));
        assertTrue(nodesNotInCommon.contains(node6));
    }

    @Test
	public void testNodesNotIntersectingCliques() {
    	List<Clique> cliques = new ArrayList();
    	cliques.add(clique1);
    	cliques.add(clique2);
    	cliques.add(clique3);
		Collection<Node> nodesNotIntersectingCliques = Clique.getNodesNotIntersectingCliques(cliques);

		assertEquals(5, nodesNotIntersectingCliques.size());
		assertTrue(nodesNotIntersectingCliques.contains(node1));
		assertTrue(nodesNotIntersectingCliques.contains(node3));
		assertTrue(nodesNotIntersectingCliques.contains(node5));
		assertTrue(nodesNotIntersectingCliques.contains(node7));
		assertTrue(nodesNotIntersectingCliques.contains(node8));
	}

	@Test
	public void testNodesIntersectingCliques() {
		List<Clique> cliques = new ArrayList();
		cliques.add(clique1);
		cliques.add(clique2);
		cliques.add(clique3);
		Collection<Node> nodesIntersectingCliques = Clique.getNodesIntersectingCliques(cliques);

		assertEquals(3, nodesIntersectingCliques.size());
		assertTrue(nodesIntersectingCliques.contains(node2));
		assertTrue(nodesIntersectingCliques.contains(node4));
		assertTrue(nodesIntersectingCliques.contains(node6));
	}

}
