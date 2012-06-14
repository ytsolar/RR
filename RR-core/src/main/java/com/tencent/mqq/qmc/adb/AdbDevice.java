package com.tencent.mqq.qmc.adb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.android.ddmlib.IDevice;
import com.tencent.mqq.qmc.Record;
import com.tencent.mqq.qmc.core.RRDevice;
import com.tencent.mqq.qmc.core.RRRecorder;
import com.tencent.mqq.qmc.core.RRReplayer;
import com.tencent.mqq.qmc.nativepkg.NativeRecorder;
import com.tencent.mqq.qmc.nativepkg.NativeReplayer;

public class AdbDevice implements RRDevice{
	Logger logger = LoggerFactory.getLogger(AdbDevice.class);
	private IDevice device;
	private RRRecorder recorder;
	private RRReplayer replayer;
	
	public AdbDevice(IDevice device) {
		super();
		this.device = device;
		this.recorder=new NativeRecorder(device);
		this.replayer=new NativeReplayer(device);
	}

	@Override
	public Record record() {
		return recorder.record();
	}

	@Override
	public void replay(long id) {
		this.replay(id, 1);
		
	}

	@Override
	public void replay(long id, int times) {
		replayer.replay(id,times);
	}

	@Override
	public void stopRecord() {
		recorder.stop();
	}

	@Override
	public void stopReplay() {
		replayer.stop();
		
	}

}
