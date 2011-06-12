package org.goldenport.android.feed;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun. 12, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class TextContent extends GAContent {
    public final String text;

    public TextContent(String text) {
        super(TextType.text);
        this.text = text;
    }

    public TextContent(Parcel in) {
        super(in);
        text = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(text);
    }

    public static final Parcelable.Creator<TextContent> CREATOR = new Parcelable.Creator<TextContent>() {  
        public TextContent createFromParcel(Parcel in) {  
            return new TextContent(in);
        }

        public TextContent[] newArray(int size) {  
            return new TextContent[size];  
        }  
    };
}
