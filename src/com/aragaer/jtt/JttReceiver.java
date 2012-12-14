package com.aragaer.jtt;

import java.lang.ref.WeakReference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class JttReceiver extends BroadcastReceiver {
	private WeakReference<Context> ctx;
	public final static String TICK_ACTION = "com.aragaer.jtt.ACTION_JTT_TICK";
	private final static IntentFilter filter = new IntentFilter(TICK_ACTION);

	abstract void handle_tick(int n, int q, int f);

	void register(Context context) {
		if (ctx != null)
			throw new IllegalStateException("Already registered");
		ctx = new WeakReference<Context>(context);
		context.registerReceiver(this, filter);
	}

	void unregister() {
		ctx.get().unregisterReceiver(this);
		ctx = null;
	}

	public void onReceive(Context context, Intent intent) {
		JTTHour h = intent.getParcelableExtra("jtt");
		handle_tick(h.num, h.quarter, h.quarter_parts);
	}
}
