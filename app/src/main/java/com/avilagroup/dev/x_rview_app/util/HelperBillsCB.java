package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.BillsAsyncActivity;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.util.List;

/**
 * Created by taghec on 06/10/2016.
 */
public class HelperBillsCB extends ItemTouchHelper.SimpleCallback{
    private static final String SAVE_FILE = "bills.json";
    Context context;
    RecyclerView.Adapter adapter;
    List<BillParsedObs> list;

    HelperBillsCB(BillsAsyncActivity context,
                  RecyclerView.Adapter adapter,
                  List<BillParsedObs> list) {
        super(0,ItemTouchHelper.RIGHT);

        this.context = context;
        this.adapter = adapter;
        this.list = list;
    }

    @Override
    public boolean onMove(RecyclerView rV,
                          RecyclerView.ViewHolder vH,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder vH, int dir) {
        StorageTools storageTools = new StorageTools(context, SAVE_FILE);
        String msg = "swiped: ";
        int pos = vH.getAdapterPosition();
//        BillParsedObs billChanged = list.get(pos);
        msg += "Loc = " + pos;

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
/*
        billChanged.setName(billChanged.getName() + "X");
        list.set(pos, billChanged);
*/
        list.remove(pos);
        storageTools.removeRecord(pos);
//        adapter = new cvBillAdapter(context, list);

        /**
         * Using the correct call here will automatically enable Android's
         * item animation. W out it, shifts of list occur in a haphazard way.
         * Changing from ...DataSetChanged to ...ItemRemoved triggers the slow
         * animation of shifting the list up on removed items. Nor further action
         * required unless a custom animation is required.
         */
//        adapter.notifyDataSetChanged(); // Breaks animations. See: http://stackoverflow.com/questions/29831083/how-to-use-itemanimator-in-a-recyclerview
        adapter.notifyItemRemoved(pos);
//        adapter.notifyItemChanged(pos);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder vH) {
        float factor = 1.5f;
        return super.getSwipeThreshold(vH)*factor;
    }
}
