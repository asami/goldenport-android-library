package org.goldenport.android.util;

import java.util.*;

/**
 * LooseList
 *
 * @since   Jan. 21, 2002
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class LooseArrayList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 843380636432331474L;

    public E put(int index, E element) {
        int size = size();
        if (size <= index) {
            for (int i = size; i <= index; i++) {
                add(null);
            }
        }
        return (set(index, element));
    }
}
