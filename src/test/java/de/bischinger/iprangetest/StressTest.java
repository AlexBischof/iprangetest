package de.bischinger.iprangetest;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static de.bischinger.iprangetest.IPUtils.ip2Long;
import static de.bischinger.iprangetest.Net.net;
import static java.util.Arrays.asList;
import static java.util.stream.LongStream.range;

/**
 * Created by bischofa on 20/03/16.
 */
public class StressTest {

    private NetRepository netRepository;

    @Before
    public void before() {
        netRepository = new NetRepository();

        Net small = net("2.152.201.10", "2.152.201.10");
        Net medium = net("192.168.0.0", "192.168.0.10");
        Net mediumParallel = net("192.168.0.11", "192.168.0.20");
        Net mediumBig = net("192.168.0.0", "192.168.0.255");
        Net mediumBig2 = net("192.167.0.0", "192.168.0.255");
        Net big = net("0.0.0.0", "255.255.255.255");


        TreeSet nets = new TreeSet();
        range(0,  5_000_000).forEach(ip -> nets.add(Net.net(ip, ip)));
        nets.addAll(asList(small, medium, mediumParallel, mediumBig, mediumBig2, big));
        netRepository.init(nets);
    }

    @Test(timeout = 1000)
    public void test() {
        range(1, 700_000).forEach(netRepository::get);
    }
}
