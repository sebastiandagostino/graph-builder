package com.sebastiandagostino.graph;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {
    
    public NodeTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNotEqualsDifferentClasses() {
        Node node1 = new Node(1, 1, 1);
        assertNotSame(node1, "");
        assertFalse(node1.equals(""));
    }
    
    @Test
    public void testNotEquals() {
        Node node1 = new Node(1, 1, 1);
        Node node2 = new Node(2, 1, 1);
        assertNotSame(node1, node2);
        assertFalse(node1.getId() == node2.getId());
        assertFalse(node1.equals(node2));
    }
    
    @Test
    public void testEquals() {
        Node node1 = new Node(1, 1, 1);
        Node node2 = new Node(1, 1, 1);
        assertEquals(node1, node2);
        assertTrue(node1.getId() == node2.getId());
        assertTrue(node1.equals(node2));
    }
    
}
