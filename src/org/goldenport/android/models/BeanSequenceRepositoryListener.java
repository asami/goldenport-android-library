package org.goldenport.android.models;

/**
 * @since   May.  4, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public interface BeanSequenceRepositoryListener {
    void onNoWait();
    void onWaiting();
    void onComplete();
    void onError(Throwable e);
}
