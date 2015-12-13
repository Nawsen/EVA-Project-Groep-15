package hogent.group15.ui.controls;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hogent.group15.ui.util.ListEntryAdapter;

/**
 * Created by Brent on 10/28/2015.
 */
public interface ListEntry<E> {
    void bindToView(ListEntryAdapter.EntryViewHolder<E> holder);
}
