package org.goldenport.android.feed;

import java.util.Date;
import java.util.TimeZone;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GADateTime implements Parcelable {
    public final Date datetime;
    public final TimeZone timezone;

    public GADateTime(Date datetime, TimeZone timezone) {
        this.datetime = datetime;
        this.timezone = timezone;
    }

    public GADateTime(String text) {
        this.datetime = null;        // TODO Auto-generated constructor stub
        this.timezone = null;        // TODO Auto-generated constructor stub
    }

    public GADateTime(Parcel in) {
        datetime = new Date(in.readLong());
        timezone = TimeZone.getTimeZone(in.readString());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(datetime.getTime());
        dest.writeString(timezone.getID());
    }

    public static final Parcelable.Creator<GADateTime> CREATOR = new Parcelable.Creator<GADateTime>() {  
        public GADateTime createFromParcel(Parcel in) {  
            return new GADateTime(in);
        }

        public GADateTime[] newArray(int size) {  
            return new GADateTime[size];  
        }  
    };  
}
