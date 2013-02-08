package com.aragaer.jtt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class JttClient {
	private static final String TAG = JttClient.class.getSimpleName();

	private final static Intent service_intent = new Intent(
			"com.aragaer.jtt.SERVICE");

	private boolean is_bound;
	private IJttService service;

	private final ServiceConnection conn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = IJttService.Stub.asInterface((IBinder) boundService);
			Log.d(TAG, "Service connected");
			is_bound = true;
			onConnected();
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
			Log.d(TAG, "Service disconnected");
		}
	};

	public JttClient() {}

	protected void onConnected() {};

	public void bind(Context context) {
		if (is_bound)
			throw new IllegalStateException("Already bound");
		context.bindService(service_intent, conn, 0);
	}

	public void unbind(Context context) {
		if (!is_bound)
			throw new IllegalStateException("Not bound");
		is_bound = false;
		context.unbindService(conn);
	}

	public boolean is_bound() {
		return is_bound;
	}

	public long[] getTr(long jdn) {
		if (service == null)
			throw new IllegalStateException("Not registered");
		try {
			return service.getTr(jdn);
		} catch (RemoteException e) {
			Log.e(TAG, "getTr("+jdn+"): "+e);
			return null;
		}
	}
}
