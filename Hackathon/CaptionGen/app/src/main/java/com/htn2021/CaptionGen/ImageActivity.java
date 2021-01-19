package com.htn2021.CaptionGen;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

public class ImageActivity extends AppCompatActivity {
  private Bitmap mImage;
  private TorchModel mModel;
  private TTS mTTS;
  private TextView mCaption;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.image_activity);
    Uri photoUri = null;
    Bundle bundle = getIntent().getExtras();
    if(bundle != null){
      String resid = bundle.getString("resId");
      photoUri = Uri.parse(resid);
    }

    try {
      mImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
    } catch (Exception e) {
      e.printStackTrace();
    }
    mImage = Bitmap.createScaledBitmap(mImage, 224, 224, false);

    ImageView imageView = findViewById(R.id.imageView2);
    imageView.setImageBitmap(mImage);

    mCaption = findViewById(R.id.textView);
    mCaption.setText("Computing Caption");

    final CustomCallback<TorchModel, TTS> observer = new CustomCallback<TorchModel, TTS>() {
      @Override
      public void callback(TorchModel model, TTS tts) {
        mModel = model;
        mTTS = tts;
        execute();
      }
    };
    GlobalData.getInstance().addObserver(observer);
    Log.e("CaptionGen", "ImageLoaded");
  }

  private void execute() {
    new Thread(() -> {
      // preparing input tensor
      final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(mImage,
              TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);

      String className = mModel.forward(inputTensor);

      // showing className on UI
      mCaption.setText(className);
      mTTS.speak(className);
    }).start();
  }
}
