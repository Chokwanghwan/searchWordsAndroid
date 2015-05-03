package com.kwanggoo.searchword.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kwanggoo.searchword.R;
import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.Word;
import com.kwanggoo.searchword.WordAdapter;
import com.kwanggoo.searchword.bus.BusProvider;
import com.kwanggoo.searchword.bus.event.GetKnownWordsEvent;
import com.kwanggoo.searchword.bus.event.LoadKnownWordsEvent;
import com.kwanggoo.searchword.bus.event.UpdateWordsEvent;
import com.kwanggoo.searchword.util.UserManager;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class KnownWordsFragment extends SearchWordFragment {
    public static final int SECTION_NUMBER = 1;

    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static KnownWordsFragment newInstance() {
        KnownWordsFragment fragment = new KnownWordsFragment();
        return fragment;
    }

    public KnownWordsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_known_words, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.word_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WordAdapter();
        mRecyclerView.setAdapter(mAdapter);
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                updateWord(reverseSortedPositions);
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                updateWord(reverseSortedPositions);
                            }
                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);
        String email = UserManager.getInstance(getActivity()).getUserEmail();
        BusProvider.getBus().post(new GetKnownWordsEvent(email));

        return rootView;
    }

    @Subscribe
    public void onLoadKnownWords(LoadKnownWordsEvent event){
        ArrayList<Word> wordList = event.getWords();
        mAdapter.initDataSet(wordList);
    }

    private void updateWord(int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            String english = mAdapter.getDataset().get(position).getEnglish();
            mAdapter.getDataset().remove(position);
            mAdapter.notifyItemRemoved(position);
            UserInfo userInfo = UserInfo.getInstance();
            userInfo.minusKnownWordCount();
            String email = UserManager.getInstance(getActivity()).getUserEmail();
            BusProvider.getBus().post(new UpdateWordsEvent(email, english, false));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ((MainActivity) activity).onSectionAttached(SECTION_NUMBER);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
