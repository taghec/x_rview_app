package com.avilagroup.dev.x_rview_app.util;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.BillBinding;
import com.avilagroup.dev.x_rview_app.BillsAsyncActivity;
import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncBinding;
import com.avilagroup.dev.x_rview_app.databinding.ActivityBillsAsyncItemBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;

import java.util.List;
import java.text.DateFormat;


/**
 * Created by taghec on 05/10/2016.
 */
public class cvBillAdapter
        extends RecyclerView.Adapter<cvBillAdapter.cvHolder> {
    private List<BillParsedObs> mBillList;
    private Context mContext;
    private SelectionCallBack selectionCallBack;
    Activity activity;

    public cvBillAdapter(Context context, List<BillParsedObs> listBills) {
        this.mContext = context;
        this.mBillList = listBills;
        activity = (BillsAsyncActivity) context;

        selectionCallBack = (SelectionCallBack) context;
    }

    public interface SelectionCallBack {
        /**
         * CB interf for when an item is selected
         *
         * See: http://stackoverflow.com/questions/34340703/super-onactivityresult-inside-adapter-class-android
         * @param pos
         * @param msg
         */
        void itemSelection(int pos, int size, String msg);
    }

    /**
     * RV.adapter overrides
     * @param parent
     * @param vType
     * @return
     */
    @Override
    public cvHolder onCreateViewHolder(ViewGroup parent, int vType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_bills_async_item,parent,false);

        return new cvHolder(v);
    }

    @Override
    public void onBindViewHolder(final cvHolder holder, final int position) {
        final BillParsedObs bill = mBillList.get(position);
        final ActivityBillsAsyncItemBinding mBinding = holder.getBinding();
        // Date is saved as long. Change format before setting on field.
        String stgDateExp = DateFormat.getDateInstance().format(bill.getExpirationDate());

        mBinding.setVariable(BR.bill, bill);
        mBinding.setVariable(BR.stg_exp_date, stgDateExp);
        mBinding.executePendingBindings();

        /**
         * Set up a call to edit items on the list.  I'll take the pos on
         * the adapter, and send the bill there to a new activity to allow editing.
         */
        mBinding.cvBillItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                Toast.makeText(mContext, "bill selected: " + pos, Toast.LENGTH_SHORT).show();

//                startBillBindingActivity(pos);
                selectionCallBack.itemSelection(pos, mBillList.size(), "item selected");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBillList.size();
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

    /**
     * Custom methods
     */
    public void remove(int pos){
        mBillList.remove(pos);
        notifyItemRemoved(pos);
    }
}
