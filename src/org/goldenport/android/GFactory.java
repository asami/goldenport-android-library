package org.goldenport.android;

import org.goldenport.android.platforms.Platform4;
import org.goldenport.android.platforms.Platform5;

import android.os.Build;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @since   Apr. 29, 2011
 * @version May.  2, 2011
 * @author  ASAMI, Tomoharu
 */
public class GFactory {
    private final GModule _module;
    private final Injector _injector;
    private GPlatform _platform = null;

    public GFactory(GModule module) {
        _module = module;
        _injector = Guice.createInjector(module);
    }

    @SuppressWarnings("unchecked")
    public <T extends GController<?, ?, ?, ?>> T createController() {
        return (T)_injector.getInstance(GController.class);
    }

    public <T extends GController<?, ?, ?, ?>> T createController(Class<T> klass) {
        return (T)_injector.getInstance(klass);
    }

    public GPlatform getPlatform() {
        if (_platform == null) {
            if (Build.VERSION.SDK_INT <= 4) {
                _platform = new Platform4();
            } else {
                _platform = new Platform5();
            }
        }
        return _platform;
    }        

    /*
     * Singleton
     */
    private static GFactory __factory = null;

    public static void setFactory(GFactory factory) {
        __factory = factory;
    }

    public static GFactory getFactory() {
        return __factory;
    }
}
