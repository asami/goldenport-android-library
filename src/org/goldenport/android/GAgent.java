package org.goldenport.android;

import android.content.Context;

import com.google.inject.Inject;

/**
 * @since   Apr. 30, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GAgent<C extends GContext, E extends GErrorModel<?>, M extends GModel<?, ?>> {
    @Inject
    protected Context context;

    @Inject
    protected C gcontext;

    @Inject
    protected E gerrors;

    @Inject
    protected M gmodel;
}
