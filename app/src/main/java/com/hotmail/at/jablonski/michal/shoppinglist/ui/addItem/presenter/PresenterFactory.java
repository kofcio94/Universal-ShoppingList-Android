package com.hotmail.at.jablonski.michal.shoppinglist.ui.addItem.presenter;

import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.DetailsActivity;

public class PresenterFactory {

    public static AddItemPresenter getGenericPresenter(String parentDialogClassName) {

        AddItemPresenter presenter;
        if (DetailsActivity.class.getSimpleName().equals(parentDialogClassName)) {
            presenter = new AddShoppingListPresenter();
        } else {
            presenter = new AddRootItemsPresenter();
        }

        return presenter;
    }
}
