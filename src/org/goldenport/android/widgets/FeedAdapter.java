package org.goldenport.android.widgets;

import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.feed.GAFeed;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/*
 * @since   Jun.  7, 2011
 * @version Jun. 12, 2011
 * @author  ASAMI, Tomoharu
 */
public class FeedAdapter extends BaseAdapter {
    private Context _context;
    private GAFeed _feed;

    public FeedAdapter(Context context, GAFeed feed) {
        _context = context;
        _feed = feed;
    }

    public int getCount() {
        return _feed.entries.size();
    }

    public GAEntry getItem(int position) {
        return _feed.entries.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        EntryView v;
        if (convertView instanceof EntryView) {
            v = (EntryView)convertView;
        } else {
            v = new EntryView(_context);
        }
        v.setEntry(getItem(position));
        return v;
    }
}
