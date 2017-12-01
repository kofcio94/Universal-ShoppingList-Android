package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.hotmail.at.jablonski.michal.shoppinglist.R;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter.AddItemPresenter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter.PresenterFactory;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BaseActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils.SharedEnter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils.SharedReturn;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddItemActivity
        extends BaseActivity
        implements AddItemViewController {

    private ViewHolder viewHolder;
    private AddItemPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initViewHolder();
        initPresenter();
        initView();
    }

    private void initViewHolder() {
        viewHolder = new ViewHolder(getActivityView());
    }

    private void initPresenter() {
        String callingClassName = getIntent().getStringExtra(AddItemActivity.class.getSimpleName());
        presenter = PresenterFactory.getGenericPresenter(callingClassName);
        presenter.setViewController(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedElementTransitions() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        SharedEnter sharedEnter = new SharedEnter();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        SharedReturn sharedReturn = new SharedReturn();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        sharedEnter.addTarget(viewHolder.container);
        sharedReturn.addTarget(viewHolder.container);

        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    public void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
    }

    public void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupSharedElementTransitions();
        }

        View.OnClickListener dismissListener = view -> dismiss();
        viewHolder.cancel.setOnClickListener(dismissListener);

        viewHolder.buttonSave.setOnClickListener(v -> {
                    String input = viewHolder.input.getText().toString();
                    if (TextUtils.isEmpty(input)) {
                        showErrorSnackBar();
                        return;
                    }
                    presenter.saveItem(input, getRootId());
                }
        );
    }

    private Integer getRootId() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return extras.getInt("rootId");
        } else
            return null;
    }

    private void showErrorSnackBar() {
        Snackbar sb = Snackbar.make(
                viewHolder.container,
                R.string.empty_error,
                Snackbar.LENGTH_SHORT);
        sb.show();
    }

    @Override
    public void closeDialog() {
        onBackPressed();
    }

    @Override
    public void showRootIdError() {
        Snackbar sb = Snackbar.make(
                viewHolder.container,
                R.string.data_malformed,
                Snackbar.LENGTH_SHORT);
        sb.show();
    }

    static class ViewHolder {

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.container)
        ViewGroup container;

        @BindView(R.id.save)
        Button buttonSave;

        @BindView(R.id.text_input)
        TextInputEditText input;

        @BindView(R.id.cancel)
        Button cancel;
    }
}
