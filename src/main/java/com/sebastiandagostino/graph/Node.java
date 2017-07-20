package com.sebastiandagostino.graph;

import java.util.*;

import org.apache.commons.lang3.Validate;

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
        Validate.isTrue(latency >= 0);
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
        if (obj.getClass() != this.getClass())
            return false;
        return this.id == ((Node) obj).id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.uniqueNodeList);
        return hash;
    }
    
}
