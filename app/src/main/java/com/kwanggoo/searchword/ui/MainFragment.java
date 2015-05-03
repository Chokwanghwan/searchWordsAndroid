package com.kwanggoo.searchword.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kwanggoo.searchword.R;
import com.kwanggoo.searchword.UserInfo;
import com.kwanggoo.searchword.Word;
import com.kwanggoo.searchword.WordAdapter;
import com.kwanggoo.searchword.bus.BusProvider;
import com.kwanggoo.searchword.bus.event.GetWordsEvent;
import com.kwanggoo.searchword.bus.event.LoadWordsEvent;
import com.kwanggoo.searchword.bus.event.UpdateWordsEvent;
import com.kwanggoo.searchword.util.UserManager;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

public class MainFragment extends SearchWordFragment implements SearchView.OnQueryTextListener {
    public static final int SECTION_NUMBER = 0;
    private static final String TAG = SearchWordFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private WordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFab;

    private SearchView mSearchView;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        mSearchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setOnQueryTextListener(this);
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
        setHasOptionsMenu(true);
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
                onClickFab(v);
            }
        });
        String email = UserManager.getInstance(getActivity()).getUserEmail();
        BusProvider.getBus().post(new GetWordsEvent(email));

        return rootView;
    }

    private void onClickFab(View v) {
        mFab.hide(true);
        new MaterialDialog.Builder(getActivity())
                .widgetColorRes(R.color.color_accent)
                .title(R.string.add_url_title)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(R.string.add_url_hint, R.string.add_url_prefill, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                })
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mFab.show(true);
                    }
                })
                .negativeText(R.string.add_url_cancel)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Toast.makeText(getActivity(), "ADD URL", Toast.LENGTH_SHORT).show();
                        mFab.show(true);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        mFab.show(true);
                    }
                }).show();
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
    public void onLoadWords(LoadWordsEvent event) {
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

            String email = UserManager.getInstance(getActivity()).getUserEmail();
            BusProvider.getBus().post(new UpdateWordsEvent(email, english, true));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        Log.i(TAG, "onQueryTextSubmit : " + s);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.i(TAG, "onQueryTextChange : " + s);
        mAdapter.getFilter().filter(s);
        return false;
    }
}
