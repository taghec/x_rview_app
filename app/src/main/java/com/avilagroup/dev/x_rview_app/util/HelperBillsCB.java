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
        String msg = "swiped: ";
        int pos = vH.getAdapterPosition();
        msg += "Loc = " + pos;

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        list.remove(pos);
//        adapter = new cvBillAdapter(context, list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder vH) {
        float factor = 1.5f;
        return super.getSwipeThreshold(vH)*factor;
    }
}
