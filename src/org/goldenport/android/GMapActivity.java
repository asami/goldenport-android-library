package org.goldenport.android;

import com.google.android.maps.MapActivity;

/**
 * @since   Apr. 28, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GMapActivity extends MapActivity implements IGActivity {
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
