package com.sebastiandagostino.graph;

import java.util.*;
import java.util.stream.Collectors;

public class Clique {

    private Collection<Node> nodes;

	public Clique() {
		this.nodes = new HashSet();
	}

    public Clique(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public Collection<Node> getNodesInCommon(Clique clique) {
        return this.getNodes().stream().filter(clique.getNodes()::contains).collect(Collectors.toList());
    }

    public Collection<Node> getNodesNotInCommon(Clique clique) {
        List<Node> list = new ArrayList(this.getNodes());
        Collection<Node> intersection = this.getNodesInCommon(clique);
        list.removeIf(intersection::contains);
        return list;
    }

    public Collection<Node> getNodesNotInCommonFromBoth(Clique clique) {
        List<Node> list = new ArrayList(this.getNodes());
        list.addAll(clique.getNodes());
        Collection<Node> intersection = this.getNodesInCommon(clique);
        list.removeIf(intersection::contains);
        return list;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("clique: { ");
        int i = 0, size = this.nodes.size();
        for (Node node : this.nodes) {
            stringBuilder.append(node.getId());
            if (i != size -1) {
                stringBuilder.append(", ");
            }
            i++;
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

	public static Collection<Node> getNodesNotIntersectingCliques(List<Clique> cliques) {
		Map<Node, Integer> nodeCountInCliques = getNodeCountInCliques(cliques);
		return nodeCountInCliques.entrySet().stream().filter(entry -> entry.getValue() == 1).map(entry -> entry.getKey()).collect(Collectors.toList());
	}

	public static Collection<Node> getNodesIntersectingCliques(List<Clique> cliques) {
		Map<Node, Integer> nodeCountInCliques = getNodeCountInCliques(cliques);
		return nodeCountInCliques.entrySet().stream().filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey()).collect(Collectors.toList());
	}

	private static Map<Node, Integer> getNodeCountInCliques(List<Clique> cliques) {
		Map<Node, Integer> nodeCountInCliques = new HashMap();
		for (Clique clique : cliques) {
			for (Node node : clique.getNodes()) {
				if (nodeCountInCliques.containsKey(node)) {
					nodeCountInCliques.merge(node, 1, Integer::sum);
				} else {
					nodeCountInCliques.put(node, 1);
				}
			}
		}
		return nodeCountInCliques;
	}

	public static List<Clique> filterIntersectingNodesFromCliques(List<Clique> cliques) {
		List<Clique> filteredList = new ArrayList();
    	Collection<Node> nodesIntersectingCliques = getNodesIntersectingCliques(cliques);
		for (Clique clique : cliques) {
			Clique filteredClique = new Clique();
			for (Node node : clique.getNodes()) {
				if (!nodesIntersectingCliques.contains(node)) {
					filteredClique.getNodes().add(node);
				}
			}
			if (!filteredClique.getNodes().isEmpty()) {
				filteredList.add(filteredClique);
			}
		}
		return filteredList;
	}

}
