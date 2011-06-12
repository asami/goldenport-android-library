package org.goldenport.android.feed;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GAContent implements Parcelable {
    public final TextType type;

    protected GAContent(TextType type) {
        this.type = type;
    }

    protected GAContent(Parcel in) {
        type = TextType.valueOf(in.readString());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type.name());
    }
}
