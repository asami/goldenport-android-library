package org.goldenport.android.util;

import java.io.IOException;


/**
 * @since   May.  3, 2011
 * @version May.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class InvocationTargetIOException extends IOException {
    private static final long serialVersionUID = 1L;

    public InvocationTargetIOException(Throwable e) {
        initCause(e);
    }
}
