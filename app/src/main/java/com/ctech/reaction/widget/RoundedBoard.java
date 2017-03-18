package com.ctech.reaction.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import com.ctech.reaction.util.Constants;
import com.ctech.reaction.util.DisplayUtil;

/**
 * Created by KenZira on 3/10/17.
 */

public class RoundedBoard {

  static final int WIDTH = 6 * Emotion.MEDIUM_SIZE + 7 * Constants.HORIZONTAL_SPACING;

  static final int HEIGHT = DisplayUtil.dpToPx(48);

  static final int SCALED_DOWN_HEIGHT = DisplayUtil.dpToPx(38);

  static final float LEFT = DisplayUtil.dpToPx(16);

  static final float BOTTOM = Constants.HEIGHT_VIEW_REACTION - 200;

  static final float TOP = BOTTOM - HEIGHT;

  static final float BASE_LINE = TOP + Emotion.MEDIUM_SIZE + Constants.VERTICAL_SPACING;

  float height = HEIGHT;
  float y;

  private float radius = height / 2;

  float startAnimatedHeight;
  float endAnimatedHeight;

  float startAnimatedY;
  float endAnimatedY;

  private Paint boardPaint;
  private RectF rect;

  RoundedBoard() {
    initPaint();
    rect = new RectF();
  }

  private void initPaint() {
    boardPaint = new Paint();
    boardPaint.setAntiAlias(true);
    boardPaint.setStyle(Paint.Style.FILL);
    boardPaint.setColor(Color.WHITE);
    boardPaint.setShadowLayer(5.0f, 0.0f, 2.0f, 0xFF000000);
  }

  void setCurrentHeight(float newHeight) {
    height = newHeight;
    y = BOTTOM - height;
  }

  void draw(Canvas canvas) {
    rect.set(LEFT, y, LEFT + WIDTH, y + height);
    canvas.drawRoundRect(rect, radius, radius, boardPaint);
  }
}
