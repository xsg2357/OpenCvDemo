package com.yx.opencvdemo.sqlite_room.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
@Entity(tableName = "user_latlng")
public class UserLatLng {


    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "userid")
    private  int userId;

}
