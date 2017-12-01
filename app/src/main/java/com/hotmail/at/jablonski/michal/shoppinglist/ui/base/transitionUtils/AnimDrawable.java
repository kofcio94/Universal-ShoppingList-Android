package com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Property;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class AnimDrawable extends Drawable {

    private Paint paint;
    private float cornerRadius;

    public static final Property<AnimDrawable, Float> CORNER_RADIUS =
            new Property<AnimDrawable, Float>(Float.class, "cornerRadius") {

        @Override
        public void set(AnimDrawable animDrawable, Float value) {
            animDrawable.setCornerRadius(value);
        }

        @Override
        public Float get(AnimDrawable animDrawable) {
            return animDrawable.getCornerRadius();
        }
    };

    public static final Property<AnimDrawable, Integer> COLOR =
            new Property<AnimDrawable, Integer>(Integer.class, "color") {

        @Override
        public void set(AnimDrawable animDrawable, Integer value) {
            animDrawable.setColor(value);
        }

        @Override
        public Integer get(AnimDrawable animDrawable) {
            return animDrawable.getColor();
        }
    };

    public AnimDrawable(@ColorInt int color, float cornerRadius) {
        this.cornerRadius = cornerRadius;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        invalidateSelf();
    }

    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(getBounds().left, getBounds().top, getBounds().right, getBounds()
                .bottom, cornerRadius, cornerRadius, paint);
    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        outline.setRoundRect(getBounds(), cornerRadius);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }


}
