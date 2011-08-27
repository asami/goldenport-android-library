package org.goldenport.android.feed;

import java.util.List;
import java.util.Map;

import android.os.Parcelable;

/*
 * @since   Jul. 23, 2011
 * @version Aug. 27, 2011
 * @author  ASAMI, Tomoharu
 */
public class GADocumentEntryBuilder<T extends IGADocument> extends GAEntryBuilder {
    private T entity;

    public GADocumentEntryBuilder() {
    }

    public GADocumentEntryBuilder(String id, GAText title, GAText summary,
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

    public GADocumentEntry<T> build() {
        return new GADocumentEntry<T>(
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

    public GADocumentEntryBuilder<T> withDocument(T entity) {
        this.entity = entity;
        this.id = entity.get_entry_id(id);
        String t = entity.get_entry_title(title == null ? null : title.text);
        if (t != null) {
            this.title = new GAText(t);
        }
        String s = entity.get_entry_summary(summary == null ? null : summary.text);
        if (s != null) {
            this.summary = new GAText(s);
        }
        return this;
    }
}
