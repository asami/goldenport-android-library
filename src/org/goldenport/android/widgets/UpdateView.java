package org.goldenport.android.widgets;

import org.goldenport.android.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * @since   May.  4, 2011
 * @version May.  7, 2011
 * @author  ASAMI, Tomoharu
 */
public class UpdateView extends LinearLayout {
    private final View _init;
    private final View _trigger;
    private final View _progress;

    public UpdateView(Context context) {
        this(context, R.layout.updateview);
    } 

    public UpdateView(Context context, int layout) {
        super(context);
        setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.WHITE);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(layout, null));
        _init = findViewById(R.id.g_update_init);
        _trigger = findViewById(R.id.g_update_trigger);
        _progress = findViewById(R.id.g_update_progress);
        initState();
    }

    public void initState() {
        _init.setVisibility(View.VISIBLE);
        _trigger.setVisibility(View.INVISIBLE);
        _progress.setVisibility(View.INVISIBLE);
    }

    public void trigerState() {
        _init.setVisibility(View.INVISIBLE);
        _trigger.setVisibility(View.VISIBLE);
        _progress.setVisibility(View.INVISIBLE);
    }

    public void progressState() {
        _init.setVisibility(View.INVISIBLE);
        _trigger.setVisibility(View.INVISIBLE);
        _progress.setVisibility(View.VISIBLE);
    }
}
