package com.hotmail.at.jablonski.michal.shoppinglist.ui.main;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hotmail.at.jablonski.michal.shoppinglist.R;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BaseActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.DetailsActivity;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.CallbackInterface;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootListsAdapter;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends BaseActivity
        implements MainPresenter.ViewController, CallbackInterface {

    private MainPresenter presenter;
    private ViewHolder viewHolder;
    private SwipeAdapter swipeAdapter;

    private RootListsAdapter rootAdapter;
    private RootListsAdapter rootArchivedAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHolder();
        initPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();

            if (extras != null) {
                String event = extras.getString(DetailsActivity.class.getSimpleName());

                if (event != null) {
                    if (event.equals("archived"))
                        showSuccessFullyArchivedDataInfo();
                    else
                        showSuccessFullyDeletedDataInfo();
                }
            }
        }

    }

    private void showSuccessFullyDeletedDataInfo() {
        showSnackBar(R.string.deleted);
    }

    private void showSuccessFullyArchivedDataInfo() {
        showSnackBar(R.string.archived);
    }

    private void showSnackBar(int resString) {
        Snackbar sb = Snackbar.make(viewHolder.coordinatorLayout, resString, Snackbar.LENGTH_LONG);
        sb.show();
    }

    private void initPresenter() {
        presenter = new MainPresenter();
        presenter.onLoad(this);
    }

    private void initHolder() {
        View activityView = getActivityView();
        viewHolder = new ViewHolder(activityView);
    }

    private void initAdapter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String[] titles = getAdapterTitles();
        swipeAdapter = new SwipeAdapter(fragmentManager, titles, presenter.getFragments());
        viewHolder.viewPager.setAdapter(swipeAdapter);
        setOnPageSelectedListener();
    }

    private String[] getAdapterTitles() {
        Resources res = getResources();
        return res.getStringArray(R.array.fragments);
    }

    private void setOnPageSelectedListener() {
        viewHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void initView() {
        changePageTabStripStyle();
        initAdapter();
        initClickListener();
        initDrawer();
    }

    private void initDrawer() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        ImageView headerView =
                (ImageView) layoutInflater.inflate(R.layout.drawer_header_view, null);

        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(viewHolder.toolbar)
                .inflateMenu(R.menu.drawer)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .withHeader(headerView)
                .build();
        drawer.deselect();

        Glide.with(this).load(R.drawable.material_header).into(headerView);

        drawer.setOnDrawerItemClickListener((view, position, drawerItem) -> {
            viewHolder.viewPager.setCurrentItem(position - 1, true);
            drawer.deselect();
            return false;
        });
    }

    private void initClickListener() {
        viewHolder.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra(AddItemActivity.class.getSimpleName(), MainActivity.class.getSimpleName());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                viewHolder.floatingActionButton,
                                getString(R.string.transition_dialog));
                startActivity(intent, options.toBundle());
            } else
                startActivity(intent);
        });
    }

    private void changePageTabStripStyle() {
        viewHolder.pageTabStrip.setTabIndicatorColorResource(R.color.colorAccent);
    }

    @Override
    public void initRecyclerView(RecyclerView recyclerView, List<RootItem> items) {
        if (rootAdapter == null) {
            rootAdapter = new RootListsAdapter(items) {
                @Override
                protected void onClick(RootItem item, ViewHolder itemView) {
                    startDetailsActivity(item, itemView);
                }

                @Override
                protected void onLikeClick(RootItem rootItem, boolean like) {
                    presenter.onLikeClick(rootItem, like);
                }
            };

            LinearLayoutManager layoutManager = getLinearLayoutManager();
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(this, layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(rootAdapter);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        showPlusButton();
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 || dy < 0 && viewHolder.floatingActionButton.isShown()) {
                        hidePlusButton();
                    }
                }
            });
        } else {
            rootAdapter.setRootListsItems(items);
            rootAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initRecyclerViewArchived(RecyclerView recyclerView, List<RootItem> items) {
        if (rootArchivedAdapter == null) {
            rootArchivedAdapter = new RootListsAdapter(items) {
                @Override
                protected void onClick(RootItem item, ViewHolder itemView) {
                    startDetailsActivity(item, itemView);
                }

                @Override
                protected void onLikeClick(RootItem rootItem, boolean like) {
                    //nothing for archived
                }
            };

            LinearLayoutManager layoutManager = getLinearLayoutManager();
            recyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(this, layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(rootArchivedAdapter);
        } else {
            rootArchivedAdapter.setRootListsItems(items);
            rootArchivedAdapter.notifyDataSetChanged();
        }
    }

    private LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        return layoutManager;
    }

    @SuppressLint("RestrictedApi")
    private void startDetailsActivity(RootItem item, RootListsAdapter.ViewHolder itemView) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        String extraKey = item.getClass().getSimpleName();
        intent.putExtra(extraKey, item);
        if (!item.isArchived()) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                itemView.letterView,
                                getString(R.string.transition_image_to_toolbar));
                startActivityForResult(intent, 1, options.toBundle());
            } else {
                startActivityForResult(intent, 1);
            }

        } else
            startActivity(intent);
    }

    @Override
    public RootListsAdapter getAdapter() {
        return rootAdapter;
    }

    @Override
    public RootListsAdapter getArchivedAdapter() {
        return rootArchivedAdapter;
    }

    @Override
    public void showPlusButton() {
        viewHolder.floatingActionButton.show();
    }

    @Override
    public void hidePlusButton() {
        viewHolder.floatingActionButton.hide();
    }

    @Override
    public void setUpRecyclerView(RecyclerView recyclerView) {
        presenter.setUpDataCurrent(recyclerView);
    }

    @Override
    public void setUpRecyclerViewForArchivedData(RecyclerView recyclerView) {
        presenter.setUpDataArchived(recyclerView);
    }

    static class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.fab)
        FloatingActionButton floatingActionButton;

        @BindView(R.id.view_pager)
        ViewPager viewPager;

        @BindView(R.id.pageTabStrip)
        PagerTabStrip pageTabStrip;

        @BindView(R.id.toolbar)
        Toolbar toolbar;

        @BindView(R.id.coordinator_layout)
        CoordinatorLayout coordinatorLayout;
    }
}
