package org.goldenport.android;

import com.google.android.maps.MapActivity;

/**
 * @since   Apr. 28, 2011
 * @version Apr. 29, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class AbstractMapActivity extends MapActivity implements IActivity {
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
