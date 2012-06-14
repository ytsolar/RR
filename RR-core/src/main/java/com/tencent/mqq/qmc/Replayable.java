package com.tencent.mqq.qmc;

public interface Replayable {

	void replay(long id);

	void replay(long id, int times);

}
