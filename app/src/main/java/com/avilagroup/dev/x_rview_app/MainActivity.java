package com.avilagroup.dev.x_rview_app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avilagroup.dev.x_rview_app.databinding.ActivityMainBinding;
import com.avilagroup.dev.x_rview_app.notes.NotesActivity;

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

        activityMainBinding.btnAsyPersonsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsycnListActivity();
            }
        });

        activityMainBinding.btnAsyPersons2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAsyncList2Act();
            }
        });

        activityMainBinding.btnAsyBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBillsAct();
            }
        });

        activityMainBinding.btnBillsTabAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBillsTabAct();
            }
        });

        activityMainBinding.btnNotesAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotesAct();
            }
        });
    }

    private void startNotesAct() {
        Intent intent = new Intent(MainActivity.this, NotesActivity.class);
        startActivity(intent);
    }

    private void startBillsTabAct() {
        Intent launchBillsTabActivity = new Intent(MainActivity.this, BillsTabActivity.class);
        startActivity(launchBillsTabActivity);
    }

    private void startBillsAct() {
        Intent launchBillsActivity = new Intent(MainActivity.this, BillsAsyncActivity.class);
        startActivity(launchBillsActivity);
    }

    private void startAsyncList2Act() {
        Intent launchAsyncList2Activity = new Intent(MainActivity.this, RviewTwoAsyncListActivity.class);
        startActivity(launchAsyncList2Activity);
    }

    private void startAsycnListActivity() {
        Intent launchAsyncListActivity = new Intent(MainActivity.this, RviewAsyncListActivity.class);
        startActivity(launchAsyncListActivity);
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
