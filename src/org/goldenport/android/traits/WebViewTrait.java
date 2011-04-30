package org.goldenport.android.traits;

import org.goldenport.android.GActivityTrait;
import org.goldenport.android.R;

import android.os.Bundle;
import android.webkit.WebView;

import com.google.common.base.Function;

/**
 * @since   Apr. 30, 2011
 * @version Apr. 30, 2011
 * @author  ASAMI, Tomoharu
 */
public class WebViewTrait extends GActivityTrait {
  private WebView _wab_view;
  private Function<Void, Bundle> _bundle_callback;

  public WebViewTrait() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    _wab_view = (WebView)findViewById(R.id.g_WebView);
  }

  @Override
  public void onUpdate() {
    _wab_view.loadData(_make_html(), "text/html", "utf-8");
  }

  public GActivityTrait withBundleToTable(Function<Void, Bundle> function) {
    _bundle_callback = function;
    return this;
  }

  private String _make_html() {
    Bundle data = _bundle_callback.apply(null);
    return _make_html_table(data);
  }

  private String _make_html_table(Bundle data) {
        StringBuilder buf = new StringBuilder();
        buf.append("<table>");
        for (String key: data.keySet()) {
          Object value = data.get(key);
            buf.append("<tr>");
            buf.append("<td>");
            buf.append(key);
            buf.append("</td>");
            buf.append("<td>");
            buf.append(value.toString());
            buf.append("</td>");
            buf.append("</tr>");
        }
        buf.append("</table>");
        return buf.toString();
  }
}
