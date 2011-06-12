package org.goldenport.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.inject.Singleton;

/**
 * @since   Jun.  7, 2011
 * @version Jun.  7, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GView extends View {
    public GView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
