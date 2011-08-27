package org.goldenport.android.widgets;

import java.util.List;

import org.goldenport.android.GView;
import org.goldenport.android.R;
import org.goldenport.android.feed.GACategory;
import org.goldenport.android.feed.GAContent;
import org.goldenport.android.feed.GADateTime;
import org.goldenport.android.feed.GAEntry;
import org.goldenport.android.feed.GAText;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @since   Jun.  7, 2011
 * @version Aug. 27, 2011
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
    protected BitmapView link_image;
    protected TextView link_geo;

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
        link_image = (BitmapView)findViewById(R.id.g_entry_link_image);
        link_geo = (TextView)findViewById(R.id.g_entry_link_geo);
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
            }
        }
        if (title != null) {
            if (entry.title != null) {
                title.setText(format_Title(entry.title));
            }
        }
        if (summary != null) {
            if (entry.summary != null) {
                summary.setText(format_Summary(entry.summary));
            }
        }
        if (published != null) {
            if (entry.published != null) {
                published.setText(format_Published(entry.published));
            }
        }
        if (updated != null) {
            if (entry.updated != null) {
                updated.setText(format_Updated(entry.updated));
            }
        }
        if (categories != null) {
            if (entry.categories != null) {
                updated.setText(format_Categories(entry.categories));
            }
        }
        if (links != null) {
            // TODO
        }
        if (authors != null) {
            // TODO
            
        }
        if (contributors != null) {
            // TODO
            
        }
        if (rights != null) {
            // TODO
            
        }
        if (source != null) {
            // TODO
            
        }
        if (content_text != null) {
            if (entry.content != null) {
                updated.setText(format_Content_Text(entry.content));
            }
        }
        if (link_image != null) {
            Uri image = entry.getLinkImage();
            if (image != null) {
                updated.setText(format_Link_Image(image));
            }
        }
        if (link_geo != null) {
            // TODO
            
        }
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

    protected CharSequence format_Link_Image(Uri image) {
        return image.toString();
    }
}
