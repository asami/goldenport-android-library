package org.goldenport.android.util;

import org.goldenport.android.GContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

/**
 * @since   May.  4, 2011
 * @version May.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class SpinnerDialogAsyncTask<Param, Result> extends GAsyncTask<Param, Message> {
    protected final Context context;
    protected final String message;
    private ProgressDialog _dialog;

    public SpinnerDialogAsyncTask(Context context, GContext gcontext, String message) {
        super(gcontext);
        this.context = context;
        this.message = message;
    }

    @Override
    protected final void onPreExecute() {
        on_Pre_Execute();
        _dialog = new ProgressDialog(context);
        _dialog.setMessage(message);
        _dialog.show();
    }

    protected void on_Pre_Execute() {
    }

    protected final void on_Post_Execute_Prologue(Result result) {
        _dialog.dismiss();
    }
}
