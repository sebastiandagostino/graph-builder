package com.sebastiandagostino.graph.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkJsonMapping {

    @JsonProperty("from")
    private int from;

    @JsonProperty("to")
    private int to;

    @JsonProperty("latency")
    private int latency;

    public LinkJsonMapping(@JsonProperty("from") int from, @JsonProperty("to") int to, @JsonProperty("latency") int latency) {
        this.from = from;
        this.to = to;
        this.latency = latency;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getLatency() {
        return latency;
    }

}