package org.goldenport.android.widgets;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @since   Jun. 13, 2011
 * @version Aug. 28, 2011
 * @author  ASAMI, Tomoharu
 */
public class BitmapView extends FrameLayout {
    public BitmapView(Context context) {
        this(context, null);
    }

    public BitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImage(Uri format_Link_Image) {
        throw new UnsupportedOperationException();        
    }
}
