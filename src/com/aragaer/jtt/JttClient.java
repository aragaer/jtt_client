package com.aragaer.jtt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public abstract class JttClient {
	private static final String TAG = JttClient.class.getSimpleName();

	private Context context;
	private final static Intent service_intent = new Intent(
			"com.aragaer.jtt.SERVICE");

	private IJttService service;

	private final ServiceConnection conn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = IJttService.Stub.asInterface((IBinder) boundService);
			Log.d(TAG, "Service connected");
			onConnected();
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
			Log.d(TAG, "Service disconnected");
		}
	};

	public JttClient(Context ctx) {
		context = ctx;
	}

	public abstract void onConnected();

	public void bind() {
		context.bindService(service_intent, conn, 0);
	}

	public void release() {
		context.unbindService(conn);
		context = null;
	}

	public JTTHour now() {
		if (service == null)
			throw new IllegalStateException();
		try {
			return service.now();
		} catch (RemoteException e) {
			Log.e(TAG, "Failed to get 'now': " + e);
			return null;
		}
	}
}
