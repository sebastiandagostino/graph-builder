package com.sebastiandagostino.graph;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class LinkTest {

    @Test
    public void testNotEqualsDifferentClasses() {
        Link link = new Link(1, 1);
        assertNotSame(link, "");
        assertFalse(link.equals(""));
    }

    @Test
    public void testNotEquals() {
        Link link1 = new Link(1, 1);
        Link link2 = new Link(2, 1);
        assertNotSame(link1, link2);
        assertFalse(link1.getToNodeId() == link2.getToNodeId());
        assertFalse(link1.equals(link2));
    }

    @Test
    public void testEquals() {
        Link link1 = new Link(1, 1);
        Link link2 = new Link(1, 1);
        assertEquals(link1, link2);
        assertTrue(link1.getToNodeId() == link2.getToNodeId());
        assertTrue(link1.equals(link2));
    }

}
