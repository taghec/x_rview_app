package com.avilagroup.dev.x_rview_app;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.avilagroup.dev.x_rview_app.databinding.ActivityBillBindingBinding;
import com.avilagroup.dev.x_rview_app.model.BillParsedObs;
import com.avilagroup.dev.x_rview_app.util.StorageTools;

import java.text.DateFormat;
import java.util.Random;

public class BillBinding extends AppCompatActivity {
    private static final String SAVE_FILE = "bills.json";
    ActivityBillBindingBinding billBinding;
    int listLoc, listSize;
    Menu actMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bill_binding);
        billBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_bill_binding);
        listLoc = this.getIntent().getIntExtra("listLoc",-1);
        listSize = this.getIntent().getIntExtra("listSize",0);

        /**
         * Get bill selected. Sanity checks done on method.
         */
        BillParsedObs bill = _getBill(listSize, listLoc);
        billBinding.setBill(bill);
        String expDate = DateFormat.getDateInstance()
                .format(bill.getExpirationDate());
        billBinding.setVariable(BR.stg_exp_date,expDate);
        billBinding.executePendingBindings();

        /**
         * enable/dis rec nav buttons dep on loc sent
         */
        _activateNAV(billBinding,listLoc,listSize);

        /**
         * activate nav buttons
         */
        billBinding.ibBillPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLoc--;
                billBinding.setBill(_getBill(listSize, listLoc));
                billBinding.executePendingBindings();
                _activateNAV(billBinding,listLoc,listSize);
            }
        });
        billBinding.ibBillNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listLoc++;
                billBinding.setBill(_getBill(listSize, listLoc));
                billBinding.executePendingBindings();
                _activateNAV(billBinding,listLoc,listSize);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actMenu = menu;
        getMenuInflater().inflate(R.menu.menu_bills_binding, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.menu_bill_edit_opt)
            _enableEditing(true);else
        if (id==R.id.menu_bill_save_opt)
            _enableEditing(false);

        return super.onOptionsItemSelected(item);
    }

    /**
     * Override existing this activity.  I need to update the 'edited' item
     * so the RV can be refreshed on exit.
     *
     * See: http://stackoverflow.com/questions/30502515/refresh-recyclerview-from-another-activity
     */
    @Override
    public void finish() {
        Intent updatedIntent = new Intent();
        updatedIntent.putExtra("listLoc",listLoc);
        setResult(RESULT_OK);
        super.finish();
    }

    private void _enableEditing(boolean onEdit) {
        actMenu.findItem(R.id.menu_bill_save_opt).setVisible(onEdit);
        actMenu.findItem(R.id.menu_bill_edit_opt).setVisible(!onEdit);
        billBinding.setVariable(BR.editMode,onEdit);

        if(onEdit) {
            // turn off NAV
            billBinding.ibBillNext.setEnabled(false);
            billBinding.ibBillPrev.setEnabled(false);
        } else {
            _activateNAV(billBinding, listLoc, listSize);
            _recModified();
        }
    }

    private boolean _recModified() {
        String name = billBinding.etBillname.getText().toString();
        if (name.length()>0) {
            StorageTools storageTools = new StorageTools(this, SAVE_FILE);
            BillParsedObs bill = _getBill(listSize, listLoc);
            bill.setName(name);
            billBinding.setBill(bill);
            billBinding.executePendingBindings();
            storageTools.updateRecord(bill,listLoc);
        }

        billBinding.etBillname.setText("");

        // exit after save
        finish();
        return false;
    }

    private void _activateNAV(ActivityBillBindingBinding binding, int loc, int size) {
        boolean btnP = false;
        boolean btnN = false;
        if (size>0 && (loc+1<=size || loc>=0)) {
            btnP = btnN = true;
            if (loc<=0)
                btnP = false;
            if (loc+1>=size)
                btnN = false;
        }
/*
        if (listSize>1 && listLoc>=0 && listLoc<listSize) {
            billBinding.ibBillPrev.setEnabled(true);
            billBinding.ibBillNext.setEnabled(true);
        } else if (listLoc+1<listSize) {
            billBinding.ibBillNext.setEnabled(true);
        } else if (listLoc>=0 && listLoc<listSize)
            billBinding.ibBillPrev.setEnabled(true);
*/
        binding.ibBillPrev.setEnabled(btnP);
        binding.ibBillNext.setEnabled(btnN);
    }

    /**
     * _getBill - will either return a random rec/bill, or a spec bill if
     *          the req is within the range.
     * @return  - the req bill obj
     */
    private BillParsedObs _getBill(int size, int listLoc) {
        StorageTools storageTools = new StorageTools(this,SAVE_FILE);
        int loc = listLoc<0 && size>0 ? new Random().nextInt(size-1) : listLoc;

        return storageTools.getDevRec(loc);
    }
}
