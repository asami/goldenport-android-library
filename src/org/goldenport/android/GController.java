package org.goldenport.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @since   Apr. 29, 2011
 * @version Sep. 10, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GController<C extends GContext, E extends GErrorModel<C>, M extends GModel<C, E>, A extends GAgent<C, E, M>>
        extends GActivityTrait {
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

    @SuppressWarnings("unchecked")
    void inject(Injector injector) {
        if (context == null) {
            context = injector.getInstance(Context.class);
        }
        if (gcontext == null) {
            gcontext = (C)injector.getInstance(GContext.class);
        }
        if (gerrors == null) {
            gerrors = (E)injector.getInstance(GErrorModel.class);
        }
        if (gmodel == null) {
            gmodel = (M)injector.getInstance(GModel.class);
        }
        if (gagent == null) {
            gagent = (A)injector.getInstance(GAgent.class);
        }
    }

    void applicationFailure(Activity activity, Throwable e) {
        gcontext.logError(activity.getClass().getName() + ":applicationFailure", e);
        GIOException ee = gerrors.applicationFailure(e);
        Intent intent = new Intent(INTENT_ACTION_EXCEPTION);
        intent.putExtra(INTENT_EXTRA_EXCEPTION, e.getMessage()); // XXX
        activity.startActivityForResult(intent, START_EXCEPTION);
    }
}
