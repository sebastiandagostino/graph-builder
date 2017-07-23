package com.sebastiandagostino.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueNodeList {
    
    private final Set<Node> unl;
    
    public UniqueNodeList() {
        this.unl = new HashSet<>();
    }
    
    public UniqueNodeList(Collection collection) {
        this.unl = new HashSet<>(collection);
    }
    
    public boolean add(Node node) {
        return this.unl.add(node);
    }
    
    public boolean remove(Node node) {
        return this.unl.remove(node);
    }
        
    public int size() {
        return this.unl.size();
    }
    
    public boolean isEmpty() {
        return this.unl.isEmpty();
    }
    
    public Collection<Node> getUNL() {
        return this.unl;
    }
    
    public Collection<Node> intersect(UniqueNodeList otherUNL) {
        return this.unl.stream()
                .filter(otherUNL.getUNL()::contains)
                .collect(Collectors.toSet());
    }
        
}
