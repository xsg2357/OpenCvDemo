package com.yx.opencvdemo.sqlite_room.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
@Database(entities = {UserLatLng.class}, version = 1)
public abstract class RoomTestDataBase extends RoomDatabase {

    private static volatile RoomTestDataBase INSTANCE;

    public abstract UserLatLngDao userLatLngDao();


    public static RoomTestDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RoomTestDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomTestDataBase.class, "DataTest.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
