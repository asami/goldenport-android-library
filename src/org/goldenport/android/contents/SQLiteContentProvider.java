package org.goldenport.android.contents;

import org.goldenport.android.GContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/*
 * @since   Jun.  2, 2011
 * @version Jun.  2, 2011
 * @author  ASAMI, Tomoharu
 */
public class SQLiteContentProvider extends GContentProvider {
    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }

    @Override
    public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
            String arg4) {
        return null;
    }

    @Override
    public Uri insert(Uri arg0, ContentValues arg1) {
        return null;
    }

    @Override
    public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
        return 0;
    }


    @Override
    public int delete(Uri arg0, String arg1, String[] arg2) {
        return 0;
    }
}
