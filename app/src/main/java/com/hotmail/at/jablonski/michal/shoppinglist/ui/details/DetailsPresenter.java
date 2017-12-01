package com.hotmail.at.jablonski.michal.shoppinglist.ui.details;

import android.content.Intent;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemArchiver;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemDeleter;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemDeleter;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemSelector;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemUpdater;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BasePresenter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

import java.util.List;

public class DetailsPresenter extends BasePresenter<DetailsActivity> {

    private RootItem rootItem;
    private List<ShoppingItem> items;

    public interface ViewController {

        void initView(boolean isArchived);

        void refreshList(List<ShoppingItem> shoppingListItems);

        void showProgressDialog();

        void hideProgressDialog();

        void setToolbarName(String name);

        void showSuccessFullyArchivedDialogAndFinish();

        void showSuccessFullyDeletedDialogAndFinish();

    }

    @Override
    public void onLoad(DetailsActivity view) {
        super.onLoad(view);

        Intent intent = getView().getIntent();
        setUpDataFromIntent(intent);

        getView().initView(rootItem.isArchived());
        loadDataFromDb();
    }

    public Integer getRootId() {
        return rootItem.getId();
    }

    private void setUpDataFromIntent(Intent intent) {
        rootItem = (RootItem) intent.getSerializableExtra(RootItem.class.getSimpleName());
        getView().setToolbarName(rootItem.getName());
    }

    private void loadDataFromDb() {
        ShoppingItemSelector selector = new ShoppingItemSelector(rootItem, items -> {
            this.items = items;
            getView().refreshList(items);
        });
        selector.execute();
    }

    public void deleteItem(ShoppingItem item, List<ShoppingItem> shoppingListItems) {
        shoppingListItems.remove(item);
        ShoppingItemDeleter deleter = new ShoppingItemDeleter(item, () -> {
            //deleted
        });
        deleter.execute();
        getView().refreshList(shoppingListItems);
    }

    public void onArrowBackClicked() {
        getView().onBackPressed();
    }

    public void onArchiveClicked() {
        archiveRoot();
    }

    public void onDeleteClicked() {
        deleteRoot();
    }

    private void archiveRoot() {
        getView().showProgressDialog();
        RootItemArchiver archiver = new RootItemArchiver(rootItem, () -> {
            getView().hideProgressDialog();
            getView().showSuccessFullyArchivedDialogAndFinish();
        });
        archiver.execute();
    }

    private void deleteRoot() {
        getView().showProgressDialog();
        RootItemDeleter deleter = new RootItemDeleter(rootItem, () -> {
            getView().hideProgressDialog();
            getView().showSuccessFullyDeletedDialogAndFinish();
        });
        deleter.execute();
    }

    public void onItemChecked(boolean checked, ShoppingItem item) {
        item.setBought(checked);
        for (int i = 0; i < items.size(); i++) {
            if (item.getId().equals(items.get(i).getId())) {
                items.set(i, item);
            }
        }

        ShoppingItemUpdater updater = new ShoppingItemUpdater(item, () -> {
            //updated in async
        });
        updater.execute();
    }

    public void refreshData() {
        loadDataFromDb();
    }
}
