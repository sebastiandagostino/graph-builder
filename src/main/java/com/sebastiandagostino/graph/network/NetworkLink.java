package com.sebastiandagostino.graph.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkLink {

    @JsonProperty("from")
    int from;

    @JsonProperty("to")
    int to;

    @JsonProperty("latency")
    int latency;

    public NetworkLink(int from, int to, int latency) {
        this.from = from;
        this.to = to;
        this.latency = latency;
    }

}
