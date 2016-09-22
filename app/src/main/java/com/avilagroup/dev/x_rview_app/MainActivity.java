package com.avilagroup.dev.x_rview_app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avilagroup.dev.x_rview_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        final ActivityMainBinding activityMainBinding = DataBindingUtil
                .setContentView(MainActivity.this, R.layout.activity_main);

        activityMainBinding.btnGreetingChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpString = (String) activityMainBinding.tvMainGreeting.getText();
                if (tmpString == getString(R.string.app_greeting_01)) {
                    activityMainBinding.tvMainGreeting.setText(R.string.app_greeting_02);
                } else
                    activityMainBinding.tvMainGreeting.setText(R.string.app_greeting_01);
            }
        });
        activityMainBinding.btnPersonlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPersonActivity();
            }
        });

        activityMainBinding.btnRvPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRviewListActivity();
            }
        });

        activityMainBinding.btnCvPersons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCviewListActivity();
            }
        });
    }

    private void startCviewListActivity() {
        Intent launchRviewListActivity = new Intent(MainActivity.this, CviewListActivity.class);
        startActivity(launchRviewListActivity);
    }

    private void startRviewListActivity() {
        Intent launchRviewListActivity = new Intent(MainActivity.this, RviewListActivity.class);
        startActivity(launchRviewListActivity);
    }

    private void startPersonActivity() {
        Intent launchPersonActivity = new Intent(MainActivity.this, PersonBinding.class);
        startActivity(launchPersonActivity);
    }
}
