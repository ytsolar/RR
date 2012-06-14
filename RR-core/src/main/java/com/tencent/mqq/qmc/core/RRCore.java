package com.tencent.mqq.qmc.core;

import java.util.Map;
import java.util.TreeMap;

import com.tencent.mqq.qmc.adb.AdbBridge;


/**
 * RRCore is a host-side library that provides an API for communication with  a device.
 *  This class provides an entry point to setting up communication with a device. 
 *  Currently it only supports communciation over ADB, however.
 * 
 * @author rainliwang
 *
 */
public class RRCore {

    private final RRBridge bridge;

    private RRCore(RRBridge bridge) {
        this.bridge = bridge;
    }

    public static RRCore getInstance(Map<String, String> options) {
        RRBridge bridge = createBridgeByName(options.get("bridge"));
        if (bridge == null) {
            return null;
        }
        RRCore rrCore = new RRCore(bridge);
        return rrCore;
    }


	/** Generates a new instance of ChimpChat using default settings
     * @return a new instance of ChimpChat or null if errors occur during creation
     */
    public static RRCore getInstance() {
        Map<String, String> options = new TreeMap<String, String>();
        options.put("bridge", "adb");
        return RRCore.getInstance(options);
    }


    /**
     * Creates a specific bridge by name.
     *
     * @param bridgeName the name of the bridge to create
     * @return the new bridge, or null if none were found.
     */

    private static RRBridge createBridgeByName(String bridgeName) {
        if ("adb".equals(bridgeName)) {
            return new AdbBridge();
        } else {
            return null;
        }
    }

    /**
     * Retrieves an instance of the device from the bridge
     * @param timeoutMs length of time to wait before timing out
     * @param deviceId the id of the device you want to connect to
     * @return an instance of the device
     */
    public RRDevice waitForConnection(long timeoutMs, String deviceId){
        return bridge.waitForConnection(timeoutMs, deviceId);
    }

    /**
     * Retrieves an instance of the device from the bridge.
     * Defaults to the longest possible wait time and any available device.
     * @return an instance of the device
     */
    public RRDevice waitForConnection(){
        return bridge.waitForConnection(Integer.MAX_VALUE, ".*");
    }

    /**
     * Shutdown and clean up chimpchat.
     */
    public void shutdown(){
        bridge.shutdown();
    }

    
    public static void main(String[] args) {
		RRCore rrCore = RRCore.getInstance();
		RRDevice device = rrCore.waitForConnection();
		device.record();
		rrCore.shutdown();
	}
}
