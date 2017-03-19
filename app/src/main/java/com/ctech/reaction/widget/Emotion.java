package com.ctech.reaction.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import com.ctech.reaction.R;
import com.ctech.reaction.util.Constants;
import com.ctech.reaction.util.DisplayUtil;
import com.facebook.keyframes.KeyframesDirectionallyScalingDrawable;
import com.facebook.keyframes.KeyframesDrawable;
import com.facebook.keyframes.KeyframesDrawableBuilder;
import com.facebook.keyframes.deserializers.KFImageDeserializer;
import com.facebook.keyframes.model.KFImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KenZira on 3/10/17.
 */

public class Emotion {

  static final int SMALL_SIZE = DisplayUtil.dpToPx(24);
  static final int MEDIUM_SIZE = DisplayUtil.dpToPx(32);
  static final int LARGE_SIZE = DisplayUtil.dpToPx(72);

  private static final int SPACING_TO_LABEL = DisplayUtil.dpToPx(16);
  private static final int MAX_WIDTH_TITLE = DisplayUtil.dpToPx(56);

  int size = 0;

  int startAnimatedSize;
  int endAnimatedSize;

  float x;
  float y;

  float startAnimatedX;

  float startAnimatedY;
  float endAnimatedY;

  private Paint textPaint;

  private KeyframesDrawable imageDrawable;

  private Rect imageBound;
  private RectF textBound;

  private Context context;
  private float labelRatio;
  private Bitmap imageTitle;

  Emotion(Context context, String title, String imageResource) {
    this.context = context;

    imageDrawable = new KeyframesDrawableBuilder().withImage(getKFImage(imageResource)).build();
    imageDrawable.startAnimation();

    textPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

    imageBound = new Rect();
    textBound = new RectF();

    snapShotLabelView(title);
  }

  private KFImage getKFImage(String fileName) {
    AssetManager assetManager = context.getAssets();

    InputStream stream;
    KFImage kfImage = null;

    try {
      stream = assetManager.open(fileName);
      kfImage = KFImageDeserializer.deserialize(stream);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return kfImage;
  }

  private void snapShotLabelView(String title) {
    LayoutInflater inflater = LayoutInflater.from(context);
    TextView labelView = (TextView) inflater.inflate(R.layout.view_label, null, false);
    labelView.setText(title);

    int width = (int) context.getResources().getDimension(R.dimen.label_width);
    int height = (int) context.getResources().getDimension(R.dimen.label_height);

    labelRatio = width / height  ;
    imageTitle = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

    Canvas canvas = new Canvas(imageTitle);
    labelView.layout(0, 0, width, height);
    labelView.draw(canvas);
  }

  private void setAlphaTitle(int alpha) {
    textPaint.setAlpha(alpha);
  }

  void draw(final Canvas canvas) {
    imageBound.set((int)x,(int) y, (int)x + size, (int)y + size);
    imageDrawable.setBounds(imageBound);
    imageDrawable.draw(canvas);
    drawLabel(canvas);
  }

  private void drawLabel(Canvas canvas) {
    int width = size - MEDIUM_SIZE ;
    int height = (int) (width / labelRatio);

    if (width <= 0) return;

    setAlphaTitle(Constants.MAX_ALPHA * width / MAX_WIDTH_TITLE);

    float x = this.x + (size - width) / 2;
    float y = this.y - SPACING_TO_LABEL - height;

    textBound.set(x, y, x + width, y + height);
    canvas.drawBitmap(imageTitle, null, textBound, textPaint);
  }

  void setCurrentSize(int currentSize) {
    if(currentSize > this.size){
      imageDrawable.setDirectionalScale(0.5F, 0.5F,
          KeyframesDirectionallyScalingDrawable.ScaleDirection.DOWN);
    }else {
      imageDrawable.setDirectionalScale(0.5F, 0.5F,
          KeyframesDirectionallyScalingDrawable.ScaleDirection.UP);
    }
    this.size = currentSize;
  }

}
