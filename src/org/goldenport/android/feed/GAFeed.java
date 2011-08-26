package org.goldenport.android.feed;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun.  4, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAFeed implements Parcelable {
    public final String id;
    public final GAText title;
    public final GAText subtitle;
    public final GADateTime updated;
    public final List<GAEntry> entries;
    public final List<GACategory> categories;
    public final List<GALink> links;
    public final List<GAPerson> contributors;
    public final String generator;
    public final GAText rights;
    public final Uri icon;
    public final Uri logo;
    public final List<String> extensionElements;
    public final GASchema schema;
    public final Map<String, Object> properties;

    public GAFeed(
            String id,
            GAText title,
            GAText subtitle,
            GADateTime updated,
            List<GAEntry> entries,
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
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.updated = updated;
        this.entries = to_list(entries);
        this.categories = to_list(categories);
        this.links = to_list(links);
        this.contributors = to_list(contributors);
        this.generator = generator;
        this.rights = rights;
        this.icon = icon;
        this.logo = logo;
        this.extensionElements = extensionElements;
        this.properties = to_map(properties);
        this.schema = schema;
    }

    public GAFeed(Parcel in) {
        ClassLoader loader = getClass().getClassLoader();
        id = in.readString();
        title = in.readParcelable(loader);
        subtitle = in.readParcelable(loader);
        updated = in.readParcelable(loader);
        entries = to_list(in.readArrayList(loader));
        categories = to_list(in.readArrayList(loader));
        links = to_list(in.readArrayList(loader));
        contributors = to_list(in.readArrayList(loader));
        generator = in.readString();
        rights = in.readParcelable(loader);
        icon = in.readParcelable(loader);
        logo = in.readParcelable(loader);
        extensionElements = to_list(in.readArrayList(loader));
        schema = in.readParcelable(loader);
        properties = to_map(in.readHashMap(loader));
    }

    private <T extends List<?>> T to_list(List<?> list) {
        if (list == null) {
            return (T)Collections.emptyList();
        } else {
            return (T)Collections.unmodifiableList(list);
        }
    }

    private Map<String, Object> to_map(Map<String, Object> map) {
        if (map == null) {
            return Collections.emptyMap();
        } else {
            return Collections.unmodifiableMap(map);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(title, flags);
        dest.writeParcelable(subtitle, flags);
        dest.writeParcelable(updated, flags);
        dest.writeList(entries);
        dest.writeList(categories);
        dest.writeList(links);
        dest.writeList(contributors);
        dest.writeString(generator);
        dest.writeParcelable(rights, flags);
        dest.writeParcelable(icon, flags);
        dest.writeParcelable(logo, flags);
        dest.writeList(extensionElements);
        dest.writeParcelable(schema, flags);
        dest.writeMap(properties);
    }

    public GAFeedBuilder builder() {
        return new GAFeedBuilder(
        );
    }

    // twitter
    public String getLinkAalternate() {
        throw new UnsupportedOperationException();
    }

    // twitter
    public String getLinkSelf() {
        throw new UnsupportedOperationException();
    }

    // twitter, opensearchdescription+xml    
    public String getLinkSearch() {
        throw new UnsupportedOperationException();

    }

    // twitter
    public String getLinkRefresh() {
        throw new UnsupportedOperationException();
        
    }

    // twitter
    public String getLinkNext() {
        throw new UnsupportedOperationException();
        
    }

    // twitter, openSearch:itemsPerPage    
    public String getExtItemsPerPage() {
        throw new UnsupportedOperationException();
        
    }

    // twitter:warning    
    public final String getExtWarning() {
        throw new UnsupportedOperationException();
        
    }

    public static final Parcelable.Creator<GAFeed> CREATOR = new Parcelable.Creator<GAFeed>() {  
        public GAFeed createFromParcel(Parcel in) {  
            return new GAFeed(in);
        }

        public GAFeed[] newArray(int size) {  
            return new GAFeed[size];  
        }  
    };  

    public static GAFeed create(Bundle bundle) {
        GAFeedBuilder builder = new GAFeedBuilder();
        for (String key: bundle.keySet()) {
            Object value = bundle.get(key);
            builder.setValue(key, value);
        }
        return builder.build();
    }

    public static GAFeed create(ContentValues values) {
        GAFeedBuilder builder = new GAFeedBuilder();
        for (Entry<String, Object> entry: values.valueSet()) {
            builder.setValue(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
