package com.tencent.mqq.qmc.core;


public interface RRBridge {
	  /**
     * Wait for a default device to connect to the client.
     *
     * @return the connected device (or null if timeout);
     */
    RRDevice waitForConnection();

    /**
     * Wait for a device to connect to the client.
     *
     * @param timeoutMs how long (in ms) to wait
     * @param deviceIdRegex the regular expression to specify which device to wait for.
     * @return the connected device (or null if timeout);
     */
    RRDevice waitForConnection(long timeoutMs, String deviceIdRegex);

    /**
     * Shutdown the bridge and cleanup any resources it was using.
     */
    void shutdown();
}
