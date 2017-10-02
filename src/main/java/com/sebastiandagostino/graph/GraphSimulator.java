package com.sebastiandagostino.graph;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastiandagostino.graph.json.GraphJsonMapping;
import com.sebastiandagostino.graph.json.LinkJsonMapping;
import com.sebastiandagostino.graph.json.NodeJsonMapping;
import com.sebastiandagostino.graph.simulator.*;

import java.io.File;
import java.io.IOException;

public class GraphSimulator {

    // Latencies in milliseconds
    // E2C - End to core, the latency from a node to a nearby node
    // C2C - Core to core, the additional latency when nodes are far

    public static final int CONSENSUS_PERCENT = 80;

    public static void main(String [] args) {

        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            System.err.println("usage: sim input_file");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        GraphJsonMapping json;
        try {
            json = mapper.readValue(new File(fileName), GraphJsonMapping.class);
        } catch (IOException e) {
            System.err.println("Unable to read or parse json file. Exiting...");
            return;
        }

        // Read parameters and network from json file
        System.out.println("Loading network from file: " + fileName);

        int numNodes = json.getNodes().size();
        System.out.println("Reading NUM_NODES = " + numNodes);
        int unlThresh = json.getUnlThresh();
        System.out.println("Reading UNL_THRESH = " + unlThresh);

        // Create nodes
        System.out.println("Creating nodes");
        Node[] nodes = new Node[numNodes];

        for (NodeJsonMapping node : json.getNodes()) {
            // NodeIds must be from 0 until numNodes - 1
            int nodeId = node.getNodeId();
            int vote = node.getVote();
            int latency = node.getLatency();
            Node newNode = new Node(nodeId, numNodes, latency);
            newNode.setVote(vote);
            newNode.getNodeTimeStamps()[nodeId] = 1;
            nodes[nodeId] = newNode;

            // Build our UNL
            for (Integer unlNode : node.getUniqueNodeList()) {
                nodes[nodeId].getUniqueNodeList().add(unlNode);
            }
        }

        // Create links
        System.out.println("Creating links");

        for (LinkJsonMapping link : json.getLinks()) {
            int i = link.getFrom();
            int lt = link.getTo();
            int latency = link.getLatency();
            int ll = nodes[i].getLatency() + nodes[lt].getLatency() + latency;
            nodes[i].getLinks().add(new Link(lt, ll));
            nodes[lt].getLinks().add(new Link(i, ll));
        }

        Network network = new Network();

        // Trigger all nodes to make initial broadcasts of their own positions
        System.out.println("Creating initial messages");
        for (Node node : nodes) {
            for (Link link : node.getLinks()) {
                Message message = new Message(node.getNodeId(), link.getToNodeId());
                message.insertData(node.getNodeId(), nodes[node.getNodeId()].getVote());
                network.sendMessage(message, link, 0);
            }

        }
        System.out.println("Created " + network.countMessages() + " events");

        // Run simulation
        System.out.println("\tTime (ms)\tPositive\tNegative");
        System.out.println("\t---------\t--------\t--------");
        do {
            int nodesPositive = 0;
            int nodesNegative = 0;
            // Count nodes and check convergence
            for (Node node : nodes) {
                if (node.getVote() > 0) {
                    nodesPositive++;
                } else if (node.getVote() < 0) {
                    nodesNegative++;
                }
            }
            if (nodesPositive > (numNodes * CONSENSUS_PERCENT / 100)) {
                break;
            }
            if (nodesNegative > (numNodes * CONSENSUS_PERCENT / 100)) {
                break;
            }

            if (network.getMessages().isEmpty()) {
                System.err.println("Fatal: Radio Silence. Exiting...");
                return;
            }

            Event event = network.getMessages().stream().findFirst().get();
            if ((event.getReceiveTime() / 100) > (network.getMasterTime() / 100)) {
                System.out.println("\t\t" + event.getReceiveTime() + ";\t\t" + nodesPositive + ";\t\t" + nodesNegative);
            }
            network.setMasterTime(event.getReceiveTime());

            for (Message message : event.getMessages()) {
                if (message.hasEmptyData()) {
                    // Message was never sent
                    nodes[message.getFromNodeId()].decreaseMessagesSent();
                } else {
                    nodes[message.getToNodeId()].receiveMessage(message, network, nodes, unlThresh);
                }
            }

            network.getMessages().remove(event);

        } while (true);

        int nodesPositive = 0;
        int nodesNegative = 0;
        // Count nodes and check convergence
        for (Node node : nodes) {
            if (node.getVote() > 0) {
                nodesPositive++;
            } else if (node.getVote() < 0) {
                nodesNegative++;
            }
        }
        System.out.println("\t" + network.getMasterTime() + ";\t\t" + nodesPositive + ";\t\t" + nodesNegative);
        System.out.println("Consensus reached in " + network.getMasterTime() + " ms with " + network.countMessagesOnTheWire() + " messages on the wire");

        // Output result
        long totalMsgSent = 0;
        for (Node node : nodes) {
            totalMsgSent += node.getMessagesSent();
        }
        System.out.println("The average node sent " + (totalMsgSent / numNodes) + " messages");

    }

}
