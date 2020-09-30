package com.yx.opencvdemo.sqlite_room;

import android.content.Context;

import com.yx.opencvdemo.sqlite_room.persistence.LocalDataSource;
import com.yx.opencvdemo.sqlite_room.persistence.RoomTestDataBase;
import com.yx.opencvdemo.sqlite_room.ui.ViewModelFactory;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public class UserInjection {

    public static UserDataSource provideUserDataSource(Context context) {
        RoomTestDataBase dataBase = RoomTestDataBase.getInstance(context);
        return new LocalDataSource(dataBase.userLatLngDao());
    }


    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }




}
