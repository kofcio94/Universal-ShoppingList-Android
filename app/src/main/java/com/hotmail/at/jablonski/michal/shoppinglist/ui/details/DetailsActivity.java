package com.hotmail.at.jablonski.michal.shoppinglist.ui.details;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ArcMotion;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.hotmail.at.jablonski.michal.shoppinglist.R;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BaseActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils.SharedEnter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.transitionUtils.SharedReturn;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity
        extends BaseActivity
        implements DetailsPresenter.ViewController {

    private DetailsPresenter presenter;
    private ViewHolder viewHolder;
    private ToolbarHolder toolbarHolder;
    private ProgressDialog progressDialog;

    private ShoppingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initHolders();
        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setupSharedElementTransitions() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.
                loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        SharedEnter sharedEnter = new SharedEnter();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        SharedReturn sharedReturn = new SharedReturn();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        sharedEnter.addTarget(viewHolder.addButton);
        sharedReturn.addTarget(viewHolder.addButton);

        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }


    private void initHolders() {
        viewHolder = new ViewHolder(getActivityView());
        toolbarHolder = new ToolbarHolder(viewHolder.toolbar);
    }

    private void initPresenter() {
        presenter = new DetailsPresenter();
        presenter.onLoad(this);
    }

    @Override
    public void initView(boolean isArchived) {
        initToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupSharedElementTransitions();
        }
        initReadyToUseAdapter(isArchived);
        initWidgets(isArchived);
    }

    private void initWidgets(boolean isArchived) {
        if (!isArchived) {
            viewHolder.addButton.setOnClickListener(v -> {
                Intent intent = new Intent(DetailsActivity.this, AddItemActivity.class);
                intent.putExtra("rootId", presenter.getRootId());
                intent.putExtra(AddItemActivity.class.getSimpleName(),
                        DetailsActivity.class.getSimpleName());

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(
                                    DetailsActivity.this,
                                    viewHolder.addButton,
                                    getString(R.string.transition_dialog));

                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            });
            toolbarHolder.archiveIcon.setOnClickListener(v -> presenter.onArchiveClicked());
            toolbarHolder.deleteIcon.setOnClickListener(v -> presenter.onDeleteClicked());
        } else {
            toolbarHolder.archiveIcon.setVisibility(View.GONE);
            toolbarHolder.deleteIcon.setVisibility(View.GONE);
            viewHolder.addButton.setVisibility(View.GONE);
        }
    }

    private void initReadyToUseAdapter(boolean isAchieved) {
        adapter = new ShoppingListAdapter(new ArrayList<>(), isAchieved) {
            @Override
            protected void onCheckedItem(boolean checked, ShoppingItem item) {
                presenter.onItemChecked(checked, item);
            }

            @Override
            protected void onDeleteClicked(ShoppingItem item) {
                presenter.deleteItem(item, adapter.getShoppingListItems());
            }
        };

        viewHolder.recyclerView.setLayoutManager(getLinearLayoutManager());
        viewHolder.recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(viewHolder.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        return layoutManager;
    }

    @Override
    public boolean onSupportNavigateUp() {
        presenter.onArrowBackClicked();
        return true;
    }

    @Override
    public void refreshList(List<ShoppingItem> shoppingListItems) {
        adapter.setShoppingList(shoppingListItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(R.string.app_name);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setProgressNumberFormat(null);
            progressDialog.setProgressPercentFormat(null);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);

            progressDialog.show();
        } else {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setToolbarName(String name) {
        toolbarHolder.toolbarTitle.setText(name);
    }

    @Override
    public void showSuccessFullyArchivedDialogAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(DetailsActivity.class.getSimpleName(), "archived");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showSuccessFullyDeletedDialogAndFinish() {
        Intent intent = new Intent();
        intent.putExtra(this.getClass().getSimpleName(), "deleted");
        setResult(RESULT_OK, intent);
        finish();
    }

    static class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.toolbar_add)
        Toolbar toolbar;

        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        @BindView(R.id.fab)
        FloatingActionButton addButton;
    }

    static class ToolbarHolder {
        ToolbarHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.delete)
        View deleteIcon;

        @BindView(R.id.archive)
        View archiveIcon;

        @BindView(R.id.item_name)
        TextView toolbarTitle;
    }
}
