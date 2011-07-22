package org.goldenport.android.feed;

import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jul. 20, 2011
 * @version Jul. 23, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAEntityEntry<T extends Parcelable> extends GAEntry {
    public final T entity;

    public GAEntityEntry(
            String id,
            GAText title,
            GAText summary,
            GADateTime published,
            GADateTime updated,
            List<GACategory> categories,
            List<GALink> links,
            List<GAPerson> authors,
            List<GAPerson> contributors,
            GAText rights,
            GAFeed source,
            GAContent content,
            List<String> extensionElements,
            Map<String, Object> properties,
            T entity) {
        super(id, title, summary, published, updated, categories, links, authors,
                contributors, rights, source, content, extensionElements, properties);
        this.entity = entity;
    }

    public GAEntityEntry(Parcel in) {
        super(in);
        entity = in.readParcelable(getClass().getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public GAEntityEntryBuilder builder() {
        return new GAEntityEntryBuilder(
        );
    }

    public static final Parcelable.Creator<GAEntityEntry<?>> CREATOR = new Parcelable.Creator<GAEntityEntry<?>>() {
        public GAEntityEntry<?> createFromParcel(Parcel in) {  
            return new GAEntityEntry(in);
        }

        public GAEntityEntry<?>[] newArray(int size) {  
            return new GAEntityEntry<?>[size];
        }
    };
}
