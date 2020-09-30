package com.yx.opencvdemo.sqlite_room.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yx.opencvdemo.sqlite_room.UserDataSource;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public  class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserDataSource userDataSource;

    public ViewModelFactory(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)){
            return (T) new UserViewModel(userDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
