package org.goldenport.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since   Apr. 29, 2011
 * @version May.  7, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GContext {
    protected final Logger logger = LoggerFactory.getLogger(getLogTag());

    private GContext _parent = null;

    public String getLogTag() {
        return context_Name();
    }

    protected String context_Name() {
        if (_parent == null) return "goldenport"; // XXX
        else return _parent.context_Name();
    }

    public void setParent(GContext parent) {
        _parent = parent;
    }

    public void logError(String message) {
        if (_parent != null) _parent.logError(message);
        else logger.error(message);
    }

    public void logError(String message, Throwable e) {
        if (_parent != null) _parent.logError(message, e);
        else logger.error(message, e);
    }

    public void logWarn(String message) {
        if (_parent != null) _parent.logWarn(message);
        logger.warn(message);
    }

    public void logWarn(String message, Throwable e) {
        if (_parent != null) _parent.logWarn(message, e);
        else logger.warn(message, e);        
    }

    public void logInfo(String message) {
        if (_parent != null) _parent.logInfo(message);
        else logger.info(message);
    }

    public void logDebug(String message) {
        if (_parent != null) _parent.logDebug(message);
        else logger.debug(message);
    }

    public void logDebug(String message, Throwable e) {
        if (_parent != null) _parent.logDebug(message, e);
        else logger.debug(message, e);
    }

    public void logVerbose(String message) {
        if (_parent != null) _parent.logVerbose(message);
        else logger.trace(message);
    }
}
