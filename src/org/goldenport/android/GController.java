package org.goldenport.android;

import android.app.Activity;
import android.content.Context;

import com.google.inject.Inject;

/**
 * @since   Apr. 29, 2011
 * @version May.  5, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GController<C extends GContext, E extends GErrorModel<C>, M extends GModel<C, E>, A extends GAgent<C, E, M>> {
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

    protected Activity activity;

    void init(GContext parentcontext, GErrorModel<?> parenterrormodel) {
        if (parentcontext != gcontext) {
            gcontext.setParent(parentcontext);
        }
        if (parenterrormodel != gerrors) {
            gerrors.setParent(parenterrormodel);
        }
    }

    void init(Activity activity) {
        this.activity = activity;  
    }
}
