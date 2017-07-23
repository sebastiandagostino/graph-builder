package com.sebastiandagostino.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    
    private Graph graph;

    private static final int NUM_NODES = 11;

    private static final int MAX_RANDOM = 500;

    private List<Node> nodes;

    private Node node00, node01, node02, node03, node04, node05;

    private Node node06, node07, node08, node09, node10;
    
    public GraphTest() {
    }

    @Before
    public void setUp() {
        // NODE CREATION
        nodes = new ArrayList<>();
        for(int i = 0; i < NUM_NODES; i++) {
            int vote = (i <= 5) ? 1 : -1;
            nodes.add(new Node(i, vote, (int) (Math.random() * MAX_RANDOM)));
        }
        node00 = nodes.get(0);
        node01 = nodes.get(1);
        node02 = nodes.get(2);
        node03 = nodes.get(3);
        node04 = nodes.get(4);
        node05 = nodes.get(5);
        node06 = nodes.get(6);
        node07 = nodes.get(7);
        node08 = nodes.get(8);
        node09 = nodes.get(9);
        node10 = nodes.get(10);

        // LEFT CLIQUE
        node00.getUniqueNodeList().add(node01);
        node00.getUniqueNodeList().add(node02);
        node00.getUniqueNodeList().add(node03);
        node00.getUniqueNodeList().add(node04);
        node00.getUniqueNodeList().add(node05);
        node01.getUniqueNodeList().add(node00);
        node01.getUniqueNodeList().add(node02);
        node01.getUniqueNodeList().add(node03);
        node01.getUniqueNodeList().add(node04);
        node01.getUniqueNodeList().add(node05);
        node02.getUniqueNodeList().add(node00);
        node02.getUniqueNodeList().add(node01);
        node02.getUniqueNodeList().add(node03);
        node02.getUniqueNodeList().add(node04);
        node02.getUniqueNodeList().add(node05);
        node03.getUniqueNodeList().add(node00);
        node03.getUniqueNodeList().add(node01);
        node03.getUniqueNodeList().add(node02);
        node03.getUniqueNodeList().add(node04);
        node03.getUniqueNodeList().add(node05);
        node04.getUniqueNodeList().add(node00);
        node04.getUniqueNodeList().add(node01);
        node04.getUniqueNodeList().add(node02);
        node04.getUniqueNodeList().add(node03);
        node04.getUniqueNodeList().add(node05);
        node05.getUniqueNodeList().add(node00);
        node05.getUniqueNodeList().add(node01);
        node05.getUniqueNodeList().add(node02);
        node05.getUniqueNodeList().add(node03);
        node05.getUniqueNodeList().add(node04);
        
        // INTERSECTION
        node04.getUniqueNodeList().add(node07);
        node07.getUniqueNodeList().add(node04);
        node03.getUniqueNodeList().add(node08);
        node08.getUniqueNodeList().add(node03);
        
        // RIGHT CLIQUE
        node06.getUniqueNodeList().add(node07);
        node06.getUniqueNodeList().add(node08);
        node06.getUniqueNodeList().add(node09);
        node06.getUniqueNodeList().add(node10);
        node07.getUniqueNodeList().add(node06);
        node07.getUniqueNodeList().add(node08);
        node07.getUniqueNodeList().add(node09);
        node07.getUniqueNodeList().add(node10);
        node08.getUniqueNodeList().add(node06);
        node08.getUniqueNodeList().add(node07);
        node08.getUniqueNodeList().add(node09);
        node08.getUniqueNodeList().add(node10);
        node09.getUniqueNodeList().add(node06);
        node09.getUniqueNodeList().add(node07);
        node09.getUniqueNodeList().add(node08);
        node09.getUniqueNodeList().add(node10);
        node10.getUniqueNodeList().add(node06);
        node10.getUniqueNodeList().add(node07);
        node10.getUniqueNodeList().add(node08);
        node10.getUniqueNodeList().add(node09);

        // LINKS
        node00.addLink(new Link(node01.getId(), (int) (Math.random() * MAX_RANDOM)));
        node00.addLink(new Link(node08.getId(), (int) (Math.random() * MAX_RANDOM)));
        node01.addLink(new Link(node08.getId(), (int) (Math.random() * MAX_RANDOM)));
        node01.addLink(new Link(node09.getId(), (int) (Math.random() * MAX_RANDOM)));
        node02.addLink(new Link(node09.getId(), (int) (Math.random() * MAX_RANDOM)));
        node02.addLink(new Link(node10.getId(), (int) (Math.random() * MAX_RANDOM)));
        node03.addLink(new Link(node00.getId(), (int) (Math.random() * MAX_RANDOM)));
        node03.addLink(new Link(node01.getId(), (int) (Math.random() * MAX_RANDOM)));
        node04.addLink(new Link(node05.getId(), (int) (Math.random() * MAX_RANDOM)));
        node04.addLink(new Link(node06.getId(), (int) (Math.random() * MAX_RANDOM)));
        node05.addLink(new Link(node06.getId(), (int) (Math.random() * MAX_RANDOM)));
        node05.addLink(new Link(node07.getId(), (int) (Math.random() * MAX_RANDOM)));
        node06.addLink(new Link(node07.getId(), (int) (Math.random() * MAX_RANDOM)));
        node06.addLink(new Link(node08.getId(), (int) (Math.random() * MAX_RANDOM)));
        node07.addLink(new Link(node08.getId(), (int) (Math.random() * MAX_RANDOM)));
        node07.addLink(new Link(node09.getId(), (int) (Math.random() * MAX_RANDOM)));
        node08.addLink(new Link(node09.getId(), (int) (Math.random() * MAX_RANDOM)));
        node08.addLink(new Link(node10.getId(), (int) (Math.random() * MAX_RANDOM)));
        node09.addLink(new Link(node10.getId(), (int) (Math.random() * MAX_RANDOM)));
        node09.addLink(new Link(node00.getId(), (int) (Math.random() * MAX_RANDOM)));
        node10.addLink(new Link(node00.getId(), (int) (Math.random() * MAX_RANDOM)));
        node10.addLink(new Link(node01.getId(), (int) (Math.random() * MAX_RANDOM)));
        
        // GRAPH SETUP
        graph = new Graph();
        graph.getNodes().add(node00);
        graph.getNodes().add(node01);
        graph.getNodes().add(node02);
        graph.getNodes().add(node03);
        graph.getNodes().add(node04);
        graph.getNodes().add(node05);
        graph.getNodes().add(node06);
        graph.getNodes().add(node07);
        graph.getNodes().add(node08);
        graph.getNodes().add(node09);
        graph.getNodes().add(node10);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUNLEmptyCreation() {
        assertEquals(0, (new Graph()).getNodes().size());
    }
    
    @Test
    public void testUNLCollectionCreation() {
        List<Node> list = new ArrayList<>();
        list.add(new Node(0, 1, 100));

        assertEquals(list.size(), (new Graph(list)).getNodes().size());
    }
    
    @Test
    public void testJGraphCliques() {
        List<Clique> cliques = graph.getAllMaximalCliques();

        System.out.println(cliques);
        assertEquals(4, cliques.size());
    }

    @Test
    public void testGraphToJsonString() {
        String jsonString = graph.toJsonString();
        System.out.println(jsonString);

        assertTrue(jsonString.contains("numNodes"));
        assertTrue(jsonString.contains("unlThresh"));
        assertTrue(jsonString.contains("nodes"));
        assertTrue(jsonString.contains("links"));
        assertEquals(NUM_NODES, StringUtils.countMatches(jsonString, "nodeId"));
        assertEquals(NUM_NODES, StringUtils.countMatches(jsonString, "vote"));
        assertEquals(NUM_NODES, StringUtils.countMatches(jsonString, "uniqueNodeList"));
    }

}
