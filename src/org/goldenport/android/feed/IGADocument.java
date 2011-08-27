package org.goldenport.android.feed;

import android.os.Parcelable;

/*
 * @since   Aug. 27, 2011
 * @version Aug. 27, 2011
 * @author  ASAMI, Tomoharu
 */
public interface IGADocument extends Parcelable {
    String get_entry_id(String id);
    String get_entry_title(String title);
    String get_entry_summary(String summary);
}
