package com.sun.eventbus_mode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import eventbu.sun.com.library.EventBus;
import eventbu.sun.com.library.anotation.Subscribe;
import eventbu.sun.com.library.mode.ThreadMode;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().post(new EventBean("msg"));
    }
}
