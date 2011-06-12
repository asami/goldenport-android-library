package org.goldenport.android.feed;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 12, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GACategory implements Parcelable {
    public final String term;
    public final Uri scheme;
    public final String label;

    public GACategory(String term, Uri scheme, String label) {
        this.term = term;
        this.scheme = scheme;
        this.label = label;
    }

    public GACategory(String term) {
        this.term = term;
        this.scheme = null;
        this.label = null;
    }

    public GACategory(Parcel in) {
        term = in.readString();
        scheme = in.readParcelable(null);
        label = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(term);
        dest.writeParcelable(scheme, flags);
        dest.writeString(label);
    }

    public static final Parcelable.Creator<GACategory> CREATOR = new Parcelable.Creator<GACategory>() {  
        public GACategory createFromParcel(Parcel in) {  
            return new GACategory(in);
        }

        public GACategory[] newArray(int size) {  
            return new GACategory[size];  
        }  
    };  
}
