package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter;

import android.support.annotation.Nullable;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemInserter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemViewController;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddRootItemsPresenter implements AddItemPresenter<String, Integer> {

    private AddItemViewController viewController;

    @Override
    public void setViewController(AddItemViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void saveItem(String itemName, @Nullable Integer rootId) {
        RootItem rootItem = new RootItem();
        rootItem.setArchived(false);
        rootItem.setLiked(false);
        rootItem.setName(itemName);
        rootItem.setTimestamp(System.currentTimeMillis());

        Observable.just(rootItem)
                .map(item -> new RootItemInserter().insert(item))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((id) -> viewController.closeDialog());
    }
}
