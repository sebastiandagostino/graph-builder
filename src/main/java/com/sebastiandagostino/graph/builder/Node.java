package com.sebastiandagostino.graph.builder;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private int id;

    private int vote;

    private int latency;

    private UniqueNodeList uniqueNodeList;

    private Set<Link> links;

    public Node(int id, int vote, int latency) {
        Validate.notNull(id);
        Validate.notNull(vote);
        Validate.notNull(latency);
        this.id = id;
        this.vote = vote;
        this.latency = latency;
        this.uniqueNodeList = new UniqueNodeList();
        this.links = new HashSet();
    }

    public int getId() {
        return this.id;
    }

    public UniqueNodeList getUniqueNodeList() {
        return this.uniqueNodeList;
    }

    public Collection<Node> getUniqueNodeListIntersection(Node node) {
        return this.getUniqueNodeList().intersect(node.getUniqueNodeList());
    }

    public int getVote() {
        return vote;
    }

    public int getLatency() {
        return latency;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    public Collection<Link> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return "Node: " + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            return new EqualsBuilder().append(this.id, ((Node) obj).id).isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }

}
