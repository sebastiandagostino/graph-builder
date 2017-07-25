package com.sebastiandagostino.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Clique {

    private Collection<Node> nodes;

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
        list.removeIf(node -> intersection.contains(node));
        return list;
    }

    public Collection<Node> getNodesNotInCommonFromBoth(Clique clique) {
        List<Node> list = new ArrayList(this.getNodes());
        list.addAll(clique.getNodes());
        Collection<Node> intersection = this.getNodesInCommon(clique);
        list.removeIf(node -> intersection.contains(node));
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

}
