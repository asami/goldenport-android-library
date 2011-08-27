package org.goldenport.android.net;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.goldenport.android.util.HttpIOException;
import org.goldenport.android.util.InvocationTargetIOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

/**
 * @since   Jul. 27, 2011
 * @version Aug. 27, 2011
 * @author  ASAMI, Tomoharu
 */
public abstract class RestDriverBase {
    private String _module_name;
    private String _server;
    private String _password;
    private String _username;

    public RestDriverBase(String module) {
        _module_name = module;
    }

    public void open(String server) {
        _server = server;
    }

    public void close() {
    }

    protected void set_basic_auth(DefaultHttpClient dhc) {
        if (_username != null) {
            dhc.getCredentialsProvider().setCredentials(
                    AuthScope.ANY, new UsernamePasswordCredentials(_username, _password));
        }
    }

    private void sign_auth(HttpRequest request) throws IOException {
        sign_Auth(request);
    }

    protected void sign_Auth(HttpRequest request) throws IOException {
    }

    protected final String make_rest_url_json(String action) {
        return _server + "/" + action + ".json";
    }

    protected final String make_rest_url_json(String action, long id) {
        return _server + "/" + action + "/" + id + ".json";
    }

    protected final String make_rest_url_json(String action, String id) {
        return _server + "/" + action + "/" + id + ".json";
    }
    
    protected final String make_rest_url_plain(String action) { // XXX make_rest_url
        return _server + "/" + action;
    }

    protected final String make_rest_url_json(String action,
            Map<String, Object> params) {
        return make_rest_url_common_json(action, params, _server);
    }

    protected final String make_rest_url_common_json(String action,
            Map<String, Object> params, String server) {
        StringBuilder buf = new StringBuilder();
        buf.append(server);
        buf.append("/");
        buf.append(action);
        buf.append(".json");
        if (params.size() > 0) {
            boolean first = true;
            for (Entry<String, Object> entry: params.entrySet()) {
                if (first) {
                    buf.append("?");
                    first = false;
                } else {
                    buf.append("&");
                }
                buf.append(entry.getKey());
                buf.append("=");
                String value = _encode_value(entry.getValue());
                buf.append(value);
            }
        }
        return buf.toString();
    }

    private String _encode_value(Object value) {
        try {
            return URLEncoder.encode(_to_string(value), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }
    private String _to_string(Object value) {
        return value.toString();
    }

    protected final String make_rest_url_json(String action,
            Bundle params) {
        return make_rest_url_common_json(action, params, _server);
    }

    protected final String make_rest_url_common_json(String action,
            Bundle params, String server) {
        StringBuilder buf = new StringBuilder();
        buf.append(server);
        buf.append("/");
        buf.append(action);
        buf.append(".json");
        if (params.size() > 0) {
            boolean first = true;
            for (String key: params.keySet()) {
                if (first) {
                    buf.append("?");
                    first = false;
                } else {
                    buf.append("&");
                }
                buf.append(key);
                buf.append("=");
                String value = _encode_value(params.get(key));
                buf.append(value);
            }
        }
        return buf.toString();
    }

    protected final JSONObject invoke_get_json(String url) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "GET: " + url);
            HttpGet get = new HttpGet(url);
            sign_auth(get);
            HttpResponse httpResponse = dhc.execute(get);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("GET*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, get, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("GET-OK: %s <= %s", make_log_message(res), url));
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("GET*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("GET*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("GET*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final boolean is_ok_status(int statusCode) {
        switch (statusCode) {
        case HttpStatus.SC_OK: return true;
        case HttpStatus.SC_CREATED: return true;
        case HttpStatus.SC_ACCEPTED: return true;
        case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION: return true;
        case HttpStatus.SC_NO_CONTENT: return true;
        default:
            return false;
        }
    }

    protected final String make_log_message(String s) {
        int size = 60;
        if (s == null) return "[EMPTY]";
        if (s.length() <= size) return s;
        return s.substring(0, size);
    }

    protected final JSONObject invoke_post_json(String url) throws IOException {
        Map<String, Object> map = Collections.emptyMap();
        return invoke_post_json(url, map);
    }

    protected final JSONObject invoke_post_json(String url, Map<String, Object> params) throws IOException {
        try {
            String res = invoke_post_string(url, params);
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("POST*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        }
    }

    protected final JSONObject invoke_post_json(String url, Bundle bundle) throws IOException {
        try {
            String res = invoke_post_string(url, bundle);
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("POST*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        }
    }

    protected final JSONObject invoke_post_json_with_upload(String url,
            LinkedHashMap<String, Object> params, LinkedHashMap<String, String> files) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            HttpPost post = new HttpPost(url);
            sign_auth(post);
            MultipartEntity reqEntity = new MultipartEntity();
            for (Map.Entry<String, Object> param: params.entrySet()) {
                reqEntity.addPart(param.getKey(), new StringBody(param.getValue().toString()));
            }
            for (Map.Entry<String, String> file: files.entrySet()) {
                FileBody bin = new FileBody(new File(file.getValue()));
                reqEntity.addPart(file.getKey(), bin);
            }
            post.setEntity(reqEntity);
            HttpResponse httpResponse = dhc.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("POST(form)*STATUS: [%s:%s] %s <= %s",
                                           statusCode, phrase, res, url);
                throw new HttpIOException(msg, post, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("POST(form)-OK: %s <= %s", make_log_message(res.toString()), url));
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("POST(form)*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("POST(form)*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("POST(form)*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final JSONObject invoke_post_json_with_upload(String url,
            Bundle params, Bundle files) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            HttpPost post = new HttpPost(url);
            sign_auth(post);
            MultipartEntity reqEntity = new MultipartEntity();
            for (String key: params.keySet()) {
                reqEntity.addPart(key, new StringBody(params.getString(key)));
            }
            for (String key: files.keySet()) {
                FileBody bin = new FileBody(new File(files.getString(key)));
                reqEntity.addPart(key, bin);
            }
            post.setEntity(reqEntity);
            HttpResponse httpResponse = dhc.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("POST(form)*STATUS: [%s:%s] %s <= %s",
                                           statusCode, phrase, res, url);
                throw new HttpIOException(msg, post, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("POST(form)-OK: %s <= %s", make_log_message(res.toString()), url));
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("POST(form)*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("POST(form)*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("POST(form)*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final String invoke_post_string(String url,
            Map<String, Object> params) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "POST: " + url);
            HttpPost post = new HttpPost(url);
            sign_auth(post);
            if (params != null) {
                ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> param: params.entrySet()) {
                    nvps.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
                }
                UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
                post.setEntity(reqEntity);
            }
            HttpResponse httpResponse = dhc.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("POST*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, post, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("POST-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (IOException e) {
            Log.w(_module_name, String.format("POST*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("POST*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final String invoke_post_string(String url,
            Bundle params) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "POST: " + url);
            HttpPost post = new HttpPost(url);
            sign_auth(post);
            if (params != null) {
                ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (String key: params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.getString(key)));
                }
                UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
                post.setEntity(reqEntity);
            }
            HttpResponse httpResponse = dhc.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("POST*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, post, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("POST-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (IOException e) {
            Log.w(_module_name, String.format("POST*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("POST*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

/*
    protected final JSONObject invoke_post_for_auth(String url) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            Log.i(_module_name, "POST: " + url);
            HttpPost post = new HttpPost(url);
            HttpResponse httpResponse = dhc.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            JSONObject res = UHttpComponents.loadJsonFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("POST*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, post, httpResponse);
            }
            Log.i(_module_name, String.format("POST-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (JSONException e) {
            Log.w(_module_name, String.format("POST*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("POST*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("POST*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }
*/

    protected final JSONObject invoke_put_json(String url) throws IOException {
        Map<String, Object> map = Collections.emptyMap();
        return invoke_put_json(url, map);
    }

    protected final JSONObject invoke_put_json(String url, Map<String, Object> params) throws IOException {
        try {
            String res = invoke_put_string(url, params);
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("PUT*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        }
    }

    protected final JSONObject invoke_put_json(String url, Bundle bundle) throws IOException {
        try {
            String res = invoke_put_string(url, bundle);
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("PUT*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        }
    }

    protected final JSONObject invoke_put_json_with_upload(String url,
            LinkedHashMap<String, Object> params, LinkedHashMap<String, String> files) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            HttpPut put = new HttpPut(url);
            sign_auth(put);
            MultipartEntity reqEntity = new MultipartEntity();
            for (Map.Entry<String, Object> param: params.entrySet()) {
                reqEntity.addPart(param.getKey(), new StringBody(param.getValue().toString()));
            }
            for (Map.Entry<String, String> file: files.entrySet()) {
                FileBody bin = new FileBody(new File(file.getValue()));
                reqEntity.addPart(file.getKey(), bin);
            }
            put.setEntity(reqEntity);
            HttpResponse httpResponse = dhc.execute(put);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("PUT(form)*STATUS: [%s:%s] %s <= %s",
                                           statusCode, phrase, res, url);
                throw new HttpIOException(msg, put, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("PUT(form)-OK: %s <= %s", make_log_message(res.toString()), url));
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("PUT(form)*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("PUT(form)*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("PUT(form)*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final JSONObject invoke_put_json_with_upload(String url,
            Bundle params, Bundle files) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            HttpPut put = new HttpPut(url);
            sign_auth(put);
            MultipartEntity reqEntity = new MultipartEntity();
            for (String key: params.keySet()) {
                reqEntity.addPart(key, new StringBody(params.getString(key)));
            }
            for (String key: files.keySet()) {
                FileBody bin = new FileBody(new File(files.getString(key)));
                reqEntity.addPart(key, bin);
            }
            put.setEntity(reqEntity);
            HttpResponse httpResponse = dhc.execute(put);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("PUT(form)*STATUS: [%s:%s] %s <= %s",
                                           statusCode, phrase, res, url);
                throw new HttpIOException(msg, put, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("PUT(form)-OK: %s <= %s", make_log_message(res.toString()), url));
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("PUT(form)*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            Log.w(_module_name, String.format("PUT(form)*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("PUT(form)*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final String invoke_put_string(String url,
            Map<String, Object> params) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "PUT: " + url);
            HttpPut put = new HttpPut(url);
            sign_auth(put);
            if (params != null) {
                ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, Object> param: params.entrySet()) {
                    nvps.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
                }
                UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
                put.setEntity(reqEntity);
            }
            HttpResponse httpResponse = dhc.execute(put);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("PUT*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, put, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("PUT-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (IOException e) {
            Log.w(_module_name, String.format("PUT*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("PUT*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final String invoke_put_string(String url,
            Bundle params) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "PUT: " + url);
            HttpPut put = new HttpPut(url);
            sign_auth(put);
            if (params != null) {
                ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (String key: params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.getString(key)));
                }
                UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
                put.setEntity(reqEntity);
            }
            HttpResponse httpResponse = dhc.execute(put);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("PUT*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, put, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("PUT-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (IOException e) {
            Log.w(_module_name, String.format("PUT*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("PUT*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }
    
    // delete
    protected final JSONObject invoke_delete_json(String url) throws IOException {
        try {
            String res = invoke_delete_string(url);
            return new JSONObject(res);
        } catch (JSONException e) {
            Log.w(_module_name, String.format("DELETE*JSON: %s <= %s", e.getMessage(), url), e);
            throw new IOException(e.getMessage());
        }
    }

    protected final String invoke_delete_string(String url) throws IOException {
        DefaultHttpClient dhc = new DefaultHttpClient();
        try {
            set_basic_auth(dhc);
            Log.i(_module_name, "DELETE: " + url);
            HttpDelete delete = new HttpDelete(url);
            sign_auth(delete);
            HttpResponse httpResponse = dhc.execute(delete);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String res = UHttpComponents.loadStringFromHttpResponse(httpResponse);
            if (!is_ok_status(statusCode)) {
                String phrase = httpResponse.getStatusLine().getReasonPhrase();
                String msg = String.format("DELETE*STATUS: [%s:%s] %s <= %s",
                        statusCode, phrase, res, url);
                throw new HttpIOException(msg, delete, statusCode, phrase, res);
            }
            Log.i(_module_name, String.format("DELETE-OK: %s <= %s", make_log_message(res.toString()), url));
            return res;
        } catch (IOException e) {
            Log.w(_module_name, String.format("DELETE*IO: %s <= %s", e.getMessage(), url), e);
            throw e;
        } catch (Throwable e) {
            Log.w(_module_name, String.format("DELETE*Throwable: %s <= %s", e.getMessage(), url), e);
            throw new InvocationTargetIOException(e);
        } finally {
            dhc.getConnectionManager().shutdown();
        }
    }

    protected final JSONArray get_array(JSONObject json, String key) throws JSONException {
        if (json == null || json.isNull(key)) {
            return new JSONArray();
        } else {
            return json.getJSONArray(key);
        }
    }

    protected String get_content_key() {
        return "content";
    }
}
