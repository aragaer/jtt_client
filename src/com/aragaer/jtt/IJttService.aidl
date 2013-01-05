package com.aragaer.jtt;
import com.aragaer.jtt.JTTHour;
import com.aragaer.jtt.JttInvalidateCallback;

interface IJttService {
	long[] getTr(long jdn);
	void registerInvalidateCallback(JttInvalidateCallback cb);
	void unregisterInvalidateCallback(JttInvalidateCallback cb);
}
