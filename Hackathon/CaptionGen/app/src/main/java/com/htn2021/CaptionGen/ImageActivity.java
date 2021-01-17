package com.htn2021.CaptionGen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.IOException;


public class ImageActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.caption_image);
    Uri photoUri = null;
    Bundle bundle = getIntent().getExtras();
    if(bundle != null){
      String resid = bundle.getString("resId");
      photoUri = Uri.parse(resid);
    }

    Bitmap bitmap = null;
    try {
      bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ImageView imageView = findViewById(R.id.imageView2);
    imageView.setImageBitmap(bitmap);

    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
    bitmapDrawable.setBounds(0, 0, 224, 224); //Make it a new size in pixels.
    bitmap = bitmapDrawable.getBitmap(); //Convert it back to a bitmap optimised for display purposes.

    TorchModel model = null;
    try {
      // loading serialized torchscript module from packaged into app android asset model.pt,
      // app/src/model/assets/model.pt
      model = new TorchModel(this);
    } catch (IOException e) {
      Log.e("PytorchHelloWorld", "Error reading assets", e);
      finish();
    }

    // preparing input tensor
    final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap,
        TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB);

    String className = model.forward(inputTensor);


    // showing className on UI
    TextView textView = findViewById(R.id.textView);
    textView.setText(className);
  }
}
