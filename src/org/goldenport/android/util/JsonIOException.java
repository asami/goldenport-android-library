package org.goldenport.android.util;

import org.json.JSONException;

/**
 * @since   May.  3, 2011
 * @version May.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class JsonIOException extends InvocationTargetIOException {
    private static final long serialVersionUID = 1L;

    public JsonIOException(JSONException e) {
        super(e);
    }
}
