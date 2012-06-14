package com.tencent.mqq.qmc.core;

import com.tencent.mqq.qmc.Recordable;
import com.tencent.mqq.qmc.Replayable;

/**
 * the basic interface we use in this framework. now we only support record and replay operations<p>
 * we will go further developing other features. currently we have only adb implementations
 * @author rainliwang
 *
 */
public interface RRDevice extends Recordable,Replayable{
	void stopRecord();
	void stopReplay();
}
