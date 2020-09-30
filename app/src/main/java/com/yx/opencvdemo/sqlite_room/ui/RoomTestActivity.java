package com.yx.opencvdemo.sqlite_room.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.yx.opencvdemo.sqlite_room.UserInjection;

/**
 * 标题 :
 * 编辑 : 向绍谷
 * 日期 : 2020/9/30
 * 描述 :
 */
public class RoomTestActivity extends AppCompatActivity {


    private UserViewModel userViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory viewModelFactory = UserInjection.provideViewModelFactory(this);
        userViewModel = new ViewModelProvider(this,
                viewModelFactory).get(UserViewModel.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (userViewModel !=null){
            userViewModel.onCleared();
        }
    }
}
