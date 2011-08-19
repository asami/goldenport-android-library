package org.goldenport.android;

import android.content.Context;
import android.util.Log;

/**
 * @since   Apr. 29, 2011
 * @version Aug. 14, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class GContext {
//    protected final Logger logger = LoggerFactory.getLogger(getLogTag());
    private Context _context;
    private GContext _parent = null;

    public String getLogTag() {
        return context_Name();
    }

    protected String context_Name() {
        if (_parent == null) return getString(R.string.g_config_context_name);
        else return _parent.context_Name();
    }

    public void setParent(GContext parent) {
        _parent = parent;
    }

    public void setContext(Context context) {
        _context = context;
    }

    public Context getContext() {
        return _context;
    }

    public String getString(int rscid) {
        return _context.getResources().getString(rscid);
    }

    public String getConfigServerUrl() {
        return getString(R.string.g_config_server_url);
    }

    public void logError(String message) {
        if (_parent != null) _parent.logError(message);
        else Log.e(getLogTag(), message);
    }

    public void logError(String message, Throwable e) {
        if (_parent != null) _parent.logError(message, e);
        else Log.e(getLogTag(), message, e);
    }

    public void logWarn(String message) {
        if (_parent != null) _parent.logWarn(message);
        else Log.w(getLogTag(), message);
    }

    public void logWarn(String message, Throwable e) {
        if (_parent != null) _parent.logWarn(message, e);
        else Log.w(getLogTag(), message, e);        
    }

    public void logInfo(String message) {
        if (_parent != null) _parent.logInfo(message);
        else Log.i(getLogTag(), message);
    }

    public void logDebug(String message) {
        if (_parent != null) _parent.logDebug(message);
        else Log.d(getLogTag(), message);
    }

    public void logDebug(String message, Throwable e) {
        if (_parent != null) _parent.logDebug(message, e);
        else Log.d(getLogTag(), message, e);
    }

    public void logVerbose(String message) {
        if (_parent != null) _parent.logVerbose(message);
        else Log.v(getLogTag(), message);
    }

/*
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
*/
    public int sp2pxi(int sp) {
        throw new UnsupportedOperationException();
    }
}
