package org.goldenport.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.google.inject.Singleton;

/**
 * @since   Jun.  7, 2011
 * @version Sep.  3, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public abstract class GView extends FrameLayout implements GConstants {
    protected final LayoutInflater inflator;

    protected GView(Context context) {
        this(context, null);
    }

    public GView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int lid = get_Layout_Id();
        if (lid != INVALID_LAYOUT_ID) {
            addView(inflator.inflate(lid, null));
        }
    }

    protected int get_Layout_Id() {
        return INVALID_LAYOUT_ID;
    }
}
