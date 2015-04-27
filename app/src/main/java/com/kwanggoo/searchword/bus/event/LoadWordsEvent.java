package com.kwanggoo.searchword.bus.event;

import com.kwanggoo.searchword.Word;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by josunghwan on 15. 4. 19..
 */
public class LoadWordsEvent {
    private ArrayList<Word> mWords;
    public LoadWordsEvent(Word[] words) {
        mWords = new ArrayList(Arrays.asList(words));
    }

    public ArrayList<Word> getWords() {
        return mWords;
    }
}
