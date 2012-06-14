package com.tencent.mqq.qmc.core;

import com.tencent.mqq.qmc.Replayable;
import com.tencent.mqq.qmc.Stoppable;
import com.tencent.mqq.qmc.util.RRException;

/**
 * 
 * abstract replayer for replay user operations.<p>
 * maybe we have a lot kinds of replayers. like monkeyrunner replayer, linux event replayer, adb shell sendevent replayer,etc
 * @author rainliwang
 *
 */
public abstract class RRReplayer implements Replayable, Stoppable {

	@Override
	public void replay(long id) {
		replay(id, 1);
	}
	
	@Override
	public void replay(long id,int times) {
		if (!isReady()) {
			init();
		}
		doReplay(id, times);
	}
	

	public boolean stop() {
		if (isReplaying()) {
			throw new RRException("recorder is already stopped");
		}
		return doStop();
	}

	protected abstract boolean doStop();

	protected abstract boolean isReplaying();

	protected abstract void doReplay(long id,int times);

	protected abstract boolean isReady();

	/**
	 * initialization the replayer
	 */
	protected abstract void init();

}
