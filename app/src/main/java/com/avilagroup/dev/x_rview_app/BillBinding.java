package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avilagroup.dev.x_rview_app.databinding.ActivityBillBindingBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;
import com.avilagroup.dev.x_rview_app.util.StorageTools;

import java.text.DateFormat;
import java.util.Random;

public class BillBinding extends AppCompatActivity {
    private static final String SAVE_FILE = "bills.json";
    private BillParsedObs bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill_binding);
        final ActivityBillBindingBinding billBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_bill_binding);
        final int listLoc = this.getIntent().getIntExtra("listLoc",-1);
        final int listSize = this.getIntent().getIntExtra("listSize",0);

        /**
         * sanity checks before sending for a rec
         */
        if (listLoc<0) {
            bill = _getBill(listSize);
        } else
        if (listLoc<listSize){
            bill = _getBill(listSize, listLoc);
        }
        billBinding.setBill(bill);
        String expDate = DateFormat.getDateInstance()
                .format(bill.getExpirationDate());
        billBinding.setVariable(BR.stg_exp_date,expDate);
        billBinding.executePendingBindings();

        /**
         * enable/dis rec nav buttons dep on loc sent
         */
        billBinding.ibBillPrev.setEnabled(false);
        billBinding.ibBillNext.setEnabled(false);
/*
        if (listSize>1 && listLoc>=0 && listLoc<listSize) {
            billBinding.ibBillPrev.setEnabled(true);
            billBinding.ibBillNext.setEnabled(true);
        } else if (listLoc+1<listSize) {
            billBinding.ibBillNext.setEnabled(true);
        } else if (listLoc>=0 && listLoc<listSize)
            billBinding.ibBillPrev.setEnabled(true);
*/

        /**
         * activate nav buttons
         */
        billBinding.ibBillPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loc = listLoc-1;
                billBinding.setBill(_getBill(listSize, loc));
                billBinding.executePendingBindings();
            }
        });
        billBinding.ibBillNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loc = listLoc+1;
                billBinding.setBill(_getBill(listSize, loc));
                billBinding.executePendingBindings();
            }
        });
    }

    /**
     * _getBill - will either return a random rec/bill, or a spec bill if
     *          the req is within the range.
     * @return  - the req bill obj
     */
    private BillParsedObs _getBill(int listSize) {
        return _getBill(listSize, new Random().nextInt(listSize-1));
    }

    private BillParsedObs _getBill(int size, int loc) {
        StorageTools storageTools = new StorageTools(this,SAVE_FILE);

        return storageTools.getDevRec(loc);
    }
}
