package org.goldenport.android;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @since   Apr. 29, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public class GFactory {
	private GModule _module;
	private Injector _injector;

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
