package com.sebastiandagostino.graph;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphBuilder {

	public static Graph buildGraph(int graphSize, int cliqueSize, int outboundLinksPerClique, int maxNodeLatency, int maxLinkLatency) {
		Validate.isTrue(graphSize >= cliqueSize && outboundLinksPerClique >= cliqueSize && cliqueSize >= 1);
		// Example: graphSize = 50, outboundLinks = 6, cliqueSize = 2 => cliqueAmount = (50 - 6) / 2 = 44 / 2 = 22
		int cliqueAmount = (graphSize - outboundLinksPerClique) / cliqueSize;
		Params params = new Params(maxNodeLatency, maxLinkLatency);
		List<Clique> cliques = new ArrayList();
		for (int i = 0; i < cliqueSize; i++) {
			cliques.add(buildClique(params, cliqueAmount, i * cliqueSize));
		}
		// TODO: Add links
		return null;
	}

	public static Clique buildClique(Params params, int cliqueSize, int startNodeId) {
		Validate.isTrue(cliqueSize >= 0);
		Validate.isTrue(startNodeId >= 0);
		List<Node> nodes = new ArrayList();
		for (int i = startNodeId; i < (startNodeId + cliqueSize); i++) {
			int vote = i % 2 == 0 ? 1 : -1;
			Node node = new Node(i, vote, params.getNextNodeLatencyRandomInteger());
			nodes.add(node);
		}
		for (Node node : nodes) {
			nodes.stream().forEach(n -> {
				if (!n.equals(node)) {
					node.getUniqueNodeList().getUNL().add(n);
					node.getLinks().add(new Link(n.getId(), params.getNextLinkLatencyRandomInteger()));
				}
			});
		}
		return new Clique(nodes);
	}

	public static Graph buildTinyGraph(Params params) {
		// NODE CREATION
		int numNodes = 11;
		List<Node> nodes;
		nodes = new ArrayList<>();
		for (int i = 0; i < numNodes; i++) {
			int vote = (i <= 5) ? 1 : -1;
			nodes.add(new Node(i, vote, params.getNextNodeLatencyRandomInteger()));
		}
		Node node00, node01, node02, node03, node04, node05;
		Node node06, node07, node08, node09, node10;
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
		node00.addLink(new Link(node01.getId(), params.getNextLinkLatencyRandomInteger()));
		node00.addLink(new Link(node08.getId(), params.getNextLinkLatencyRandomInteger()));
		node01.addLink(new Link(node08.getId(), params.getNextLinkLatencyRandomInteger()));
		node01.addLink(new Link(node09.getId(), params.getNextLinkLatencyRandomInteger()));
		node02.addLink(new Link(node09.getId(), params.getNextLinkLatencyRandomInteger()));
		node02.addLink(new Link(node10.getId(), params.getNextLinkLatencyRandomInteger()));
		node03.addLink(new Link(node00.getId(), params.getNextLinkLatencyRandomInteger()));
		node03.addLink(new Link(node01.getId(), params.getNextLinkLatencyRandomInteger()));
		node04.addLink(new Link(node05.getId(), params.getNextLinkLatencyRandomInteger()));
		node04.addLink(new Link(node06.getId(), params.getNextLinkLatencyRandomInteger()));
		node05.addLink(new Link(node06.getId(), params.getNextLinkLatencyRandomInteger()));
		node05.addLink(new Link(node07.getId(), params.getNextLinkLatencyRandomInteger()));
		node06.addLink(new Link(node07.getId(), params.getNextLinkLatencyRandomInteger()));
		node06.addLink(new Link(node08.getId(), params.getNextLinkLatencyRandomInteger()));
		node07.addLink(new Link(node08.getId(), params.getNextLinkLatencyRandomInteger()));
		node07.addLink(new Link(node09.getId(), params.getNextLinkLatencyRandomInteger()));
		node08.addLink(new Link(node09.getId(), params.getNextLinkLatencyRandomInteger()));
		node08.addLink(new Link(node10.getId(), params.getNextLinkLatencyRandomInteger()));
		node09.addLink(new Link(node10.getId(), params.getNextLinkLatencyRandomInteger()));
		node09.addLink(new Link(node00.getId(), params.getNextLinkLatencyRandomInteger()));
		node10.addLink(new Link(node00.getId(), params.getNextLinkLatencyRandomInteger()));
		node10.addLink(new Link(node01.getId(), params.getNextLinkLatencyRandomInteger()));

		// GRAPH SETUP
		Graph graph = new Graph();
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

		return graph;
	}

	public static class Params {

		private Random nodeLatency;

		private Random linkLatency;

		private int maxRandomLatency;

		private int maxLinkLatency;

		public Params(int maxRandomLatency, int maxLinkLatency) {
			this.nodeLatency = new Random();
			this.nodeLatency.setSeed(123456789);
			this.linkLatency = new Random();
			this.linkLatency.setSeed(987654321);
			this.maxRandomLatency = maxRandomLatency;
			this.maxLinkLatency = maxLinkLatency;
		}

		public Random getNodeLatency() {
			return nodeLatency;
		}

		public Random getLinkLatency() {
			return linkLatency;
		}

		public int getMaxRandomLatency() {
			return maxRandomLatency;
		}

		public int getMaxLinkLatency() {
			return maxLinkLatency;
		}

		public int getNextNodeLatencyRandomInteger() {
			return getNodeLatency().nextInt(maxRandomLatency);
		}

		public int getNextLinkLatencyRandomInteger() {
			return getLinkLatency().nextInt(maxRandomLatency);
		}

	}

}
