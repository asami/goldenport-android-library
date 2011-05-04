package org.goldenport.android;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @since   Apr. 29, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public class GModel<C extends GContext, E extends GErrorModel<C>> {
    @Inject
    protected Context context;

    @Inject
    public C gcontext;

    @Inject
    protected E gerrors;

    /*
     * Debug switches
     */
    public boolean isDebugMoreReadError() {
        return false;
    }
}
