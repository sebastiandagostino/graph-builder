package com.sebastiandagostino.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.Validate;

public class Node {
    
    private final String id;
    
    private UniqueNodeList uniqueNodeList; 
    
    private List<Link> links;
    
    public Node(String id) {
        Validate.notNull(id);
        this.id = id;
        this.uniqueNodeList = new UniqueNodeList();
        this.links = new ArrayList();
    }
    
    public String getId() {
        return this.id;
    }
    
    public UniqueNodeList getUniqueNodeList() {
        return this.uniqueNodeList;
    }
    
    public Collection<Node> getUniqueNodeListIntersection(Node node) {
        return this.getUniqueNodeList().intersect(node.getUniqueNodeList());
    }

    @Override
    public String toString() {
        return "Node: " + this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        return this.id.compareTo(((Node) obj).id) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.uniqueNodeList);
        return hash;
    }
    
}
