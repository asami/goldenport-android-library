package org.goldenport.android.feed;

import java.util.Collections;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun.  4, 2011
 * @version Jun.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class GASchema implements Parcelable {
    public final List<GASchemaField> fields;

    public GASchema(List<GASchemaField> fields) {
        this.fields = Collections.unmodifiableList(fields);
    }

    public GASchema(Parcel in) {
        this.fields = Collections.unmodifiableList(in.readArrayList(getClass().getClassLoader()));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(fields);
    }  

    public static final Parcelable.Creator<GASchema> CREATOR = new Parcelable.Creator<GASchema>() {  
        public GASchema createFromParcel(Parcel in) {  
            return new GASchema(in);
        }

        public GASchema[] newArray(int size) {  
            return new GASchema[size];  
        }  
    };
}
