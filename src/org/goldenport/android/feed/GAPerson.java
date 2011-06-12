package org.goldenport.android.feed;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAPerson implements Parcelable {
    public final String name;
    public final Uri uri;
    public final String email;
    
    public GAPerson(String name, Uri uri, String email) {
        this.name = name;
        this.uri = uri;
        this.email = email;
    }

    public GAPerson(String person) {
        this.name = person;
        this.uri = null;
        this.email = null;
    }

    public GAPerson(Parcel in) {
        name = in.readString();
        uri = in.readParcelable(null);
        email = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(uri, flags);
        dest.writeString(email);
    }

    public static final Parcelable.Creator<GAPerson> CREATOR = new Parcelable.Creator<GAPerson>() {  
        public GAPerson createFromParcel(Parcel in) {  
            return new GAPerson(in);
        }

        public GAPerson[] newArray(int size) {  
            return new GAPerson[size];  
        }  
    };  
}
