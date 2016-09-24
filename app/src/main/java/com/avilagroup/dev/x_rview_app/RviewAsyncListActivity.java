package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewAsyncListBinding;
import com.avilagroup.dev.x_rview_app.util.asyncGetPeople;

public class RviewAsyncListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rview_async_list);
        setTitle(getString(R.string.app_name) + " - RView Async");

        final ActivityRviewAsyncListBinding listBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_rview_async_list);
//        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        /*
         * Make Async call, sending the binding and layoutManager
         * along for use of adapter for data attachment.
         */
//        new asyncGetPeople(this, listBinding, layoutManager).execute();
        new asyncGetPeople(this, listBinding).execute();
    }
}
