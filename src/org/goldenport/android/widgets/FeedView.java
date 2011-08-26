package org.goldenport.android.widgets;

import org.goldenport.android.GView;
import org.goldenport.android.R;
import org.goldenport.android.feed.GAFeed;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @since   Jun. 12, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
public class FeedView extends GView {
    public FeedView(Context context) {
        this(context, null);
    }

    public FeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int get_Layout_Id() {
        return R.layout.entryview; // XXX
    }

    public void setEntry(GAFeed feed) {
/*
        feed.categories;
        feed.contributors;
        feed.entries;
        feed.generator;
        feed.icon;
        feed.id;
        feed.links;
        feed.logo;
        feed.rights;
        feed.subtitle
        feed.title;
        feed.updated;
*/
        throw new UnsupportedOperationException();
    }
}
