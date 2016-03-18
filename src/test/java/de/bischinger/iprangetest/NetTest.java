package de.bischinger.iprangetest;

import org.junit.Test;

import static de.bischinger.iprangetest.Net.net;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by bischofa on 18/03/16.
 */
public class NetTest {
    @Test
    public void testKleiner() {
        Net actual = net("192.168.0.0", "192.168.0.10");

        assertThat(actual).isLessThan(net("192.168.0.0", "192.168.0.255"));
        assertThat(actual).isLessThan(net("192.168.0.20", "192.168.0.30"));
        assertThat(actual).isLessThan(net("0.0.0.0", "255.255.255.255"));
    }

    @Test
    public void testGroesser() {
        Net actual = net("192.168.0.0", "192.168.0.10");

        assertThat(actual).isGreaterThan(net("192.168.0.0", "192.168.0.9"));
        assertThat(actual).isGreaterThan(net("192.167.0.20", "192.167.0.30"));
    }

    @Test
    public void testGleich() {
        assertThat(net("192.168.0.0", "192.168.0.10")).isEqualByComparingTo(net("192.168.0.0", "192.168.0.10"));
        assertThat(net("192.168.0.0", "192.168.0.0")).isEqualByComparingTo(net("192.168.0.0", "192.168.0.0"));
    }
}