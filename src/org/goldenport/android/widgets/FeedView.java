package org.goldenport.android.widgets;

import org.goldenport.android.GView;
import org.goldenport.android.feed.GAFeed;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @since   Jun. 12, 2011
 * @version Aug. 13, 2011
 * @author  ASAMI, Tomoharu
 */
public class FeedView extends GView {
    public FeedView(Context context) {
        this(context, null);
    }

    public FeedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setEntry(GAFeed feed) {
        throw new UnsupportedOperationException();
    }
}
