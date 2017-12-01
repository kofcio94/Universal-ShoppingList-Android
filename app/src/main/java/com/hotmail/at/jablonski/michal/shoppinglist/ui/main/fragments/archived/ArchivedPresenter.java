package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.archived;

import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BasePresenter;

public class ArchivedPresenter extends BasePresenter<ArchivedFragment> {

    public interface ViewController {
        void initView();
    }

    @Override
    public void onLoad(ArchivedFragment view) {
        super.onLoad(view);
        getView().initView();
    }
}
