package com.tencent.mqq.qmc.core;

import com.tencent.mqq.qmc.Record;
import com.tencent.mqq.qmc.Recordable;
import com.tencent.mqq.qmc.Stoppable;
import com.tencent.mqq.qmc.util.RRException;

/**
 * 
 * abstract recorder for record user operations.<p>
 * maybe we have a lot kinds of recorders. like monkeyrunner recorder, linux event recorder, adb shell getevent recorder,etc
 * @author rainliwang
 *
 */
public abstract class RRRecorder implements Recordable, Stoppable {

	@Override
	public Record record() {
		if (!isReady()) {
			init();
		}
		return doRecord();
	}

	public boolean stop() {
		if (isRecording()) {
			throw new RRException("recorder is already stopped");
		}
		return doStop();
	}

	protected abstract boolean doStop();

	protected abstract boolean isRecording();

	protected abstract Record doRecord();

	protected abstract boolean isReady();

	/**
	 * initialization the recorder
	 */
	protected abstract void init();

}
