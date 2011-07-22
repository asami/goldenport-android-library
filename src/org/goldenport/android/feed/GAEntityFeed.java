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
 * @version Jul. 22, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAEntityFeed<T extends Parcelable> extends GAFeed {
    public final List<GAEntityEntry<T>> entities;

    public GAEntityFeed(
            String id,
            GAText title,
            GAText subtitle,
            GADateTime updated,
            List<GAEntityEntry<T>> entries,
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
        this.entities = Collections.unmodifiableList(entries);
    }

    private static List<GAEntry> to_entries(List<?> entities) {
        List<GAEntry> list = new ArrayList<GAEntry>();
        for (Object entry: entities) {
            list.add((GAEntry)entry);
        }
        return list;
    }

    public GAEntityFeed(Parcel in) {
        super(in);
        List<GAEntityEntry<T>> list = to_entities();
        entities = Collections.unmodifiableList(list);
    }

    private List<GAEntityEntry<T>> to_entities() {
        List<GAEntityEntry<T>> list = new ArrayList<GAEntityEntry<T>>();
        for (GAEntry entry: entries) {
            list.add((GAEntityEntry<T>)entry);
        }
        return list;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static final Parcelable.Creator<GAEntityFeed<?>> CREATOR = new Parcelable.Creator<GAEntityFeed<?>>() {  
        public GAEntityFeed<?> createFromParcel(Parcel in) {
            return new GAEntityFeed(in);
        }

        public GAEntityFeed<?>[] newArray(int size) {
            return new GAEntityFeed<?>[size];
        }
    };
}
