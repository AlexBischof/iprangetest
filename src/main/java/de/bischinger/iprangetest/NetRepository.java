package de.bischinger.iprangetest;

import java.util.TreeSet;

import static de.bischinger.iprangetest.Net.ip;


/**
 * Created by bischofa on 18/03/16.
 */
public class NetRepository {

    private TreeSet<Net> netTreeSet;

    public void init(TreeSet<Net> nets) {
        this.netTreeSet = nets;

    }

    public Net get(String ip) {
        return netTreeSet.ceiling(ip(ip));
    }

    @Override
    public String toString() {
        return "NetRepository{" +
                "netTreeSet=" + netTreeSet +
                ",\n" +
                '}';
    }
}
