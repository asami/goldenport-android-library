package org.goldenport.android.feed;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GALink implements Parcelable {
    public final String type;
    public final Uri href;
    public final String rel;
    
    public GALink(String type, Uri href, String rel) {
        this.type = type;
        this.href = href;
        this.rel = rel;
    }

    public GALink(Parcel in) {
        type = in.readString();
        href = in.readParcelable(null);
        rel = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeParcelable(href, flags);
        dest.writeString(rel);
    }

    public static final Parcelable.Creator<GALink> CREATOR = new Parcelable.Creator<GALink>() {  
        public GALink createFromParcel(Parcel in) {  
            return new GALink(in);
        }

        public GALink[] newArray(int size) {  
            return new GALink[size];
        }  
    };  
}
