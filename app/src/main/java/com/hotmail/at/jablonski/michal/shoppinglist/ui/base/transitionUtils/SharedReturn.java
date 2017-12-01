package com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.hotmail.at.jablonski.michal.shoppinglist.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SharedReturn extends ChangeBounds {

    private static final String PROPERTY_COLOR = "rectMorph:color";
    private static final String PROPERTY_CORNER_RADIUS = "rectMorph:cornerRadius";
    private static final String[] TRANSITION_PROPERTIES = {
            PROPERTY_COLOR,
            PROPERTY_CORNER_RADIUS
    };

    public SharedReturn() {
        super();
    }

    public SharedReturn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), R.color.dialogBackground));
        transitionValues.values.put(PROPERTY_CORNER_RADIUS, view.getResources()
                .getDimensionPixelSize(R.dimen.dialog_corners));
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        final View view = transitionValues.view;
        if (view.getWidth() <= 0 || view.getHeight() <= 0) {
            return;
        }
        transitionValues.values.put(PROPERTY_COLOR, ContextCompat.getColor(view.getContext(), R.color.fabColor));
        transitionValues.values.put(PROPERTY_CORNER_RADIUS, view.getHeight() / 2);//view.getHeight() / 2
    }

    @Override
    public Animator createAnimator(final ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (startValues == null || endValues == null || changeBounds == null) {
            return null;
        }

        Integer startColor = (Integer) startValues.values.get(PROPERTY_COLOR);
        Integer startCornerRadius = (Integer) startValues.values.get(PROPERTY_CORNER_RADIUS);
        Integer endColor = (Integer) endValues.values.get(PROPERTY_COLOR);
        Integer endCornerRadius = (Integer) endValues.values.get(PROPERTY_CORNER_RADIUS);

        if (startColor == null || startCornerRadius == null || endColor == null || endCornerRadius == null) {
            return null;
        }

        AnimDrawable background = new AnimDrawable(startColor, startCornerRadius);
        endValues.view.setBackground(background);

        Animator color = ObjectAnimator.ofArgb(background, AnimDrawable.COLOR, endColor);
        Animator corners = ObjectAnimator.ofFloat(background, AnimDrawable.CORNER_RADIUS, endCornerRadius);

        if (endValues.view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) endValues.view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View v = vg.getChildAt(i);
                v.animate().alpha(0f).translationY(v.getHeight() / 3).setStartDelay(0L).setDuration(50L)
                        .setInterpolator(AnimationUtils.loadInterpolator(vg.getContext(), android.R.interpolator.fast_out_linear_in))
                        .start();
            }
        }

        AnimatorSet transition = new AnimatorSet();
        transition.playTogether(changeBounds, corners, color);
        transition.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(), android.R.interpolator.fast_out_slow_in));
        transition.setDuration(300);
        return transition;
    }
}
