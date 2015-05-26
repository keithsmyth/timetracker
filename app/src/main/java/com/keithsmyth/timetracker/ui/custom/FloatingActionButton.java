package com.keithsmyth.timetracker.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

/**
 * @author keithsmyth
 */
public class FloatingActionButton extends FrameLayout {

  public FloatingActionButton(Context context) {
    this(context, null, 0, 0);
  }

  public FloatingActionButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0, 0);
  }

  public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
    super(context, attrs, defStyleAttr);

    setClickable(true);

    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      setOutline();
    }
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  private void setOutline() {
    this.setElevation(6);

    // Set the outline provider for this view. The provider is given the outline which it can
    // then modify as needed. In this case we set the outline to be an oval fitting the
    // height
    // and width.
    setOutlineProvider(new ViewOutlineProvider() {
      @Override
      public void getOutline(View view, Outline outline) {
        outline.setOval(0, 0, getWidth(), getHeight());
      }
    });

    // Finally, enable clipping to the outline, using the provider we set above
    setClipToOutline(true);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      callInvalidateOutline();
    }
  }

  @TargetApi(VERSION_CODES.LOLLIPOP)
  private void callInvalidateOutline() {
    // As we have changed size, we should invalidate the outline so that is the the
    // correct size
    invalidateOutline();
  }

  @Override
  protected int[] onCreateDrawableState(int extraSpace) {
    return super.onCreateDrawableState(extraSpace + 1);
  }
}
