package com.hotmail.at.jablonski.michal.shoppinglist.ui.main.fragments.current;

import com.hotmail.at.jablonski.michal.shoppinglist.ui.base.BasePresenter;

public class CurrentPresenter extends BasePresenter<CurrentFragment> {

    public interface ViewController {
        void initView();
    }

    @Override
    public void onLoad(CurrentFragment view) {
        super.onLoad(view);
        getView().initView();
    }
}
