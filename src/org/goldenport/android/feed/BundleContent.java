package org.goldenport.android.feed;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class BundleContent extends GAContent {
    public final Bundle bundle;
    public final GASchema schema;

    public BundleContent(Bundle bundle, GASchema schema) {
        super(TextType.html);
        this.bundle = bundle;
        this.schema = schema;
    }

    public BundleContent(Parcel in) {
        super(in);
        this.bundle = in.readBundle();
        this.schema = in.readParcelable(getClass().getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBundle(bundle);
        dest.writeParcelable(schema, flags);
    }

    public static final Parcelable.Creator<BundleContent> CREATOR = new Parcelable.Creator<BundleContent>() {  
        public BundleContent createFromParcel(Parcel in) {  
            return new BundleContent(in);
        }

        public BundleContent[] newArray(int size) {  
            return new BundleContent[size];  
        }  
    };  
}

