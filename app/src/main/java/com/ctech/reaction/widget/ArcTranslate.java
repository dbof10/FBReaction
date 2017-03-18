package com.ctech.reaction.widget;

import android.graphics.Point;
import android.graphics.PointF;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ArcTranslate extends Animation {

  private final float mFromXValue;
  private final float mToXValue;
  private final float mYValue;
  private final int mFromXType;
  private final int mToXType;
  private final int mYType;
  private PointF start;
  private PointF end;
  private PointF middle;

  public ArcTranslate(long duration, int fromXType, float fromXValue,
      int toXType, float toXValue, int yType, float yValue) {
    setDuration(duration);

    mFromXValue = fromXValue;
    mToXValue = toXValue;
    mYValue = yValue;

    mFromXType = fromXType;
    mToXType = toXType;
    mYType = yType;
  }

  /**
   * Calculate the position on a quadratic bezier curve given three points
   * and the percentage of time passed.
   * from http://en.wikipedia.org/wiki/B%C3%A9zier_curve
   *
   * @param interpolatedTime - the fraction of the duration that has passed where 0<=time<=1
   * @param p0 - a single dimension of the starting point
   * @param p1 - a single dimension of the middle point
   * @param p2 - a single dimension of the ending point
   */
  private long calcBezier(float interpolatedTime, float p0, float p1, float p2) {
    return Math.round((Math.pow((1 - interpolatedTime), 2) * p0)
        + (2 * (1 - interpolatedTime) * interpolatedTime * p1)
        + (Math.pow(interpolatedTime, 2) * p2));
  }

  @Override
  protected void applyTransformation(float interpolatedTime, Transformation t) {
    float dx = calcBezier(interpolatedTime, start.x, middle.x, end.x);
    float dy = calcBezier(interpolatedTime, start.y, middle.y, end.y);
    t.getMatrix().setTranslate(dx, dy);
  }

  @Override
  public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);
    float startX = resolveSize(mFromXType, mFromXValue, width, parentWidth);
    float endX = resolveSize(mToXType, mToXValue, width, parentWidth);
    float middleY = resolveSize(mYType, mYValue, width, parentWidth);
    float middleX = startX + ((endX - startX) / 2);
    start = new PointF( startX, 0);
    end = new PointF(endX, 0);
    middle = new PointF(middleX,  middleY);
  }
}