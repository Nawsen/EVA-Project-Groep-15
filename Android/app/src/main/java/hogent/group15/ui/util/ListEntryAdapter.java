package hogent.group15.ui.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hogent.group15.domain.Achievement;
import hogent.group15.domain.Challenge;
import hogent.group15.ui.R;
import hogent.group15.ui.controls.ListEntry;
import hogent.group15.ui.controls.list.AchievementListEntry;
import hogent.group15.ui.controls.list.ChallengeListEntry;
import hogent.group15.ui.controls.list.EmptyListEntry;

/**
 * Created by Brent on 10/28/2015.
 */
public class ListEntryAdapter extends RecyclerView.Adapter<ListEntryAdapter.EntryViewHolder> {

    private List<? extends ListEntry> entries = new ArrayList<>();
    private LayoutInflater inflater;

    public static final int ACHIEVEMENT_TYPE = 1;
    public static final int CHALLENGE_TYPE = 2;
    public static final int EMPTY_TYPE = 3;

    public ListEntryAdapter(Activity owner, List<? extends ListEntry> entries) {
        this.entries = entries;
        inflater = owner.getLayoutInflater();
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case ACHIEVEMENT_TYPE:
                return new EntryViewHolder(new AchievementListEntry(inflater.getContext()));
            case CHALLENGE_TYPE:
                return new EntryViewHolder(new ChallengeListEntry(inflater.getContext()));
            case EMPTY_TYPE:
                return new EntryViewHolder(inflater.inflate(R.layout.empty_list_entry, null));
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
       if(entries.get(position) instanceof Achievement) {
           return ACHIEVEMENT_TYPE;
       } else if (entries.get(position) instanceof Challenge) {
           return CHALLENGE_TYPE;
       }

        return EMPTY_TYPE;
    }

    @Override
    public void onBindViewHolder(EntryViewHolder holder, int position) {
        ListEntry lr = entries.get(position);
        lr.bindToView(holder);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class EntryViewHolder<E> extends RecyclerView.ViewHolder {

        private E view;

        public EntryViewHolder(View itemView) {
            super(itemView);
            view = (E) itemView;
        }

        public E getView() {
            return view;
        }
    }
}
