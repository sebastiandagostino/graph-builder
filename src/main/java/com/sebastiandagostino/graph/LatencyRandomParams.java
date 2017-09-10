package com.sebastiandagostino.graph;

import org.apache.commons.lang3.Validate;

import java.util.Random;

public class LatencyRandomParams {

    public static final int NODE_LATENCY_SEED = 123456789;

    public static final int LINK_LATENCY_SEED = 987654321;

    private Random nodeLatency = new Random(NODE_LATENCY_SEED);

    private Random linkLatency = new Random(LINK_LATENCY_SEED);

    private int maxNodeLatency;

    private int maxLinkLatency;

    public LatencyRandomParams(int maxNodeLatency, int maxLinkLatency) {
        Validate.isTrue(maxNodeLatency > 0);
        Validate.isTrue(maxLinkLatency > 0);
        this.maxNodeLatency = maxNodeLatency;
        this.maxLinkLatency = maxLinkLatency;
    }

    public int getNextNodeLatency() {
        return this.nodeLatency.nextInt(maxNodeLatency);
    }

    public int getNextLinkLatency() {
        return this.linkLatency.nextInt(maxLinkLatency);
    }

}
