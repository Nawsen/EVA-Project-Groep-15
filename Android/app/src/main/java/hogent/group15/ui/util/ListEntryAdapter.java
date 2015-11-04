package hogent.group15.ui.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.ui.controls.ListEntry;

/**
 * Created by Brent on 10/28/2015.
 */
public class ListEntryAdapter extends BaseAdapter {

    private List<? extends ListEntry> entries = new ArrayList<>();
    private LayoutInflater inflater;

    public ListEntryAdapter(Activity owner, List<? extends ListEntry> entries) {
        this.entries = entries;
        inflater = owner.getLayoutInflater();

    }

    @Override
    public int getCount() {
        return entries.size() <= 0 ? 1 : entries.size();
    }

    @Override
    public Object getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return entries.get(position).retrieveView(inflater, parent);
    }
}
