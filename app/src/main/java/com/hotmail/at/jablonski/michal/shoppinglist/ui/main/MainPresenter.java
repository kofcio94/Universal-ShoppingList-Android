package com.hotmail.at.jablonski.michal.shoppinglist.ui.main;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemUpdater;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemsSelector;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BasePresenter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.archived.ArchivedFragment;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.current.CurrentFragment;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootListsAdapter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainActivity> {

    public interface ViewController {

        void showPlusButton();

        void hidePlusButton();

        void initView();

        void initRecyclerView(RecyclerView recyclerView, List<RootItem> items);

        void initRecyclerViewArchived(RecyclerView recyclerView, List<RootItem> items);

        RootListsAdapter getAdapter();

        RootListsAdapter getArchivedAdapter();
    }

    @Override
    public void onLoad(MainActivity view) {
        super.onLoad(view);
        getView().initView();
    }

    public void onPageSelected(int pagePosition) {
        switch (pagePosition) {
            case 0:
                getView().showPlusButton();
                break;
            case 1:
                getView().hidePlusButton();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Fragment[] getFragments() {
        CurrentFragment currentFragment = new CurrentFragment();
        ArchivedFragment archivedFragment = new ArchivedFragment();

        return new Fragment[]{currentFragment, archivedFragment};
    }

    public void setUpDataCurrent(RecyclerView recyclerView) {
        Observable.just(false)
                .map(archived -> new RootItemsSelector().select(archived))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> getView().initRecyclerView(recyclerView, result));
    }

    public void setUpDataArchived(RecyclerView recyclerView) {
        Observable.just(true)
                .map(archived -> new RootItemsSelector().select(archived))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> getView().initRecyclerView(recyclerView, result));
    }

    public void onLikeClick(RootItem rootItem, boolean like) {
        rootItem.setLiked(like);
        Observable.just(rootItem)
                .map(item -> new RootItemUpdater().update(item))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    //async results
                });
    }
}
