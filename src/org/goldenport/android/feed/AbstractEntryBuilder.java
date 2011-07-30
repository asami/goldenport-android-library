package org.goldenport.android.feed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

/*
 * @since   Jun. 12, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class AbstractEntryBuilder {
    protected final ArrayList<GAPerson> to_person_list(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final ArrayList<GACategory> to_category_list(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final GADateTime to_datetime(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final ArrayList<String> to_string_list(Object value) {
        // XXXX Relaxerのコードを持ってくる
        return null;
    }

    protected final GAText to_text(Object value) {
        if (value instanceof GAText) {
            return (GAText)value;
        } else {
            return new GAText(to_string(value));
        }
    }

    protected final GAContent to_content(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final GAFeed to_source(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final ArrayList<GALink> to_link_list(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    protected final String to_string(Object value) {
        // XXX Relaxerのコードを持ってくる
        return value.toString();
    }

    protected final Uri to_uri(Object value) {
        if (value instanceof Uri) {
            return (Uri)value;
        } else {
            return Uri.parse(to_string(value));
        }
    }

    protected final ArrayList<String> to_StringArrayList(List<String> list) {
        if (list instanceof ArrayList<?>) {
            return (ArrayList<String>)list;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected final ArrayList<String> to_StringArrayList(String[] strings) {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(strings));
        return list;
    }

    protected final <T extends Parcelable> ArrayList<T> to_ParcelableArrayList(List<T> list) {
        if (list instanceof ArrayList<?>) {
            return (ArrayList<T>)list;
        } else {
            return new ArrayList<T>(list);
        }
    }

    protected final LinkedHashMap<String, Object> to_LinkedHashMap(
            Map<String, Object> map) {
        if (map instanceof LinkedHashMap<?, ?>) {
            return (LinkedHashMap<String, Object>)map;
        } else {
            return new LinkedHashMap<String, Object>(map);
        }
    }

    protected final LinkedHashMap<String, Object> to_LinkedHashMap(Bundle bundle) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        for (String key: bundle.keySet()) {
            map.put(key, bundle.get(key));
        }
        return map;
    }
}
