package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter;

import android.support.annotation.Nullable;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemInserter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemViewController;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

public class AddRootItemsPresenter implements AddItemPresenter<String, Integer> {

    private AddItemViewController viewController;

    @Override
    public void setViewController(AddItemViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void saveItem(String itemName, @Nullable Integer rootId) {
        RootItem item = new RootItem();
        item.setArchived(false);
        item.setLiked(false);
        item.setName(itemName);
        item.setTimestamp(System.currentTimeMillis());

        RootItemInserter inserter = new RootItemInserter(item, id -> viewController.closeDialog());
        inserter.execute();
    }
}
