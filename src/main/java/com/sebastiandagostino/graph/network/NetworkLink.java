package com.sebastiandagostino.graph.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkLink {

    @JsonProperty("from")
    int from;

    @JsonProperty("to")
    int to;

    @JsonProperty("latency")
    int latency;

    public NetworkLink() {
    }

    public NetworkLink(int from, int to, int latency) {
        this.from = from;
        this.to = to;
        this.latency = latency;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

}
