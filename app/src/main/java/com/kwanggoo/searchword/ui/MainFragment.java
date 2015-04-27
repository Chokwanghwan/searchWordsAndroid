package com.kwanggoo.searchword.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kwanggoo.searchword.R;
import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.Word;
import com.kwanggoo.searchword.WordAdapter;
import com.kwanggoo.searchword.bus.BusProvider;
import com.kwanggoo.searchword.bus.event.GetWordsEvent;
import com.kwanggoo.searchword.bus.event.LoadWordsEvent;
import com.kwanggoo.searchword.bus.event.UpdateWordsEvent;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class MainFragment extends SearchWordFragment {
    public static final int SECTION_NUMBER = 0;

    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public MainFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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
        mFab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFab.attachToRecyclerView(mRecyclerView);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click FAB", Toast.LENGTH_SHORT).show();
            }
        });
        BusProvider.getBus().post(new GetWordsEvent(getString(R.string.user_email)));

        return rootView;
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

    @Subscribe
    public void onLoadWords(LoadWordsEvent event){
        ArrayList<Word> wordList = event.getWords();
        mAdapter.initDataSet(wordList);
    }

    private void updateWord(int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            String english = mAdapter.getDataset().get(position).getEnglish();
            mAdapter.getDataset().remove(position);
            mAdapter.notifyItemRemoved(position);
            UserInfo userInfo = UserInfo.getInstance();
            userInfo.plusKnownWordCount();

            BusProvider.getBus().post(new UpdateWordsEvent(getString(R.string.user_email), english, true));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
