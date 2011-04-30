package org.goldenport.android.traits;

import org.goldenport.android.GActivityTrait;

import android.R;
import android.os.Bundle;
import android.view.View;

/**
 * @since   Apr. 28, 2011
 * @version Apr. 29, 2011
 * @author  ASAMI, Tomoharu
 */
public class ListViewTrait extends GActivityTrait {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        View list = findViewById(R.id.list);
        String ok = getString(R.string.ok);
    }
}
