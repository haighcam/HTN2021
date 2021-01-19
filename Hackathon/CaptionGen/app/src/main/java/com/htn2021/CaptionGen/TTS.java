package com.htn2021.CaptionGen;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Locale;

import static java.lang.Math.random;

public class TTS implements OnInitListener {
    private TextToSpeech mTTS;
    private Boolean mReady = false;
    private Queue<String>  mQ;
    private String id;
    private int nCalls = 0;

    public TTS(Context context) {
        mQ = new LinkedList<>();
        mTTS = new TextToSpeech(context, this);
        id = Double.toHexString(random());
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mReady = true;
            Log.e("TTS", "loaded");
            mTTS.setLanguage(Locale.UK);
            mTTS.setSpeechRate(1.33f);
            while (true) {
                String val = mQ.poll();
                if (val == null) {
                    break;
                } else {
                    mTTS.speak(val,  TextToSpeech.QUEUE_ADD, null, id + Integer.toString(nCalls));
                    nCalls++;
                }
            }
        } else {
            Log.e("TTS", "not loaded");
        }
    }

    public void speak(String text) {
        if (mReady) {
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, null, id + Integer.toString(nCalls));
            nCalls++;
            Log.e("TTS", "requested :" + text);
        } else {
            mQ.add(text);
            Log.e("TTS", "not ready :" + text);
        }
    }
}
