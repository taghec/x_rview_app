package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.BillsAsyncActivity;
import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncItemBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.util.List;


/**
 * Created by temp on 05/10/2016.
 */
public class cvBillAdapter extends RecyclerView.Adapter<cvBillAdapter.cvHolder> {
    List<BillParsedObs> mBillList;
    private Context mContext;

    public cvBillAdapter(Context context, List<BillParsedObs> listBills) {
        this.mContext = context;
        this.mBillList = listBills;
    }

    /**
     * RV.adapter overrides
     * @param parent
     * @param vType
     * @return
     */
    @Override
    public cvBillAdapter.cvHolder onCreateViewHolder(ViewGroup parent, int vType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_bills_async_item,parent,false);

        return new cvHolder(v);
    }

    @Override
    public void onBindViewHolder(final cvHolder holder, int position) {
        final BillParsedObs bill = mBillList.get(holder.getAdapterPosition());

        holder.getBinding().setVariable(BR.bill, bill);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * RV.A.viewHolder override
     */
    public class cvHolder
            extends RecyclerView.ViewHolder {
        private final ActivityBillsAsyncItemBinding cvItemBinding;

        public cvHolder(View v) {
            super(v);
            cvItemBinding = DataBindingUtil.bind(v);
        }

        public ActivityBillsAsyncItemBinding getBinding() {
            return cvItemBinding;
        }
    }
}
