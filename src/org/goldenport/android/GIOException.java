package org.goldenport.android;

import java.io.IOException;

/**
 * @since   May.  3, 2011
 * @version May.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class GIOException extends IOException {
    private static final long serialVersionUID = 1L;
    public static final int KIND_SILENT = 1;
    public static final int KIND_NOTIFY = 2;
    public static final int KIND_RETRY = 3;
    public static final int KIND_TERMINATE = 4;
    public final String title;
    public final String message;
    public final String code;
    public Runnable retry = null;
    public Runnable cancel = null;
    public int kind = KIND_NOTIFY;
    public boolean forceMessage = false;

    public GIOException(String title, String message, String code) {
        this(title, message, code, null);        
    }

    public GIOException(String title, String message, String code, Throwable e) {
        super(title + " : " + message);
        if (e != null) {
            initCause(e);
        }
        this.title = title;
        this.message = message;
        this.code = code;
    }

    public void setKindSilent() {
        kind = KIND_SILENT;
    }

    public void setKindNotify() {
        kind = KIND_NOTIFY;
    }

    public void setKindRetry() {
        kind = KIND_RETRY;
    }

    public void setKindTerminate() {
        kind = KIND_TERMINATE;
    }

    public GIOException withNotify() {
        kind = KIND_NOTIFY;
        return this;
    }

    public GIOException withSilent() {
        kind = KIND_SILENT;
        return this;
    }

    public GIOException withRetry() {
        kind = KIND_RETRY;
        return this;
    }

    public GIOException withRetry(Runnable retry) {
        kind = KIND_RETRY;
        this.retry = retry;
        return this;
    }

    public GIOException withTerminat() {
        kind = KIND_TERMINATE;
        return this;
    }

    public GIOException withForceMessage() {
        forceMessage = true;
        return this;
    }

    public void withCancel(Runnable cancel) {
        this.cancel = cancel;        
    }
}
