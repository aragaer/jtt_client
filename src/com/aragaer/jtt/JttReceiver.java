package com.aragaer.jtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class JttReceiver extends BroadcastReceiver {
	public final static String TICK_ACTION = "com.aragaer.jtt.ACTION_JTT_TICK";
	public final static IntentFilter filter = new IntentFilter(TICK_ACTION);

	abstract void handle_tick(int n, int q, int f);

	public void onReceive(Context context, Intent intent) {
		JTTHour h = intent.getParcelableExtra("jtt");
		handle_tick(h.num, h.quarter, h.quarter_parts);
	}
}
