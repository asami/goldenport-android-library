package org.goldenport.android.feed;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun.  4, 2011
 * @version Jun.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAConstraint implements Parcelable {
    public GAConstraint(Parcel in) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }  

    public static final Parcelable.Creator<GAConstraint> CREATOR = new Parcelable.Creator<GAConstraint>() {  
        public GAConstraint createFromParcel(Parcel in) {  
            return new GAConstraint(in);
        }

        public GAConstraint[] newArray(int size) {  
            return new GAConstraint[size];  
        }  
    };
}
