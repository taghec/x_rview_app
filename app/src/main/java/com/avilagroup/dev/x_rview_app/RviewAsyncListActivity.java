package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewAsyncListBinding;
import com.avilagroup.dev.x_rview_app.util.asyncGetPeople;

public class RviewAsyncListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rview_async_list);
        setTitle(getString(R.string.app_name) + " - RView Async");

//        Log.d("ASYNC",": Starting listBinding");
        final ActivityRviewAsyncListBinding listBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_rview_async_list);
//        Log.d("ASYNC",": Ending listBinding");
//        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        /*
         * Make Async call, sending the binding and layoutManager
         * along for use of adapter for data attachment.
         */
//        new asyncGetPeople(this, listBinding, layoutManager).execute();
        new asyncGetPeople(this, listBinding).execute();

        // Link the FAB button to add new person
        listBinding.fabPersonAdd.setHapticFeedbackEnabled(true);
        listBinding.fabPersonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RviewAsyncListActivity.this,
                        R.string.stg_adding_entry,Toast.LENGTH_SHORT).show();
            }
        });

/*
        listBinding.rvRviewList.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int vX, int vY) {
                Toast.makeText(RviewAsyncListActivity.this,
                        "flinged!", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG FLING: ", "vX = " + vX + ":vY = " + vY);
                return false;
            }
        });
*/
        ItemTouchHelper.SimpleCallback slideCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
/*
            @Override
            public int getMovementFlags(RecyclerView rvView, RecyclerView.ViewHolder vHolder) {
                return 0;
            }
*/

            @Override
            public boolean onMove(RecyclerView rvView, RecyclerView.ViewHolder vHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vHolder, int direction) {
                Toast.makeText(RviewAsyncListActivity.this,
                        "flinged! thr:"+super.getSwipeThreshold(vHolder),
                        Toast.LENGTH_SHORT).show();
                Log.d("DEBUG SWIPE: ", "dir = " + direction);
            }

            @Override
            public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
                float factor = 1.5f;
                return super.getSwipeThreshold(viewHolder)*factor;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(slideCallback);
        itemTouchHelper.attachToRecyclerView(listBinding.rvRviewList);
    }
}
