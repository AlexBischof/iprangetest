/*******************************************************************************
 * Copyright (c) 2012 GigaSpaces Technologies Ltd. All rights reserved
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.bischinger.iprangetest;

/**
 * A utility class for IP manipulation and validation.
 *
 * @author noak
 * @since 2.6.1
 */
public final class IPUtils {


    // hidden constructor
    private IPUtils() {
    }

    // timeout in seconds, waiting for a socket to connect
    private static final int IP_BYTE_RANGE = 256;
    private static final int IPV4_PARTS = 4;


    //security constants
    private static final String SPRING_ACTIVE_PROFILE_ENV_VAR = "SPRING_PROFILES_ACTIVE";

    /**
     * Converts a standard IP address to a long-format IP address.
     *
     * @param ipAddress A standard IP address
     * @return IP address as a long value
     * @throws IllegalArgumentException Indicates the given IP is invalid
     */
    public static long ip2Long(final String ipAddress) throws IllegalArgumentException {
        if (!validateIPAddress(ipAddress)) {
            throw new IllegalArgumentException("Invalid IP address: " + ipAddress);
        }

        final byte[] ipBytes = getIPv4BytesArray(ipAddress);
        return ((long) (byte2int(ipBytes[0]) * IP_BYTE_RANGE + byte2int(ipBytes[1]))
                * IP_BYTE_RANGE + byte2int(ipBytes[2]))
                * IP_BYTE_RANGE + byte2int(ipBytes[3]);
    }

    /**
     * Converts a long value representing an IP address to a standard IP address
     * (dotted decimal format).
     *
     * @param ip long value representing an IP address
     * @return A standard IP address
     */
    public static String long2String(final long ip) {
        final long a = (ip & 0xff000000) >> 24;
        final long b = (ip & 0x00ff0000) >> 16;
        final long c = (ip & 0x0000ff00) >> 8;
        final long d = ip & 0xff;

        return a + "." + b + "." + c + "." + d;
    }

    /**
     * Converts a standard IP address to a byte array.
     *
     * @param ipAddress IP address as a standard IP address (dotted decimal format)
     * @return IP as a 4-element byte array
     * @throws IllegalArgumentException Indicates the given IP is invalid
     */
    public static byte[] getIPv4BytesArray(final String ipAddress) throws IllegalArgumentException {
        // This implementation is commented out because it involves resolving
        // the host, which we want to avoid.
        // return InetAddress.getByName(ipAddress).getAddress();
        final byte[] addrArr = new byte[IPV4_PARTS];
        final String[] ipParts = ipAddress.split("\\.");
        if (ipParts.length == IPV4_PARTS) {
            for (int i = 0; i < IPV4_PARTS; i++) {
                addrArr[i] = (byte) Integer.parseInt(ipParts[i]);
            }
        } else {
            throw new IllegalArgumentException("Invalid IP address: " + ipAddress);
        }

        return addrArr;
    }

    /**
     * Validates a standard IP address (dotted decimal format).
     *
     * @param ipAddress IP address to validate (in a dotted decimal format)
     * @return true if valid, false if invalid
     */
    public static boolean validateIPAddress(final String ipAddress) {
        boolean valid = false;


        // a simple split by dots (.), escaped.
        final String[] ipParts = ipAddress.split("\\.");
        if (ipParts.length == IPV4_PARTS) {
            for (final String part : ipParts) {
                final int intValue = Integer.parseInt(part);
                if (intValue < 0 || intValue > IP_BYTE_RANGE - 1) {
                    valid = false;
                    break;
                }
                valid = true;
            }
        }

        return valid;
    }

    /**
     * Converts (unsigned) byte to int.
     *
     * @param b byte to convert
     * @return int value representing the given byte
     */
    public static int byte2int(final byte b) {
        int i = b;
        if (b < 0) {
            i = b & 0x7f + 128;
        }

        return i;
    }
}