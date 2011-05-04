package org.goldenport.android.models;

/**
 * @since   May.  4, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class Record<T> {
    public final T data;
    public final int totalLength;
    public final boolean isComplete;

    public Record(T data, int totalLength, boolean isComplete) {
        this.data = data;
        this.totalLength = totalLength;
        this.isComplete = isComplete;
    }
}
