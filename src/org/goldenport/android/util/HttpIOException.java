package org.goldenport.android.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @since   May.  3, 2011
 * @version Jul. 29, 2011
 * @author  ASAMI, Tomoharu
 */
public class HttpIOException extends IOException {
    private static final long serialVersionUID = 1L;
    private int _status_code;
    private String _text;
    private Runnable _retry;

    public HttpIOException(String msg, HttpGet get, HttpResponse httpResponse) {
        super(msg);
        try {
            _status_code = httpResponse.getStatusLine().getStatusCode();
            _text = AndroidIos.loadStringFromHttpResponse(httpResponse);
        } catch (IOException e) {
        }
    }

    public HttpIOException(String msg, HttpPost post, HttpResponse httpResponse) {
        super(msg);
        try {
            _status_code = httpResponse.getStatusLine().getStatusCode();
            _text = AndroidIos.loadStringFromHttpResponse(httpResponse);
        } catch (IOException e) {
        }
    }

    public HttpIOException(String msg, HttpDelete delete, HttpResponse httpResponse) {
        super(msg);
        try {
            _status_code = httpResponse.getStatusLine().getStatusCode();
            _text = AndroidIos.loadStringFromHttpResponse(httpResponse);
        } catch (IOException e) {
        }
    }

    public HttpIOException(String msg, HttpGet get, int statusCode,
            String phrase, String res) {
        super(msg);
        _status_code = statusCode;
        _text = res;
    }

    public HttpIOException(String msg, HttpPost post, int statusCode,
            String phrase, String res) {
        super(msg);
        _status_code = statusCode;
        _text = res;
    }

    public HttpIOException(String msg, HttpPut put, int statusCode,
            String phrase, String res) {
        _status_code = statusCode;
        _text = res;
    }

    public HttpIOException(String msg, HttpDelete delete, int statusCode,
            String phrase, String res) {
        super(msg);
        _status_code = statusCode;
        _text = res;
    }

    public HttpIOException(String msg, int statusCode, String res) {
        super(msg);
        _status_code = statusCode;
        _text = res;
    }

    public int getStatusCode() {
        return _status_code;
    }

    public String getResponseText() {
        return _text;
    }

    public JSONObject getResponseJson() throws JSONException {
        return new JSONObject(_text);
    }

    public void setRetry(Runnable retry) {
        _retry = retry;
    }

    public Runnable getRetry() {
        return _retry;
    }
}
