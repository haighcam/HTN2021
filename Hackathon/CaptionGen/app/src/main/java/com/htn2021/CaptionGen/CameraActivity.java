package com.htn2021.CaptionGen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Size;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.TextureView;

import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.nio.FloatBuffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.core.app.ActivityCompat;


public class CameraActivity extends AppCompatActivity {
  private static final int REQUEST_CODE_CAMERA_PERMISSION = 200;
  private static final String[] PERMISSIONS = {Manifest.permission.CAMERA};
  protected HandlerThread mBackgroundThread;
  protected Handler mBackgroundHandler;
  private long mLastAnalysisResultTime;
  private FloatBuffer mInputTensorBuffer;
  private Tensor mInputTensor;
  private TextView mCaption;
  private static final int INPUT_TENSOR_WIDTH = 224;
  private static final int INPUT_TENSOR_HEIGHT = 224;
  private TTS mTTS;
  private TorchModel mModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.camera_activity);
    final CustomCallback<TorchModel, TTS> observer = new CustomCallback<TorchModel, TTS>() {
      @Override
      public void callback(TorchModel model, TTS tts) {
        mModel = model;
        mTTS = tts;
      }
    };
    GlobalData.getInstance().addObserver(observer);

    mInputTensorBuffer =
            Tensor.allocateFloatBuffer(3 * INPUT_TENSOR_WIDTH * INPUT_TENSOR_HEIGHT);
    mInputTensor = Tensor.fromBlob(mInputTensorBuffer, new long[]{1, 3, INPUT_TENSOR_HEIGHT, INPUT_TENSOR_WIDTH});

    mCaption = findViewById(R.id.textView);
    mCaption.setText("Computing Caption");

    startBackgroundThread();

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(
              this,
              PERMISSIONS,
              REQUEST_CODE_CAMERA_PERMISSION);
    } else {
      setupCameraX();
    }
  }

  protected void startBackgroundThread() {
    mBackgroundThread = new HandlerThread("ModuleActivity");
    mBackgroundThread.start();
    mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
  }

  @Override
  public void onRequestPermissionsResult(
          int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
      if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
        Toast.makeText(
                this,
                "You can't use image classification example without granting CAMERA permission",
                Toast.LENGTH_LONG)
                .show();
        finish();
      } else {
        setupCameraX();
      }
    }
  }

  private void setupCameraX() {
    final TextureView textureView = getCameraPreviewTextureView();
    final PreviewConfig previewConfig = new PreviewConfig.Builder().build();
    final Preview preview = new Preview(previewConfig);
    preview.setOnPreviewOutputUpdateListener(output -> textureView.setSurfaceTexture(output.getSurfaceTexture()));

    final ImageAnalysisConfig imageAnalysisConfig =
            new ImageAnalysisConfig.Builder()
                    .setTargetResolution(new Size(INPUT_TENSOR_WIDTH, INPUT_TENSOR_HEIGHT))
                    .setCallbackHandler(mBackgroundHandler)
                    .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
                    .build();
    final ImageAnalysis imageAnalysis = new ImageAnalysis(imageAnalysisConfig);
    imageAnalysis.setAnalyzer(
            (image, rotationDegrees) -> {
              if (SystemClock.elapsedRealtime() - mLastAnalysisResultTime < 500) {
                return;
              }

              final String result = analyzeImage(image, rotationDegrees);
              if (result != null) {
                mLastAnalysisResultTime = SystemClock.elapsedRealtime();
                runOnUiThread(() -> applyToUiAnalyzeImageResult(result));
              }
            });

    CameraX.bindToLifecycle(this, preview, imageAnalysis);
  }

  protected TextureView getCameraPreviewTextureView() {
    return (TextureView) findViewById(R.id.image_classification_texture_view);
  }

  protected void applyToUiAnalyzeImageResult(String result) {
    mCaption.setText(result);
    if (mTTS != null) {
      mTTS.speak(result);
    }
  }

  protected String analyzeImage(ImageProxy image, int rotationDegrees) {
    if (mModel != null) {
      TensorImageUtils.imageYUV420CenterCropToFloatBuffer(
              image.getImage(), rotationDegrees,
              INPUT_TENSOR_WIDTH, INPUT_TENSOR_HEIGHT,
              TensorImageUtils.TORCHVISION_NORM_MEAN_RGB,
              TensorImageUtils.TORCHVISION_NORM_STD_RGB,
              mInputTensorBuffer, 0);
      return mModel.forward(mInputTensor);
    } else {
      return null;
    }
  }

  @Override
  protected void onDestroy() {
    stopBackgroundThread();
    super.onDestroy();
  }

  protected void stopBackgroundThread() {
    mBackgroundThread.quitSafely();
    try {
      mBackgroundThread.join();
      mBackgroundThread = null;
      mBackgroundHandler = null;
    } catch (InterruptedException e) {
      Log.e("DemoApp", "Error on stopping background thread", e);
    }
  }

}
