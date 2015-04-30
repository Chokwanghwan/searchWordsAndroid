package com.kwanggoo.searchword;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> implements Filterable {
    private static final String TAG = WordAdapter.class.getSimpleName();
    private ArrayList<Word> mDataset;
    private ArrayList<Word> orig;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEnglish;
        TextView tvMean;

        public ViewHolder(View v) {
            super(v);
            tvEnglish = (TextView) v.findViewById(R.id.tv_english);
            tvMean = (TextView) v.findViewById(R.id.tv_mean);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WordAdapter() {
        this.mDataset = new ArrayList<>();
    }

    public WordAdapter(ArrayList<Word> wordList) {
        initDataSet(wordList);
    }

    public ArrayList<Word> getDataset() {
        return mDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_word, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Word word = mDataset.get(position);
        holder.tvEnglish.setText(word.getEnglish());
        holder.tvMean.setText(word.getMean());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset==null)
            return 0;
        return mDataset.size();
    }

    public void initDataSet(ArrayList<Word> wordList) {
        this.mDataset = wordList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                List<Word> results = new ArrayList<Word>();
                if (orig == null)
                    orig = mDataset;
                if (constraint != null) {
                    if(constraint.length() == 0)
                        results = (List<Word>) orig.clone();
                    else if (orig != null & orig.size() > 0) {
                        for (final Word g : orig) {
                            String english = g.getEnglish();
                            if(english == null)
                                continue;
                            if(english.toLowerCase().startsWith(constraint.toString().toLowerCase())){
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataset = (ArrayList<Word>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
