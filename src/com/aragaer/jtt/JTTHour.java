package com.aragaer.jtt;

import android.os.Parcel;
import android.os.Parcelable;

public class JTTHour implements Parcelable {
	public static final int QUARTERS = 4;
	public static final int PARTS = 10; // split quarter to that much parts

	/* compatibility calculations */
	private static final int COMPAT_COEFF = QUARTERS * PARTS / 100;

	public static final String Glyphs[] = { "酉", "戌", "亥", "子", "丑", "寅", "卯",
			"辰", "巳", "午", "未", "申" };

	public boolean isNight;
	public int num; // 0 to 11, where 0 is hour of Cock and 11 is hour of Monkey
	public int quarter; // 0 to 3
	public int quarter_parts; // 0 to PARTS

	public JTTHour(int num) {
		this(num, QUARTERS / 2, 0);
	}

	public JTTHour(int n, int f) {
		this.setTo(n, f);
	}

	public JTTHour(int n, int q, int f) {
		this.setTo(n, q, f);
	}

	// compatibility method
	public void setTo(int n, int f) {
		f *= COMPAT_COEFF;
		int q = f / PARTS;
		f -= q * PARTS;
		setTo(n, q, f);
	}

	// Instead of reallocation, reuse existing object
	public void setTo(int n, int q, int f) {
		num = n;
		isNight = n < 6;
		quarter = q;
		quarter_parts = f;
	}

	public static final Parcelable.Creator<JTTHour> CREATOR = new Parcelable.Creator<JTTHour>() {
		public JTTHour createFromParcel(Parcel in) {
			return new JTTHour(in);
		}

		public JTTHour[] newArray(int size) {
			return new JTTHour[size];
		}
	};

	private JTTHour(Parcel in) {
		final int n = in.readInt();
		final int q = in.readInt();
		final int f = in.readInt();
		setTo(n, q, f);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(num);
		dest.writeInt(quarter);
		dest.writeInt(quarter_parts);
	}
}
