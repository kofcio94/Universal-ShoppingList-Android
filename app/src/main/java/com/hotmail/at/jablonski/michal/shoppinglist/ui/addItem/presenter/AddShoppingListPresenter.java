package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter;

import android.support.annotation.Nullable;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemInserter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemViewController;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddShoppingListPresenter implements AddItemPresenter<String, Integer> {

    private AddItemViewController viewController;

    @Override
    public void setViewController(AddItemViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void saveItem(String itemName, @Nullable Integer rootId) {
        if (rootId != null) {
            ShoppingItem item = new ShoppingItem();
            item.setBought(false);
            item.setItemName(itemName);
            item.setRootId(rootId);

            Observable.just(item)
                    .map(item1 -> new ShoppingItemInserter().insert(item1))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((result) -> viewController.closeDialog());
        } else
            viewController.showRootIdError();

    }
}
