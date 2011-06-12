package org.goldenport.android.feed;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun.  4, 2011
 * @version Jun.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAFacet {

    public static final Parcelable.Creator<GAFeed> CREATOR = new Parcelable.Creator<GAFeed>() {  
        public GAFeed createFromParcel(Parcel in) {  
            return new GAFeed(in);
        }

        public GAFeed[] newArray(int size) {  
            return new GAFeed[size];  
        }  
    };  
}
