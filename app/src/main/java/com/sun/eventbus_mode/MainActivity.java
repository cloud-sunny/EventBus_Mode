package com.sun.eventbus_mode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import eventbu.sun.com.library.EventBus;
import eventbu.sun.com.library.anotation.Subscribe;
import eventbu.sun.com.library.mode.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        findViewById(R.id.txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(EventBean eventBean){
        Log.e("TAg","getMessage1");
        Toast.makeText(MainActivity.this,eventBean.getMsg(),Toast.LENGTH_SHORT).show();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage2(EventBean eventBean){
        Log.e("TAg","getMessage2");
        Toast.makeText(MainActivity.this,eventBean.getMsg(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
