package org.goldenport.android.platforms;

import java.io.IOException;

import org.goldenport.android.GPlatform;

/**
 * @since   May.  2, 2011
 * @version May.  3, 2011
 * @author  ASAMI, Tomoharu
 */
public class Platform4 extends GPlatform {
    @Override
    public int getJpegOrientation(String filename) throws IOException {
        return ORIENTATION_NORMAL;
    }
}
