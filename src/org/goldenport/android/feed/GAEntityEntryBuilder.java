package org.goldenport.android.feed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Parcelable;

/*
 * @since   Jul. 23, 2011
 * @version Jul. 23, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAEntityEntryBuilder<T extends Parcelable> extends GAEntryBuilder {
    private T entity;

    public GAEntityEntryBuilder() {
    }

    public GAEntityEntryBuilder(String id, GAText title, GAText summary,
            GADateTime published, GADateTime updated, List<GACategory> categories,
            List<GALink> links, List<GAPerson> authors,
            List<GAPerson> contributors, GAText rights, GAFeed source,
            GAContent content, List<String> extensionElements,
            Map<String, Object> properties,
            T entity) {
        super(id, title, summary, published, updated, categories,
                links, authors, contributors, rights, source,
                content, extensionElements, properties);
        this.entity = entity;
    }

    public GAEntityEntry<T> build() {
        return new GAEntityEntry<T>(
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
                to_LinkedHashMap(properties),
                entity);
    }
}
