package com.sebastiandagostino.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class UniqueNodeListTest {
    
    private UniqueNodeList unl1, unl2;
    
    private Node node1, node2, node3, node4, node5, node6;
    
    public UniqueNodeListTest() {
    }
    
    @Before
    public void setUp() {
        node1 = new Node(1, 1, 1);
        node2 = new Node(2, 1, 1);
        node3 = new Node(3, 1, 1);
        node4 = new Node(4, 1, 1);
        node5 = new Node(5, 1, 1);
        node6 = new Node(6, 1, 1);
        unl1 = new UniqueNodeList();
        unl1.add(node1);
        unl1.add(node2);
        unl1.add(node3);
        unl1.add(node4);
        unl2 = new UniqueNodeList();
        unl2.add(node2);
        unl2.add(node4);
        unl2.add(node5);
        unl2.add(node6);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testUNLEmptyCreation() {
        UniqueNodeList unl = new UniqueNodeList();
        assertEquals(0, unl.size());
        assertTrue(unl.isEmpty());
    }
    
    @Test
    public void testUNLCollectionCreation() {
        List<Node> list = new ArrayList<>();
        list.add(new Node(1, 1, 1));
        UniqueNodeList unl = new UniqueNodeList(list);
        assertEquals(list.size(), unl.getUNL().size());
    }
    
    @Test
    public void testAdditionAndRemovalOfNodes() {
        int previousSize = unl1.size();
        unl1.remove(node1);
        assertEquals(previousSize - 1, unl1.size());
    }

    @Test
    public void testIntersection() {
        Collection<Node> intersection = unl1.intersect(unl2);
        assertEquals(2, intersection.size());
        assertTrue(intersection.contains(node2));
        assertTrue(intersection.contains(node4));
    }

}
