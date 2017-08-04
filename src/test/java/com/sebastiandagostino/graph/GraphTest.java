package com.sebastiandagostino.graph;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    private LatencyRandomParams params;

    private Graph tinyGraph;

    private static final int MAX_RANDOM = 500;

    @Before
    public void setUp() {
        this.params = new LatencyRandomParams(MAX_RANDOM, MAX_RANDOM);
        this.tinyGraph = GraphBuilder.buildTinyGraph(params);
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
        List<Clique> cliques = this.tinyGraph.getAllMaximalCliques();

        System.out.println(cliques);
        assertEquals(4, cliques.size());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGraphFromJson() {
        new Graph("invalid");
    }

    @Test
    public void testGraphFromJsonFile() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("network.json").getFile());
        String jsonInputString = new Scanner(file).useDelimiter("\\Z").next();

        Graph graph = new Graph(jsonInputString);
        String jsonString = graph.toString();
        System.out.println(jsonString);

        assertTrue(jsonString.contains("numNodes"));
        assertTrue(jsonString.contains("unlThresh"));
        assertTrue(jsonString.contains("nodes"));
        assertTrue(jsonString.contains("links"));
        assertEquals(1000, StringUtils.countMatches(jsonString, "nodeId"));
        assertEquals(1000, StringUtils.countMatches(jsonString, "vote"));
        assertEquals(1000, StringUtils.countMatches(jsonString, "uniqueNodeList"));
    }

    @Test
    public void testGraphToJsonString() {
        int numNodes = this.tinyGraph.getNodes().size();
        String jsonString = this.tinyGraph.toString();
        System.out.println(jsonString);

        assertTrue(jsonString.contains("numNodes"));
        assertTrue(jsonString.contains("unlThresh"));
        assertTrue(jsonString.contains("nodes"));
        assertTrue(jsonString.contains("links"));
        assertEquals(numNodes, StringUtils.countMatches(jsonString, "nodeId"));
        assertEquals(numNodes, StringUtils.countMatches(jsonString, "vote"));
        assertEquals(numNodes, StringUtils.countMatches(jsonString, "uniqueNodeList"));
    }

    @Test
    public void testImproveConnectivity() {
        int size = this.tinyGraph.getNodes().size();
        int vote = -1;
        int latency = this.params.getNextNodeLatency();
        this.tinyGraph.improveConnectivity(vote, latency);

        System.out.println(this.tinyGraph.toString());
        assertEquals(size + 1, this.tinyGraph.getNodes().size());
    }

    @Test
    public void testImproveConnectivityFromFile() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("network.json").getFile());
        String jsonInputString = new Scanner(file).useDelimiter("\\Z").next();

        Graph graph = new Graph(jsonInputString);
        int size = graph.getNodes().size();
        int vote = -1;
        int latency = this.params.getNextNodeLatency();
        graph.improveConnectivity(vote, latency);

        System.out.println(graph.toString());
        assertEquals(size + 1, graph.getNodes().size());
    }

}
