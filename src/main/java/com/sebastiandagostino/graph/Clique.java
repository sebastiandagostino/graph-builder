package com.sebastiandagostino.graph;

import java.util.Set;

public class Clique {

    private Set<Node> nodes;

    public Clique(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Set<Node> getNodes() {
        return nodes;
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
