package com.sebastiandagostino.graph;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebastiandagostino.graph.json.GraphJsonMapping;
import com.sebastiandagostino.graph.json.LinkJsonMapping;
import com.sebastiandagostino.graph.json.NodeJsonMapping;
import com.sebastiandagostino.graph.simulator.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GraphSimulator {

    // Latencies in milliseconds
    // E2C - End to core, the latency from a node to a nearby node
    // C2C - Core to core, the additional latency when nodes are far

    // TODO: Create application configuration with this constant and the ones in the Node class
    public static final int CONSENSUS_PERCENT = 80;

    public static void main(String [] args) {

        String fileName;
        if (args.length == 1) {
            fileName = args[0];
        } else {
            System.err.println("Simulator requires only one parameter providing the filename where data is");
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

        // TODO: Remove NUM_NODES from JSON since it is no longer necessary
        int numNodes = json.getNodes().size();
        System.out.println("Reading NUM_NODES = " + numNodes);
        int unlThresh = json.getUnlThresh();
        System.out.println("Reading UNL_THRESH = " + unlThresh);

        // Create nodes
        System.out.println("Creating nodes");
        HashMap<Integer, Node> nodes = new HashMap<>();

        for (NodeJsonMapping node : json.getNodes()) {
            // TODO: Add validation that all nodeIds must be different
            int nodeId = node.getNodeId();
            nodes.put(nodeId, new Node(nodeId, node.getLatency(), NodeState.Vote.fromInteger(node.getVote())));

            // Build our UNL
            for (Integer unlNode : node.getUniqueNodeList()) {
                nodes.get(nodeId).getUniqueNodeList().add(unlNode);
            }
        }

        // Create links
        System.out.println("Creating links");

        for (LinkJsonMapping link : json.getLinks()) {
            int linkFrom = link.getFrom();
            int linkTo = link.getTo();
            int latency = nodes.get(linkFrom).getLatency() + nodes.get(linkTo).getLatency() + link.getLatency();
            nodes.get(linkFrom).getLinks().add(new Link(linkTo, latency));
            nodes.get(linkTo).getLinks().add(new Link(linkFrom, latency));
        }

        Network network = new Network(nodes, CONSENSUS_PERCENT);

        // Trigger all nodes to make initial broadcasts of their own positions
        System.out.println("Creating initial messages");
        for (Node node : nodes.values()) {
            for (Link link : node.getLinks()) {
                Message message = new Message(node.getId(), link.getToNodeId());
                message.insertData(node.getId(), nodes.get(node.getId()).getVote());
                network.sendMessage(message, link);
            }

        }
        System.out.println("Created " + network.countMessages() + " events");

        // Run simulation
        System.out.println("\tTime (ms)\tPositive\tNegative");
        System.out.println("\t---------\t--------\t--------");
        do {
            // Check convergence
            if (network.isConsensusReached()) {
                break;
            }

            if (network.getMessages().isEmpty()) {
                System.err.println("Fatal: Radio Silence. Exiting...");
                return;
            }

            // Assumes order from the network message stream
            Event event = network.getMessages().stream().findFirst().get();
            if ((event.getReceiveTime() / 100) > (network.getMasterTime() / 100)) {
                System.out.println("\t\t" + event.getReceiveTime()
                        + ";\t\t" + network.countVotes(NodeState.Vote.POSITIVE)
                        + ";\t\t" + network.countVotes(NodeState.Vote.NEGATIVE));
            }
            network.setMasterTime(event.getReceiveTime());

            for (Message message : event.getMessages()) {
                if (message.hasEmptyData()) {
                    // Message was never sent
                    network.getNodes().get(message.getFromNodeId()).decreaseMessagesSent();
                } else {
                    network.getNodes().get(message.getToNodeId()).receiveMessage(message, network, unlThresh);
                }
            }

            network.getMessages().remove(event);

        } while (true);

        System.out.println("\t" + network.getMasterTime()
                + ";\t\t" + network.countVotes(NodeState.Vote.POSITIVE)
                + ";\t\t" + network.countVotes(NodeState.Vote.NEGATIVE));

        System.out.println("Consensus reached in " + network.getMasterTime() + " ms "
                + "with " + network.countMessagesOnTheWire() + " messages on the wire");

        System.out.println("The average node sent " + (network.countMessagesSent() / numNodes) + " messages");

    }

}
