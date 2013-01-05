package com.aragaer.jtt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class JttReceiver extends BroadcastReceiver {
	public final static String TICK_ACTION = "com.aragaer.jtt.ACTION_JTT_TICK";
	public final static IntentFilter filter = new IntentFilter(TICK_ACTION);
	private JTTHour last;

	abstract void handle_tick(int n, int q, int f);

	public void onReceive(Context context, Intent intent) {
		last = intent.getParcelableExtra("jtt");
		handle_last();
	}

	public void handle_last() {
		if (last == null)
			throw new IllegalStateException("No tick yet");
		handle_tick(last.num, last.quarter, last.quarter_parts);
	}
}
