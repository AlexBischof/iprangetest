package de.bischinger.iprangetest;

import static de.bischinger.iprangetest.IPUtils.ip2Long;
import static de.bischinger.iprangetest.IPUtils.long2String;
import static java.util.Comparator.comparingLong;

/**
 * Created by bischofa on 18/03/16.
 */
public class Net implements Comparable<Net> {

    long von;
    long bis;

    public Net() {
    }

    public Net(long von, long bis) {
        this.von = von;
        this.bis = bis;
    }

    public static Net ip(String ip) {
        return new Net(ip2Long(ip), ip2Long(ip));
    }

    public static Net net(String von, String bis) {
        return new Net(ip2Long(von), ip2Long(bis));
    }

    public static Net net(long von, long bis) {
        return new Net(von, bis);
    }


    public long getVon() {
        return von;
    }

    public long getBis() {
        return bis;
    }

    public long getDist() {
        return bis - von;
    }

    public boolean contains(Net o) {
        return this.von <= o.von && this.bis >= o.bis;
    }

    @Override
    public int compareTo(Net o) {
        return comparingLong(Net::getBis).thenComparing(net -> net.contains(o)).compare(this, o);
    }

    @Override
    public String toString() {
        return "Net{" +
                "von=" + long2String(von) +
                ", bis=" + long2String(bis) +
                ", dist=" + getDist() +
                '}';
    }
}
