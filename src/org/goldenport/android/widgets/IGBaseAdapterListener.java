package org.goldenport.android.widgets;

/**
 * @since   May.  4, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public interface IGBaseAdapterListener {
    void startLoading(GBaseAdapter<?> adapter, int length);
    void finishLoading(GBaseAdapter<?> adapter);
    void finishLoadingException(GBaseAdapter<?> adapter, Throwable e, Runnable retry);
    void refreshed(GBaseAdapter<?> adapter);
    void updating(GBaseAdapter<?> adapter);
}
