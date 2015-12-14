package hogent.group15.ui.controls.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.util.ListEntryAdapter;

/**
 * Created by Brent on 10/28/2015.
 */
public class EmptyListEntry implements ListEntry {

    private String text;
    private Drawable drawable;
    public EmptyListEntry(Context context, String text, int drawable) {
        this.text = text;
        this.drawable = ContextCompat.getDrawable(context, drawable);
    }

    @Override
    public void bindToView(ListEntryAdapter.EntryViewHolder holder) {
        View view = (View) holder.getView();
        ((TextView)view.findViewById(R.id.emptyListEntryText)).setText(text);
        ((ImageView)view.findViewById(R.id.emptyListEntryIcon)).setImageDrawable(drawable);
    }
}
