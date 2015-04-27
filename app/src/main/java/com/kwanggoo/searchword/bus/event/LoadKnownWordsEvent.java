package com.kwanggoo.searchword.bus.event;

import com.kwanggoo.searchword.Word;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by josunghwan on 15. 4. 25..
 */
public class LoadKnownWordsEvent {
    private ArrayList<Word> mWords;
    public LoadKnownWordsEvent(Word[] words) {
        mWords = new ArrayList(Arrays.asList(words));
    }

    public ArrayList<Word> getWords() {
        return mWords;
    }
}
