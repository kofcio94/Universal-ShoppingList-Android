package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter;

import android.support.annotation.Nullable;

import com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.AddItemViewController;

public interface AddItemPresenter<T, K> {

    void setViewController(AddItemViewController viewController);

    void saveItem(T arg1, @Nullable K arg2);
}
