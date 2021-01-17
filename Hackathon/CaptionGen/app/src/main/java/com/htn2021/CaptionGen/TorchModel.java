package com.htn2021.CaptionGen;

import android.content.Context;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class TorchModel {
    Module mModule;
    Properties Wordmap;

    public TorchModel(Context context) throws IOException {
        mModule = Module.load(assetFilePath(context, "test.pt"));
        Wordmap = new Properties();
        InputStream wordmap_input = new FileInputStream(assetFilePath(context, "wordmap.properties"));
        Wordmap.load(wordmap_input);
    }

    public String forward(Tensor inputTensor) {
        final Tensor outputTensor = mModule.forward(IValue.from(inputTensor)).toTensor();
        final long[] words = outputTensor.getDataAsLongArray();
        String caption = "";

        int i;
        for (i=0; i< words.length; i++) {
            long val = words[i];
            if (val != 0 && val != 9488 && val != 9489) {
                caption = caption + " " + Wordmap.getProperty(Long.toString(val));
            }
        }

        return caption.substring(0, 1).toUpperCase() + caption.substring(1) + ".";
    }

    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
}
