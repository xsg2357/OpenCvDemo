package com.yx.opencvdemo.sqlite_room.persistence;

import com.yx.opencvdemo.sqlite_room.UserDataSource;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public  class LocalDataSource implements UserDataSource {

    private  final UserLatLngDao userLatLngDao;

    public LocalDataSource(UserLatLngDao userLatLngDao) {
        this.userLatLngDao = userLatLngDao;
    }
}
