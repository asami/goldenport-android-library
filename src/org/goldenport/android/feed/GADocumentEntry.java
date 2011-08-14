package org.goldenport.android.feed;

import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jul. 20, 2011
 * @version Aug. 13, 2011
 * @author  ASAMI, Tomoharu
 */
public class GADocumentEntry<T extends Parcelable> extends GAEntry {
    public final T document;

    public GADocumentEntry(
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
        this.document = entity;
    }

    public GADocumentEntry(Parcel in) {
        super(in);
        document = in.readParcelable(getClass().getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public GADocumentEntryBuilder builder() {
        return new GADocumentEntryBuilder(
        );
    }

    public static final Parcelable.Creator<GADocumentEntry<?>> CREATOR = new Parcelable.Creator<GADocumentEntry<?>>() {
        public GADocumentEntry<?> createFromParcel(Parcel in) {  
            return new GADocumentEntry(in);
        }

        public GADocumentEntry<?>[] newArray(int size) {  
            return new GADocumentEntry<?>[size];
        }
    };
}
