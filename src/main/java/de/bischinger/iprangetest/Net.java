package de.bischinger.iprangetest;

import java.io.Serializable;
import java.util.Comparator;

import static de.bischinger.iprangetest.IPUtils.ip2Long;
import static de.bischinger.iprangetest.IPUtils.long2String;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;

/**
 * Created by bischofa on 18/03/16.
 */
public class Net implements Comparable<Net> , Serializable{

    long von;
    long bis;
    Long dist;

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
        if (dist == null) {
            dist = bis - von;
        }
        return dist;
    }

    public boolean contains(Net o) {
        return this.von <= o.von && this.bis >= o.bis;
    }

    @Override
    public int compareTo(Net o) {
      //  Comparator<Net> containsComparator = comparing(net -> net.contains(o));
      //  return containsComparator.thenComparing(Net::getBis).compare(this, o);

        Comparator<Net> containsComparator = comparing(net -> net.contains(o));
        Comparator<Net> reversedVon = comparing(Net::getVon).reversed();
        int compare = Comparator.comparing(Net::getBis)
                //.thenComparing(reversedVon)
                .thenComparing(containsComparator).compare(this, o);
        return compare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Net net = (Net) o;

        if (von != net.von) return false;
        return bis == net.bis;

    }

    @Override
    public int hashCode() {
        int result = (int) (von ^ (von >>> 32));
        result = 31 * result + (int) (bis ^ (bis >>> 32));
        return result;
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
