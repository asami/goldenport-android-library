package org.goldenport.android;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @since   Apr. 30, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GErrorModel<C extends GContext> {
    @Inject
    private Context context;

    @Inject
    private C gcontext;
}
