package org.goldenport.android.widgets;

import java.util.List;

import org.goldenport.android.GView;
import org.goldenport.android.R;
import org.goldenport.android.feed.GACategory;
import org.goldenport.android.feed.GAContent;
import org.goldenport.android.feed.GADateTime;
import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.feed.GAFeed;
import org.goldenport.android.feed.GALink;
import org.goldenport.android.feed.GAPerson;
import org.goldenport.android.feed.GAText;
import org.goldenport.android.feed.TextContent;
import org.goldenport.android.feed.TextType;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * @since   Jun.  7, 2011
 * @version Sep.  4, 2011
 * @author  ASAMI, Tomoharu
 */
public class EntryView extends GView {
    protected GAEntry entry;
    protected TextView id; 
    protected TextView title;
    protected TextView summary;
    protected DateTimeView published;
    protected DateTimeView updated;
    protected TextView categories;
    protected TextView links;
    protected TextView authors;
    protected TextView contributors;
    protected TextView rights;
    protected TextView source;
    protected TextView content_text;
    protected WebView content_html;
    protected BitmapView link_image;
    protected TextView ext_geo;

    public EntryView(Context context) {
        this(context, null);
    }

    public EntryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        id = (TextView)findViewById(R.id.g_entry_id);
        title = (TextView)findViewById(R.id.g_entry_title);
        summary = (TextView)findViewById(R.id.g_entry_summary);
        published = (DateTimeView)findViewById(R.id.g_entry_published);
        updated = (DateTimeView)findViewById(R.id.g_entry_updated);
        categories = (TextView)findViewById(R.id.g_entry_categories);
        links = (TextView)findViewById(R.id.g_entry_links);
        authors = (TextView)findViewById(R.id.g_entry_authors);
        contributors = (TextView)findViewById(R.id.g_entry_contributors);
        rights = (TextView)findViewById(R.id.g_entry_rights);
        source = (TextView)findViewById(R.id.g_entry_source);
        content_text = (TextView)findViewById(R.id.g_entry_content_text);
        content_html = (WebView)findViewById(R.id.g_entry_content_html);
        link_image = (BitmapView)findViewById(R.id.g_entry_link_image);
        ext_geo = (TextView)findViewById(R.id.g_entry_link_geo);
    }

    @Override
    protected int get_Layout_Id() {
        return R.layout.entryview;
    }

    public void setEntry(GAEntry entry) {
        this.entry = entry;
        if (id != null) {
            if (entry.id != null) {
                id.setText(format_Id(entry.id));
            } else {
                id.setVisibility(View.GONE);
            }
        }
        if (title != null) {
            if (entry.title != null) {
                title.setText(format_Title(entry.title));
            } else {
                title.setVisibility(View.GONE);
            }
        }
        if (summary != null) {
            if (entry.summary != null) {
                summary.setText(format_Summary(entry.summary));
            } else {
                summary.setVisibility(View.GONE);
            }
        }
        if (published != null) {
            if (entry.published != null) {
                published.setText(format_Published(entry.published));
            } else {
                published.setVisibility(View.GONE);
            }
        }
        if (updated != null) {
            if (entry.updated != null) {
                updated.setText(format_Updated(entry.updated));
            } else {
                updated.setVisibility(View.GONE);
            }
        }
        if (categories != null) {
            if (entry.categories != null) {
                categories.setText(format_Categories(entry.categories));
            } else {
                categories.setVisibility(View.GONE);
            }
        }
        if (links != null) {
            if (entry.links != null) {
                links.setText(format_Links(entry.links));
            } else {
                links.setVisibility(View.GONE);
            }
        }
        if (authors != null) {
            if (entry.authors != null) {
                authors.setText(format_Authors(entry.authors));
            } else {
                authors.setVisibility(View.GONE);
            }
        }
        if (contributors != null) {
            if (entry.contributors != null) {
                contributors.setText(format_Contributors(entry.contributors));
            } else {
                contributors.setVisibility(View.GONE);
            }
        }
        if (rights != null) {
            if (entry.rights != null) {
                rights.setText(format_Rights(entry.rights));
            } else {
                rights.setVisibility(View.GONE);
            }
        }
        if (source != null) {
            if (entry.source != null) {
                source.setText(format_Source(entry.source));
            } else {
                source.setVisibility(View.GONE);
            }
        }
        if (content_text != null && content_html != null) {
            if (entry.content != null) {
                if (_is_html(entry.content)) {
                    _set_html(entry);
                } else {
                    content_text.setText(format_Content_Text(entry.content));
                }
            } else {
                content_text.setVisibility(View.GONE);
                content_html.setVisibility(View.GONE);
            }
        } else if (content_text != null && content_html == null) {
            if (entry.content != null) {
                content_text.setText(format_Content_Text(entry.content));
            } else {
                content_text.setVisibility(View.GONE);
            }
        } else if (content_text == null && content_html != null) { 
            if (entry.content != null) {
                _set_html(entry);
            } else {
                content_html.setVisibility(View.GONE);
            }
        }
        if (link_image != null) {
            Uri image = entry.getLinkImage();
            if (image != null) {
                link_image.setImage(format_Link_Image(image));
            } else {
                link_image.setVisibility(View.GONE);
            }
        }
        if (ext_geo != null) {
            Location loc = entry.getExtGeo();
            if (loc != null) {
                ext_geo.setText(format_Link_Geo(loc));
            } else {
                ext_geo.setVisibility(View.GONE);
            }
        }
    }

    private boolean _is_html(GAContent content) {
        if (!(content instanceof TextContent)) {
            return false;
        }
        TextContent tc = (TextContent)content;
        return tc.type == TextType.html || tc.type == TextType.xhtml;
    }

    private void _set_html(GAEntry entry) {
        content_html.loadData(format_Content_Html(entry.content), "text/html", "utf-8");
    }

    protected CharSequence format_Id(String id) {
        return id;
    }

    protected CharSequence format_Title(GAText title) {
        return title.text;
    }

    protected CharSequence format_Summary(GAText summary) {
        return summary.text;
    }

    protected CharSequence format_Published(GADateTime published) {
        return published.toString();
    }

    protected CharSequence format_Updated(GADateTime updated) {
        return updated.toString();
    }

    protected CharSequence format_Categories(List<GACategory> categories) {
        return categories.toString(); // XXX
    }

    protected CharSequence format_Content_Text(GAContent content) {
        return content.toString();
    }

    private String format_Content_Html(GAContent content) {
        return content.toString();
    }

    protected CharSequence format_Links(List<GALink> links) {
        return _to_string(links);
    }

    protected CharSequence format_Authors(List<GAPerson> authors) {
        return _to_string(authors);
    }

    protected CharSequence format_Contributors(List<GAPerson> contributors) {
        return _to_string(contributors);
    }

    protected CharSequence format_Rights(GAText rights) {
        return rights.text;
    }

    protected CharSequence format_Source(GAFeed source) {
        return source.toString();
    }

    protected Uri format_Link_Image(Uri uri) {
        return uri;
    }

    protected CharSequence format_Link_Geo(Location loc) {
        return loc.toString();
    }

    private CharSequence _to_string(List<?> list) {
        boolean first = true;
        StringBuilder buf = new StringBuilder();
        for (Object elem: list) {
            if (first) {
                first = false;
            } else {
                buf.append(", ");
            }
            buf.append(elem.toString());
        }
        return buf;
    }
}
