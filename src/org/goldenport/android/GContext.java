package org.goldenport.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

/**
 * @since   Apr. 29, 2011
 * @version Apr. 29, 2011
 * @author  ASAMI, Tomoharu
 */
@Singleton
public abstract class GContext {
	protected final Logger logger = LoggerFactory.getLogger(context_Name());

	protected abstract String context_Name();

	public void logError(String message) {
		logger.error(message);
	}

	public void logWarn(String message) {
		logger.warn(message);
	}

	public void logInfo(String message) {
		logger.info(message);
	}

	public void logDebug(String message) {
		logger.debug(message);
	}

	public void logVerbose(String message) {
		logger.trace(message);
	}
}
