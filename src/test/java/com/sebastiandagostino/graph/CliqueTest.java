package com.sebastiandagostino.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CliqueTest {

    private Clique clique1, clique2;

    private Node node1, node2, node3, node4, node5, node6;

    @Before
    public void setUp() {
        node1 = new Node(1, 1, 1);
        node2 = new Node(2, 1, 1);
        node3 = new Node(3, 1, 1);
        node4 = new Node(4, 1, 1);
        node5 = new Node(5, 1, 1);
        node6 = new Node(6, 1, 1);

        clique1 = new Clique(new ArrayList(Arrays.asList(node1, node2, node3, node4)));
        clique2 = new Clique(new ArrayList(Arrays.asList(node2, node4, node5, node6)));
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

}
