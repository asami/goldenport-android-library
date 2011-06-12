package org.goldenport.android.feed;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 11, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAText implements Parcelable {
    public final TextType type;
    public final String text;

    public GAText(TextType type, String text) {
        this.type = type;
        this.text = text;
    }

    public GAText(String title) {
        this(TextType.text, title);
    }

    public GAText(Parcel in) {
        type = TextType.valueOf(in.readString());
        text = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type.name());
        dest.writeString(text);
    }

    public static final Parcelable.Creator<GAText> CREATOR = new Parcelable.Creator<GAText>() {  
        public GAText createFromParcel(Parcel in) {  
            return new GAText(in);
        }

        public GAText[] newArray(int size) {  
            return new GAText[size];
        }  
    };  
}
