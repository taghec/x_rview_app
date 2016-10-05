package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;
import com.avilagroup.dev.x_rview_app.util.cvBillAdapter;

import java.util.ArrayList;
import java.util.List;

public class BillsAsyncActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name + " - Bills");
//        setContentView(R.layout.activity_bills_async);

        final ActivityBillsAsyncBinding mainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_bills_async);
        final RecyclerView.LayoutManager rvLayoutManager = new LinearLayoutManager(this);
        mainBinding.rvBillsAsync.setLayoutManager(rvLayoutManager);

        List<BillParsedObs> listBills = new ArrayList<>();
        final RecyclerView.Adapter rvBillsAdapter = new cvBillAdapter(this, listBills);
    }
}
