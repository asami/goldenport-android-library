package org.goldenport.android.feed;

import java.util.Collections;
import java.util.LinkedHashMap;
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
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAEntry implements Parcelable {
    public final String id;
    public final GAText title;
    public final GAText summary;
    public final GADateTime published;
    public final GADateTime updated;
    public final List<GACategory> categories;
    public final List<GALink> links;
    public final List<GAPerson> authors;
    public final List<GAPerson> contributors;
    public final GAText rights;
    public final GAFeed source;
    public final GAContent content;
    public final List<String> extensionElements;
    public final Map<String, Object> properties;

    public GAEntry(
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
            Map<String, Object> properties) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.published = published;
        this.updated = updated;
        this.categories = Collections.unmodifiableList(categories);
        this.links = Collections.unmodifiableList(links);
        this.authors = Collections.unmodifiableList(authors);
        this.contributors = Collections.unmodifiableList(contributors);
        this.rights = rights;
        this.source = source;
        this.content = content;
        this.extensionElements = Collections.unmodifiableList(extensionElements);
        this.properties = Collections.unmodifiableMap(properties);
    }

    @SuppressWarnings("unchecked")
    public GAEntry(Parcel in) {
        ClassLoader loader = getClass().getClassLoader();
        id = in.readString();
        title = in.readParcelable(loader);
        summary = in.readParcelable(loader);
        published = in.readParcelable(loader);
        updated = in.readParcelable(loader);
        categories = in.readArrayList(loader);
        links = in.readArrayList(loader);
        authors = in.readArrayList(loader);
        contributors = in.readArrayList(loader);
        rights = in.readParcelable(loader);
        source = in.readParcelable(loader);
        content = in.readParcelable(loader);
        extensionElements = in.readArrayList(loader);
        properties = to_LinkedHashMap(in.readBundle(loader));
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(title, flags);
        dest.writeParcelable(summary, flags);
        dest.writeParcelable(published, flags);
        dest.writeParcelable(updated, flags);
        dest.writeList(categories);
        dest.writeList(links);
        dest.writeList(authors);
        dest.writeList(contributors);
        dest.writeParcelable(rights, flags);
        dest.writeParcelable(source, flags);
        dest.writeParcelable(content, flags);
        dest.writeList(extensionElements);
        dest.writeBundle(to_Bundle(properties));
    }

    public GAEntryBuilder builder() {
        return new GAEntryBuilder(
                id,
                title,
                summary,
                published,
                updated,
                categories,
                links,
                authors,
                contributors,
                rights,
                source,
                content,
                extensionElements,
                properties);
    }

    // twitter, html/text
    public Uri getLinkAlternate() {
        throw new UnsupportedOperationException();
    }

    // twitter, html/text    
    public String getLinkNext() {
        throw new UnsupportedOperationException();
        
    }

    // twitter, image/png    
    public String getLinkImage() {
        throw new UnsupportedOperationException();
        
    }

    // twitter:geo    
    public String getExtGeo() {
        throw new UnsupportedOperationException();
        
    }

    // twitter:metadata    
    public String getExtMetadata() {
        throw new UnsupportedOperationException();
        
    }

    // twitter:source    
    public String getExtSource() {
        throw new UnsupportedOperationException();
        
    }

    // twitter:lang
    public String getExtLang() {
        throw new UnsupportedOperationException();
        
    }

    private LinkedHashMap<String, Object> to_LinkedHashMap(Bundle bundle) {
        // TODO Auto-generated method stub
        return null;
    }

    private Bundle to_Bundle(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return null;
    }

    public static final Parcelable.Creator<GAEntry> CREATOR = new Parcelable.Creator<GAEntry>() {  
        public GAEntry createFromParcel(Parcel in) {  
            return new GAEntry(in);
        }

        public GAEntry[] newArray(int size) {  
            return new GAEntry[size];  
        }
    };

    public static GAEntry create(Bundle bundle) {
        GAEntryBuilder builder = new GAEntryBuilder();
        for (String key: bundle.keySet()) {
            Object value = bundle.get(key);
            builder.setValue(key, value);
        }
        return builder.build();
    }

    public static GAEntry create(ContentValues values) {
        GAEntryBuilder builder = new GAEntryBuilder();
        for (Entry<String, Object> entry: values.valueSet()) {
            builder.setValue(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
