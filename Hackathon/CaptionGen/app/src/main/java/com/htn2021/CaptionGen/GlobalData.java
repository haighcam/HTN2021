package com.htn2021.CaptionGen;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    private static GlobalData mInstance;
    private TorchModel mModel;
    private TTS mTTS;
    private List<CustomCallback<TorchModel, TTS>> mObservers = new ArrayList<CustomCallback<TorchModel, TTS>>();
    private Boolean ModelLoaded = false;


    public void addObserver(CustomCallback<TorchModel, TTS> observer) {
        if (ModelLoaded) {
            observer.callback(mModel, mTTS);
        } else {
            mObservers.add(observer);
        }
    }

    private GlobalData() { }

    public static GlobalData getInstance() {
        if (mInstance == null) {
            mInstance = new GlobalData();
        }
        return mInstance;
    }

    public void loadModel(Context context) {
        final Context parameter = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mTTS = new TTS(parameter);
                    mModel = new TorchModel(parameter);
                    ModelLoaded = true;
                    for (CustomCallback<TorchModel, TTS> observer : mObservers) {
                        observer.callback(mModel, mTTS);
                    }
                    mObservers.clear();
                } catch (IOException e) {
                    Log.e("CaptionGen", "Error reading assets", e);
                }
            }
        }).start();
    }
}
