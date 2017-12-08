package com.hotmail.at.jablonski.michal.shoppinglist.ui.details;

import android.content.Intent;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemArchiver;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.rootItems.RootItemDeleter;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemDeleter;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemSelector;
import com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems.ShoppingItemUpdater;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BasePresenter;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.main.listAdapter.RootItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        Observable.just(rootItem)
                .map(item -> new ShoppingItemSelector().select(item))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    this.items = result;
                    getView().refreshList(result);
                });
    }

    public void deleteItem(ShoppingItem item, List<ShoppingItem> shoppingListItems) {
        Observable.just(item)
                .map(shoppingItem -> {
                    ShoppingItemDeleter deleter = new ShoppingItemDeleter();
                    deleter.delete(shoppingItem);
                    return shoppingItem;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    shoppingListItems.remove(result);
                    getView().refreshList(shoppingListItems);
                });
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
        Observable.just(rootItem)
                .map(item -> {
                    RootItemArchiver archiver = new RootItemArchiver();
                    archiver.archive(item);
                    return item;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((item) -> {
                    getView().hideProgressDialog();
                    getView().showSuccessFullyArchivedDialogAndFinish();
                });
    }

    private void deleteRoot() {
        getView().showProgressDialog();
        Observable.just(rootItem)
                .map(item -> {
                    RootItemDeleter deleter = new RootItemDeleter();
                    deleter.delete(item);
                    return item;
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe((item) -> {
                    getView().hideProgressDialog();
                    getView().showSuccessFullyDeletedDialogAndFinish();
                });
    }

    public void onItemChecked(boolean checked, ShoppingItem item) {
        item.setBought(checked);
        for (int i = 0; i < items.size(); i++) {
            if (item.getId() != null && item.getId().equals(items.get(i).getId())) {
                items.set(i, item);
            }
        }

        Observable.just(item)
                .map(item1 -> new ShoppingItemUpdater().update(item1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {
                    //updated async
                });
    }

    public void refreshData() {
        loadDataFromDb();
    }
}
