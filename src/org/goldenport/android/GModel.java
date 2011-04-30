package org.goldenport.android;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @since   Apr. 29, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GModel<C extends GContext, E extends GErrorModel> {
    @Inject
    protected Context context;

    @Inject
    protected C gcontext;

    @Inject
    protected E gerrors;
}
