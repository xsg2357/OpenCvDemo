package com.yx.opencvdemo.sqlite_room.ui;

import androidx.lifecycle.ViewModel;

import com.yx.opencvdemo.sqlite_room.UserDataSource;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public class UserViewModel extends ViewModel {

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private final UserDataSource mDataSource;


    public UserViewModel(UserDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.dispose();
    }
}
