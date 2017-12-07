package com.hotmail.at.jablonski.michal.shoppinglist.db.dataManagers.shoppingItems;

import com.hotmail.at.jablonski.michal.shoppinglist.db.dao.ChildDao;
import com.hotmail.at.jablonski.michal.shoppinglist.ui.details.listAdapter.ShoppingItem;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShoppingItemDeleter {

    private ShoppingItem shoppingItem;
    private ShoppingItemDeleterListener listener;

    public ShoppingItemDeleter(ShoppingItem shoppingItem, ShoppingItemDeleterListener listener) {
        this.shoppingItem = shoppingItem;
        this.listener = listener;
    }

    public void execute() {
        Observable.just(shoppingItem)
                .map((Function<ShoppingItem, Void>) shoppingItem -> {
                    ChildDao dao = new ChildDao();
                    dao.delete(shoppingItem.toChildEntity());
                    return null;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Void value) {
                        listener.deleted();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ShoppingItemDeleterListener {
        void deleted();
    }
}
