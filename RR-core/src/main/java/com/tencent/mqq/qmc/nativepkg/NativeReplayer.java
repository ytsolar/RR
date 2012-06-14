package com.tencent.mqq.qmc.nativepkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.android.ddmlib.IDevice;
import com.tencent.mqq.qmc.core.RRReplayer;
import com.tencent.mqq.qmc.util.RRException;

public class NativeReplayer extends RRReplayer {
	Logger logger = LoggerFactory.getLogger(NativeReplayer.class);
	private IDevice device;
	private static final String REPLAYER_PATH = "/data/local/rr/relayer";
	private boolean isReplaying = false;

	
	public NativeReplayer(IDevice device) {
		super();
		this.device = device;
	}

	@Override
	protected boolean doStop() {
		// TODO kill the progress
		isReplaying = checkProcessExists();
		return !isReplaying;
	}

	private boolean checkProcessExists() {
		return false;
	}

	@Override
	protected boolean isReplaying() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doReplay(long id, int times) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void init() {
		// push the replay file to the device
		try {
			device.pushFile(
					this.getClass().getClassLoader()
							.getResource("lib/armeabi/event_replay").getPath(),
					REPLAYER_PATH);
		} catch (Exception e) {
			String msg = "failed pushing replayer to android device";
			logger.error(msg, e);
			throw new RRException(msg, e);
		}

	}

}
