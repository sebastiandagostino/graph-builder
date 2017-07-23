package com.sebastiandagostino.graph;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {
    
    private Graph graph;
    
    private Node node01, node02, node03, node04, node05, node06;
    
    private Node node07, node08, node09, node10, node11;
    
    public GraphTest() {
    }
       
    @Before
    public void setUp() {
        // NODE CREATION
        node01 = new Node(0, 1, 1);
        node02 = new Node(1, 1, 1);
        node03 = new Node(2, 1, 1);
        node04 = new Node(3, 1, 1);
        node05 = new Node(4, 1, 1);
        node06 = new Node(5, 1, 1);
        node07 = new Node(6, -1, 1);
        node08 = new Node(7, -1, 1);
        node09 = new Node(8, -1, 1);
        node10 = new Node(9, -1, 1);
        node11 = new Node(10, -1, 1);
        
        // LEFT CLIQUE
        node01.getUniqueNodeList().add(node02);
        node01.getUniqueNodeList().add(node03);
        node01.getUniqueNodeList().add(node04);
        node01.getUniqueNodeList().add(node05);
        node01.getUniqueNodeList().add(node06);
        node02.getUniqueNodeList().add(node01);
        node02.getUniqueNodeList().add(node03);
        node02.getUniqueNodeList().add(node04);
        node02.getUniqueNodeList().add(node05);
        node02.getUniqueNodeList().add(node06);
        node03.getUniqueNodeList().add(node01);
        node03.getUniqueNodeList().add(node02);
        node03.getUniqueNodeList().add(node04);
        node03.getUniqueNodeList().add(node05);
        node03.getUniqueNodeList().add(node06);
        node04.getUniqueNodeList().add(node01);
        node04.getUniqueNodeList().add(node02);
        node04.getUniqueNodeList().add(node03);
        node04.getUniqueNodeList().add(node05);
        node04.getUniqueNodeList().add(node06);
        node05.getUniqueNodeList().add(node01);
        node05.getUniqueNodeList().add(node02);
        node05.getUniqueNodeList().add(node03);
        node05.getUniqueNodeList().add(node04);
        node05.getUniqueNodeList().add(node06);
        node06.getUniqueNodeList().add(node01);
        node06.getUniqueNodeList().add(node02);
        node06.getUniqueNodeList().add(node03);
        node06.getUniqueNodeList().add(node04);
        node06.getUniqueNodeList().add(node05);
        
        // INTERSECTION
        node05.getUniqueNodeList().add(node08);
        node08.getUniqueNodeList().add(node05);
        node04.getUniqueNodeList().add(node09);
        node09.getUniqueNodeList().add(node04);
        
        // RIGHT CLIQUE
        node07.getUniqueNodeList().add(node08);
        node07.getUniqueNodeList().add(node09);
        node07.getUniqueNodeList().add(node10);
        node07.getUniqueNodeList().add(node11);
        node08.getUniqueNodeList().add(node07);
        node08.getUniqueNodeList().add(node09);
        node08.getUniqueNodeList().add(node10);
        node08.getUniqueNodeList().add(node11);
        node09.getUniqueNodeList().add(node07);
        node09.getUniqueNodeList().add(node08);
        node09.getUniqueNodeList().add(node10);
        node09.getUniqueNodeList().add(node11);
        node10.getUniqueNodeList().add(node07);
        node10.getUniqueNodeList().add(node08);
        node10.getUniqueNodeList().add(node09);
        node10.getUniqueNodeList().add(node11);
        node11.getUniqueNodeList().add(node07);
        node11.getUniqueNodeList().add(node08);
        node11.getUniqueNodeList().add(node09);
        node11.getUniqueNodeList().add(node10);

        // LINKS
        node01.addLink(new Link(node02.getId(), 1));
        node01.addLink(new Link(node03.getId(), 1));
        node02.addLink(new Link(node03.getId(), 1));
        node02.addLink(new Link(node04.getId(), 1));
        node03.addLink(new Link(node04.getId(), 1));
        node03.addLink(new Link(node05.getId(), 1));
        node04.addLink(new Link(node05.getId(), 1));
        node04.addLink(new Link(node06.getId(), 1));
        node05.addLink(new Link(node06.getId(), 1));
        node05.addLink(new Link(node07.getId(), 1));
        node06.addLink(new Link(node07.getId(), 1));
        node06.addLink(new Link(node08.getId(), 1));
        node07.addLink(new Link(node08.getId(), 1));
        node07.addLink(new Link(node09.getId(), 1));
        node08.addLink(new Link(node09.getId(), 1));
        node08.addLink(new Link(node10.getId(), 1));
        node09.addLink(new Link(node10.getId(), 1));
        node09.addLink(new Link(node11.getId(), 1));
        node10.addLink(new Link(node11.getId(), 1));
        node10.addLink(new Link(node01.getId(), 1));
        node11.addLink(new Link(node01.getId(), 1));
        node11.addLink(new Link(node02.getId(), 1));
        
        // GRAPH SETUP
        graph = new Graph();
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
        graph.getNodes().add(node11);
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
        list.add(new Node(1, 1, 1));
        assertEquals(list.size(), (new Graph(list)).getNodes().size());
    }
    
    @Test
    public void testJGraph() {
        // TODO: Test
        System.out.println(graph.toJGraph().toString());
    }

    @Test
    public void testJson() {
        // TODO: Test
        System.out.println(graph.toJsonString());
    }

    @Test
    public void testFork() {
        // TODO: Test
        System.out.println(graph);
        graph.calculateForkPossibility();
    }

}
