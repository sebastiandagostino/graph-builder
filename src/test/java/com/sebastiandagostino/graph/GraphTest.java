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
        node01 = new Node("v01");
        node02 = new Node("v02");
        node03 = new Node("v03");
        node04 = new Node("v04");
        node05 = new Node("v05");
        node06 = new Node("v06");
        node07 = new Node("v07");
        node08 = new Node("v08");
        node09 = new Node("v09");
        node10 = new Node("v10");
        node11 = new Node("v11");
        
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
        list.add(new Node("1"));
        assertEquals(list.size(), (new Graph(list)).getNodes().size());
    }
    
    @Test
    public void testJGraph() {
        System.out.println(graph.toJGraph().toString());
    }
    
    @Test
    public void testFork() {
        System.out.println(graph);
        graph.calculateForkPossibility();
    }

}
