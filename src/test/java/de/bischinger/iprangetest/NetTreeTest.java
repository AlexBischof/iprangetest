package de.bischinger.iprangetest;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeSet;

import static de.bischinger.iprangetest.Net.net;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by bischofa on 18/03/16.
 */
public class NetTreeTest {

    private Net small;
    private Net medium;
    private Net mediumParallel;
    private Net mediumBig;
    private Net big;
    private NetRepository netRepository;

    @Before
    public void before() {
        netRepository = new NetRepository();

        small = net("2.152.201.10", "2.152.201.10");
        medium = net("192.168.0.0", "192.168.0.10");
        mediumParallel = net("192.168.0.11", "192.168.0.20");
        mediumBig = net("192.168.0.0", "192.168.0.255");
        big = net("0.0.0.0", "255.255.255.255");

        netRepository.init(new TreeSet(asList(small, medium, mediumParallel, mediumBig, big)));
    }


    @Test
    public void testExactHits() {
        assertThat(netRepository.get("192.168.0.0")).isEqualTo(medium);
        assertThat(netRepository.get("2.152.201.10")).isEqualTo(small);
        assertThat(netRepository.get("192.168.0.11")).isEqualTo(mediumParallel);
    }

    @Test
    public void testNonExactHits() {
        assertThat(netRepository.get("192.168.0.1")).isEqualTo(medium);
        assertThat(netRepository.get("192.168.0.10")).isEqualTo(medium);
        assertThat(netRepository.get("192.168.0.11")).isEqualTo(mediumParallel);
        assertThat(netRepository.get("192.168.0.21")).isEqualTo(mediumBig);
        assertThat(netRepository.get("202.168.0.21")).isEqualTo(big);
    }
}