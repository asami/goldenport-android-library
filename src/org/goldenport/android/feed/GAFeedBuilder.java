package org.goldenport.android.feed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

/*
 * @since   Jun.  7, 2011
 * @version Jul. 11, 2011
 * @author  ASAMI, Tomoharu
 */
public class GAFeedBuilder extends AbstractEntryBuilder {
    private String id;
    private GAText title;
    private GAText subtitle;
    private GADateTime updated;
    private ArrayList<GAEntry> entries;
    private ArrayList<GACategory> categories;
    private ArrayList<GALink> links;
    private ArrayList<GAPerson> contributors;
    private String generator;
    private GAText rights;
    private Uri icon;
    private Uri logo;
    private List<String> extensionElements;
    private LinkedHashMap<String, Object> properties;
    private GASchema schema;

    public GAFeedBuilder() {
    }

    public GAFeedBuilder(
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
        Map<String, Object> properties
    ) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.updated = updated;
        this.entries = to_ParcelableArrayList(entries);
        this.categories = to_ParcelableArrayList(categories);
        this.links = to_ParcelableArrayList(links);
        this.contributors = to_ParcelableArrayList(contributors);
        this.generator = generator;
        this.rights = rights;
        this.icon = icon;
        this.logo = logo;
        this.extensionElements = extensionElements;
        this.schema = schema;
        this.properties = to_LinkedHashMap(properties);
    }

    public GAFeed build() {
        return new GAFeed(
            id,
            title,
            subtitle,
            updated,
            entries,
            categories,
            links,
            contributors,
            generator,
            rights,
            icon,
            logo,
            extensionElements,
            schema,
            properties);
    }

    public GAFeedBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public GAFeedBuilder withTitle(String title) {
        this.title = new GAText(title);
        return this;
    }

    public GAFeedBuilder withSubtitle(String subtitle) {
        this.subtitle = new GAText(subtitle);
        return this;
    }

    public GAFeedBuilder withUpdated(String updated) {
        this.updated = new GADateTime(updated);
        return this;
    }

    public GAFeedBuilder withEntries(List<GAEntry> entries) {
        this.entries = to_ParcelableArrayList(entries);
        return this;
    }

    public GAFeedBuilder withCategories(List<GACategory> categories) {
        this.categories = to_ParcelableArrayList(categories);
        return this;
    }

    public GAFeedBuilder withCategories(GACategory... categories) {
        return withCategories(Arrays.asList(categories));
    }

    public GAFeedBuilder withCategoriesByString(List<String> categories) {
        this.categories = new ArrayList<GACategory>();
        for (String c: categories) {
            this.categories.add(new GACategory(c));
        }
        return this;
    }

    public GAFeedBuilder withCategories(String... categories) {
        return withCategoriesByString(Arrays.asList(categories));
    }

    public GAFeedBuilder withLinks(List<GALink> links) {
        this.links = to_ParcelableArrayList(links);
        return this;
    }

    public GAFeedBuilder withLinks(GALink... links) {
        return withLinks(Arrays.asList(links));
    }

    public GAFeedBuilder withContributors(List<GAPerson> contributors) {
        this.contributors = to_ParcelableArrayList(contributors);
        return this;
    }

    public GAFeedBuilder withContributors(GAPerson... contributors) {
        return withContributors(Arrays.asList(contributors));
    }

    public GAFeedBuilder withGenerator(String generator) {
        this.generator = generator;
        return this;
    }    

    public GAFeedBuilder withRights(String rights) {
        this.rights = new GAText(rights);
        return this;
    }    

    public GAFeedBuilder withIcon(String icon) {
        this.icon = Uri.parse(icon);
        return this;
    }

    public GAFeedBuilder withLogo(String logo) {
        this.logo = Uri.parse(logo);
        return this;
    }

    public GAFeedBuilder withExtensionElements(List<String> extensionElements) {
        this.extensionElements = extensionElements;
        return this;
    }

    public GAFeedBuilder withExtensionElements(String... extensionElements) {
        this.extensionElements = Arrays.asList(extensionElements);
        return this;
    }

    public GAFeedBuilder withSchema(GASchema schema) {
        this.schema = schema;
        return this;
    }

    public GAFeedBuilder withProperties(Map<String, Object> properties) {
        this.properties = to_LinkedHashMap(properties);
        return this;
    }

    public GAFeedBuilder withProperties(Bundle bundle) {
        this.properties = new LinkedHashMap<String, Object>();
        for (String key: bundle.keySet()) {
            properties.put(key, bundle.get(key));
        }
        return this;
    }

    public GAFeedBuilder withProperties(ContentValues values) {
        this.properties = new LinkedHashMap<String, Object>();
        // XXX
        return this;
    }

    public void setValue(String key, Object value) {
        if ("id".equals(key)) {
            id = to_string(value);
        } else if ("title".equals(key)) {
            title = to_text(value);
        } else if ("subtitle".equals(key)) {
            subtitle = to_text(value);
        } else if ("updated".equals(key)) {
            updated = to_datetime(value);
        } else if ("category".equals(key)) {
            categories = to_category_list(value);
        } else if ("categories".equals(key)) {
            categories = to_category_list(value);
        } else if ("link".equals(key)) {
            links = to_link_list(value);
        } else if ("links".equals(key)) {
            links = to_link_list(value);
        } else if ("contributor".equals(key)) {
            contributors = to_person_list(value);
        } else if ("contributors".equals(key)) {
            contributors = to_person_list(value);
        } else if ("generator".equals(key)) {
            generator = to_string(value);
        } else if ("rights".equals(key)) {
            rights = to_text(value);
        } else if ("icon".equals(key)) {
            icon = to_uri(value);
        } else if ("logo".equals(key)) {
            logo = to_uri(value);
        } else if ("extensionElements".equals(key)) {
            extensionElements = to_string_list(value);
        }
    }
}
