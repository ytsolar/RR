package com.tencent.mqq.qmc.adb;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.tencent.mqq.qmc.core.RRBridge;
import com.tencent.mqq.qmc.core.RRDevice;

public class AdbBridge implements RRBridge {
	private static Logger logger = LoggerFactory.getLogger(AdbBridge.class);
	// How long to wait each time we check for the device to be connected.
	private static final int CONNECTION_ITERATION_TIMEOUT_MS = 200;
	private final List<RRDevice> devices = new ArrayList<RRDevice>();
	private final AndroidDebugBridge bridge;

	public AdbBridge() {
		AndroidDebugBridge.init(false /* debugger support */);
		bridge = AndroidDebugBridge.createBridge();
	}

	@Override
	public RRDevice waitForConnection() {
		 return waitForConnection(Integer.MAX_VALUE, ".*");
	}
	 /**
     * Checks the attached devices looking for one whose device id matches the specified regex.
     *
     * @param deviceIdRegex the regular expression to match against
     * @return the Device (if found), or null (if not found).
     */
    private IDevice findAttachedDevice(String deviceIdRegex) {
        Pattern pattern = Pattern.compile(deviceIdRegex);
        for (IDevice device : bridge.getDevices()) {
            String serialNumber = device.getSerialNumber();
            if (pattern.matcher(serialNumber).matches()) {
                return device;
            }
        }
        return null;
    }

	@Override
	public RRDevice waitForConnection(long timeoutMs, String deviceIdRegex) {
		 do {
	            IDevice device = findAttachedDevice(deviceIdRegex);
	            // Only return the device when it is online
	            if (device != null && device.getState() == IDevice.DeviceState.ONLINE) {
	                RRDevice chimpDevice = new AdbDevice(device);
	                devices.add(chimpDevice);
	                return chimpDevice;
	            }

	            try {
	                Thread.sleep(CONNECTION_ITERATION_TIMEOUT_MS);
	            } catch (InterruptedException e) {
	            	logger.error("Error sleeping", e);
	            }
	            timeoutMs -= CONNECTION_ITERATION_TIMEOUT_MS;
	        } while (timeoutMs > 0);

	        // Timeout.  Give up.
	        return null;
	}

	@Override
	public void shutdown() {
		bridge.terminate();
	}

}
