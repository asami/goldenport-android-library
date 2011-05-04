package org.goldenport.android.platforms;

import java.io.IOException;

import android.media.ExifInterface;

/**
 * @since   May.  2, 2011
 * @version May.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class Platform5 extends Platform4 {
    @Override
    public int getJpegOrientation(String filename) throws IOException {
        ExifInterface exif = new ExifInterface(filename);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        switch (orientation) {
        case ExifInterface.ORIENTATION_UNDEFINED:
            return ORIENTATION_UNDEFINED; 
        case ExifInterface.ORIENTATION_NORMAL:
            return ORIENTATION_NORMAL;
        case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
            return ORIENTATION_FLIP_HORIZONTAL;
        case ExifInterface.ORIENTATION_ROTATE_180:
            return ORIENTATION_ROTATE_180;
        case ExifInterface.ORIENTATION_FLIP_VERTICAL:
            return ORIENTATION_FLIP_VERTICAL;
        case ExifInterface.ORIENTATION_TRANSPOSE:
            return ORIENTATION_TRANSPOSE;
        case ExifInterface.ORIENTATION_ROTATE_90:
            return ORIENTATION_ROTATE_90;
        case ExifInterface.ORIENTATION_TRANSVERSE:
            return ORIENTATION_TRANSVERSE;
        case ExifInterface.ORIENTATION_ROTATE_270:
            return ORIENTATION_ROTATE_270;
        default:
            return ORIENTATION_UNDEFINED;
        }
    }
}
