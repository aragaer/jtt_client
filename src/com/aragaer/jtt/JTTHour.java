package com.aragaer.jtt;

import android.os.Parcel;
import android.os.Parcelable;

public class JTTHour implements Parcelable {
    public static final String Glyphs[] = { "酉", "戌", "亥", "子", "丑", "寅", "卯",
            "辰", "巳", "午", "未", "申" };

    public int num; // 0 to 11, where 0 is hour of Cock and 11 is hour of Monkey
    public int fraction; // 0 to 99

    public JTTHour(int num) {
        this(num, 0);
    }

    public JTTHour(int n, int f) {
        this.setTo(n, f);
    }

    // Instead of reallocation, reuse existing object
    public void setTo(int n, int f) {
        num = n;
        fraction = f;
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
		final int f = in.readInt();
		setTo(n, f);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(num);
		dest.writeInt(fraction);
	}
}
