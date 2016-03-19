package de.bischinger.iprangetest;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import static de.bischinger.iprangetest.Net.ip;
import static de.bischinger.iprangetest.Net.net;
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
        return getInternal(ip(ipStr));
    }

    public Net get(long ip) {
        return getInternal(net(ip, ip));
    }

    private Net getInternal(Net ip) {
        AtomicInteger i = new AtomicInteger(0);
        Net net1 = netTreeSet.tailSet(ip).stream().filter(net -> {
            i.incrementAndGet();
            return net.contains(ip);
        }).findFirst().get();
        return net1;
    }

    @Override
    public String toString() {
        return "NetRepository{" +
                "netTreeSet=" + netTreeSet +
                ",\n" +
                '}';
    }
}
