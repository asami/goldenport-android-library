package org.goldenport.android;

import org.goldenport.android.platforms.Platform4;
import org.goldenport.android.platforms.Platform5;

import android.os.Build;
import android.view.View;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @since   Apr. 29, 2011
 * @version Aug. 26, 2011
 * @author  ASAMI, Tomoharu
 */
public class GFactory {
    protected final Injector injector;
    private GPlatform _platform = null;

    public GFactory(Module... module) {
        injector = Guice.createInjector(module);
    }

    public <T extends GModel<?, ?>> T createModel() {
        T m = (T)injector.getInstance(GModel.class);
        return m;
    }

    @SuppressWarnings("unchecked")
    public <T extends GController<?, ?, ?, ?>> T createController() {
        return _init((T)injector.getInstance(GController.class));
    }

    public <T extends GController<?, ?, ?, ?>> T createController(Class<T> klass) {
        if (klass == null) {
            return createController();
        } else {
            return _init((T)injector.getInstance(klass));
        }
    }

    private <T extends GController<?, ?, ?, ?>> T _init(T c) {
        c.inject(injector);
        return c;
    }

    public <T extends View> T createView(Class<T> klass) {
//        try {
//            return _injector.getInstance(klass);
//        } catch (RuntimeException e) {
//            return null;
//        }
        return null;
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
