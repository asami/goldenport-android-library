package org.goldenport.android.feed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jul. 20, 2011
 * @version Aug. 13, 2011
 * @author  ASAMI, Tomoharu
 */
public class GADocumentFeed<T extends Parcelable> extends GAFeed {
    public final List<GADocumentEntry<T>> documents;

    public GADocumentFeed(
            String id,
            GAText title,
            GAText subtitle,
            GADateTime updated,
            List<GADocumentEntry<T>> entries,
            List<GACategory> categories,
            List<GALink> links,
            List<GAPerson> contributors,
            String generator,
            GAText rights,
            Uri icon,
            Uri logo,
            List<String> extensionElements,
            GASchema schema,
            Map<String, Object> properties) {
        super(id, title, subtitle, updated, to_entries(entries), categories, links,
                contributors, generator, rights, icon, logo, 
                extensionElements, schema, properties);
        this.documents = Collections.unmodifiableList(entries);
    }

    private static List<GAEntry> to_entries(List<?> entities) {
        List<GAEntry> list = new ArrayList<GAEntry>();
        for (Object entry: entities) {
            list.add((GAEntry)entry);
        }
        return list;
    }

    public GADocumentFeed(Parcel in) {
        super(in);
        List<GADocumentEntry<T>> list = to_entities();
        documents = Collections.unmodifiableList(list);
    }

    private List<GADocumentEntry<T>> to_entities() {
        List<GADocumentEntry<T>> list = new ArrayList<GADocumentEntry<T>>();
        for (GAEntry entry: entries) {
            list.add((GADocumentEntry<T>)entry);
        }
        return list;
    }

    public List<T> getDocuments() {
        List<T> list = new ArrayList<T>();
        for (GADocumentEntry<T> entry: documents) {
            list.add(entry.document);
        }
        return list;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Parcelable.Creator<GADocumentFeed<?>> CREATOR = new Parcelable.Creator<GADocumentFeed<?>>() {  
        public GADocumentFeed<?> createFromParcel(Parcel in) {
            return new GADocumentFeed(in);
        }

        public GADocumentFeed<?>[] newArray(int size) {
            return new GADocumentFeed<?>[size];
        }
    };
}
