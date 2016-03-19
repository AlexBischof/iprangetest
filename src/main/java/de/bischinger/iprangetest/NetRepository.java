package de.bischinger.iprangetest;

import java.util.Comparator;
import java.util.TreeSet;

import static de.bischinger.iprangetest.Net.ip;
import static java.util.Comparator.comparing;


/**
 * Created by bischofa on 18/03/16.
 */
public class NetRepository {

    //natural order of nets
    TreeSet<Net> netTreeSet;

    public void init(TreeSet<Net> nets) {
        this.netTreeSet = nets;
    }

    public Net get(String ipStr) {
        Net ip = ip(ipStr);
        return netTreeSet.tailSet(ip).stream().filter(net->net.contains(ip)).findFirst().get();
    }

    @Override
    public String toString() {
        return "NetRepository{" +
                "netTreeSet=" + netTreeSet +
                ",\n" +
                '}';
    }
}
