package com.tencent.mqq.qmc.nativepkg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.IShellOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.tencent.mqq.qmc.Record;
import com.tencent.mqq.qmc.core.RRRecorder;
import com.tencent.mqq.qmc.util.RRException;

/**
 * 
 * we use a c program to record the linux input event from /dev/input/eventX file
 * @author rainliwang
 *
 */
public class NativeRecorder extends RRRecorder{
	Logger logger = LoggerFactory.getLogger(NativeRecorder.class);
	private IDevice device;
	private static final String RECORDER_PATH = "/data/local/rr/recorder";
	private boolean isRecording=false;
	
	
	
	public NativeRecorder(IDevice device) {
		super();
		this.device = device;
	}

	@Override
	protected boolean isRecording() {
		return isRecording&&checkProcessExists();
	}

	/**
	 * check if the recorder progress is still running
	 * @return true if the progress is running
	 */
	private boolean checkProcessExists() {
		//TODO  
		return false;
	}

	@Override
	protected Record doRecord() {
		IShellOutputReceiver receiver=null;
		try {
			device.executeShellCommand("adb shell "+RECORDER_PATH, receiver);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShellCommandUnresponsiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected boolean isReady() {
		//check if the recorder file is already there
		//TOOD
		return false;
	}

	/* (non-Javadoc)
	 * @see com.tencent.mqq.qmc.core.RRRecorder#init()
	 */
	@Override
	protected void init() {
		//push the record file to the device
		try {
			device.pushFile(this.getClass().getClassLoader().getResource("libs/armeabi/event_record").getPath(), RECORDER_PATH);
		} catch (Exception e) {
			String msg = "failed pushing recorder to android device";
			logger.error(msg, e);
			throw new RRException(msg, e);
		}
		
	}

	@Override
	protected boolean doStop() {
		//TODO kill the progress 
		isRecording=checkProcessExists();
		return !isRecording;
	}


}
