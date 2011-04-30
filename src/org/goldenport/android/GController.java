package org.goldenport.android;

import android.content.Context;

import com.google.inject.Inject;

/**
 * @since   Apr. 29, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GController<C extends GContext, E extends GErrorModel, M extends GModel<C, E>, A extends GAgent<C, E, M>> {
    @Inject
    protected Context context;

    @Inject
    protected C gcontext;

    @Inject
    protected E gerrors;

    @Inject
    protected M gmodel;

    @Inject
    protected A gagent;
}
