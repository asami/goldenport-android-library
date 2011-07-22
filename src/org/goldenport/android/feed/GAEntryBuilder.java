package org.goldenport.android.feed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Parcelable;

/*
 * @since   Jun.  6, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAEntryBuilder extends AbstractEntryBuilder {
    protected String id;
    protected GAText title;
    protected GAText summary;
    protected GADateTime published;
    protected GADateTime updated;
    protected ArrayList<GACategory> categories;
    protected ArrayList<GALink> links;
    protected ArrayList<GAPerson> authors;
    protected ArrayList<GAPerson> contributors;
    protected GAText rights;
    protected GAFeed source;
    protected GAContent content;
    protected ArrayList<String> extensionElements;
    protected LinkedHashMap<String, Object> properties;
    protected Bundle contents;

    public GAEntryBuilder() {
    }

    public GAEntryBuilder(String id, GAText title, GAText summary,
            GADateTime published, GADateTime updated, List<GACategory> categories,
            List<GALink> links, List<GAPerson> authors,
            List<GAPerson> contributors, GAText rights, GAFeed source,
            GAContent content, List<String> extensionElements,
            Map<String, Object> properties) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.published = published;
        this.updated = updated;
        this.categories = to_ParcelableArrayList(categories);
        this.links = to_ParcelableArrayList(links);
        this.authors = to_ParcelableArrayList(authors);
        this.contributors = to_ParcelableArrayList(contributors);
        this.rights = rights;
        this.source = source;
        this.content = content;
        this.extensionElements = to_StringArrayList(extensionElements);
        this.properties = to_LinkedHashMap(properties);
    }

    public GAEntry build() {
        return new GAEntry(
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
                to_LinkedHashMap(properties));
    }

    public AbstractEntryBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AbstractEntryBuilder withTitle(String title) {
        this.title = new GAText(title);
        return this;
    }

    public AbstractEntryBuilder withSummary(String summary) {
        this.summary = new GAText(summary);
        return this;
    }

    public AbstractEntryBuilder withPublished(String published) {
        this.published = new GADateTime(published);
        return this;
    }

    public AbstractEntryBuilder withUpdated(String updated) {
        this.updated = new GADateTime(updated);
        return this;
    }

    public AbstractEntryBuilder withCategories(List<String> categories) {
        this.categories = new ArrayList<GACategory>();
        for (String c: categories) {
            this.categories.add(new GACategory(c));
        }
        return this;
    }

    public AbstractEntryBuilder withCategories(String... categories) {
        return withCategories(to_StringArrayList(categories));
    }

    public AbstractEntryBuilder withLinks(List<GALink> links) {
        this.links = to_ParcelableArrayList(links);
        return this;
    }

    public AbstractEntryBuilder withLinks(GALink... links) {
        return withLinks(links);
    }

    public AbstractEntryBuilder withAuthors(List<GAPerson> authors) {
        this.authors = to_ParcelableArrayList(authors);
        return this;
    }

    public AbstractEntryBuilder withAuthors(GAPerson... authors) {
        return withAuthors(Arrays.asList(authors));
    }

    public AbstractEntryBuilder withAuthorsByString(List<String> authors) {
        this.authors = new ArrayList<GAPerson>();
        for (String a: authors) {
            this.authors.add(new GAPerson(a));
        }
        return this;
    }

    public AbstractEntryBuilder withAuthorsByString(String... authors) {
        withAuthorsByString(Arrays.asList(authors));
        return this;
    }

    public AbstractEntryBuilder withContributors(List<GAPerson> contributors) {
        this.contributors = to_ParcelableArrayList(contributors);
        return this;
    }

    public AbstractEntryBuilder withContributorsByString(GAPerson... contributors) {
        return withContributors(Arrays.asList(contributors));
    }

    public AbstractEntryBuilder withRights(String rights) {
        this.rights = new GAText(rights);
        return this;
    }    

    public AbstractEntryBuilder withSource(GAFeed source) {
        this.source = source;
        return this;
    }

    public AbstractEntryBuilder withContent(String content) {
        this.content = new TextContent(content);
        return this;
    }

    public AbstractEntryBuilder withExtensionElements(List<String> extensionElements) {
        this.extensionElements = to_StringArrayList(extensionElements);
        return this;
    }

    public AbstractEntryBuilder withExtensionElements(String... extensionElements) {
        return withExtensionElements(Arrays.asList(extensionElements));
    }

    public AbstractEntryBuilder withProperties(Bundle properties) {
        this.properties = to_LinkedHashMap(properties);
        return this;
    }

    public void setValue(String key, Object value) {
        if ("id".equals(key)) {
            id = to_string(value);
            put_string(key, id);
        } else if ("title".equals(key)) {
            title = to_text(value);
            put_parcelable(key, title);
        } else if ("summary".equals(key)) {
            summary = to_text(value);
            put_parcelable(key, summary);
        } else if ("published".equals(key)) {
            published = to_datetime(value);
            put_parcelable(key, published);
        } else if ("updated".equals(key)) {
            updated = to_datetime(value);
            put_parcelable(key, updated);
        } else if ("category".equals(key)) {
            categories = to_category_list(value);
            put_parcelable_list(key, categories);
        } else if ("categories".equals(key)) {
            categories = to_category_list(value);
            put_parcelable_list(key, categories);
        } else if ("link".equals(key)) {
            links = to_link_list(value);
            put_parcelable_list(key, links);
        } else if ("links".equals(key)) {
            links = to_link_list(value);
            put_parcelable_list(key, links);
        } else if ("author".equals(key)) {
            authors = to_person_list(value);
            put_parcelable_list(key, authors);
        } else if ("authors".equals(key)) {
            authors = to_person_list(value);
            put_parcelable_list(key, authors);
        } else if ("contributor".equals(key)) {
            contributors = to_person_list(value);
            put_parcelable(key, title);
        } else if ("contributors".equals(key)) {
            contributors = to_person_list(value);
            put_parcelable(key, title);
        } else if ("rights".equals(key)) {
            rights = to_text(value);
            put_parcelable(key, rights);
        } else if ("source".equals(key)) {
            source = to_source(value);
            put_parcelable(key, source);
        } else if ("content".equals(key)) {
            content = to_content(value);
            put_parcelable(key, title);
        } else if ("contents".equals(key)) {
            content = to_content(value);
            put_parcelable(key, title);
        } else if ("extensionElements".equals(key)) {
            extensionElements = to_string_list(value);
            put_string_list(key, extensionElements);
        } else {
            put_string(key, to_string(value)); // XXX
        }
    }

    protected final void put_parcelable_list(String key, ArrayList<? extends Parcelable> value) {
        if (contents == null) {
            contents = new Bundle();
        }
        contents.putParcelableArrayList(key, value);
    }

    protected final void put_parcelable(String key, Parcelable value) {
        if (contents == null) {
            contents = new Bundle();
        }
        contents.putParcelable(key, value);        
    }

    protected final void put_string(String key, String value) {
        if (contents == null) {
            contents = new Bundle();
        }
        contents.putString(key, value);
    }

    protected final void put_string_list(String key, ArrayList<String> value) {
        if (contents == null) {
            contents = new Bundle();
        }
        contents.putStringArrayList(key, value);
    }
}
