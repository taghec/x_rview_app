package com.avilagroup.dev.x_rview_app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewListBinding;
import com.avilagroup.dev.x_rview_app.model.Person;
import com.avilagroup.dev.x_rview_app.util.PersonUtil;
import com.avilagroup.dev.x_rview_app.util.cvPersonAdapter;

import java.util.List;

public class CviewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cview_list);
        setTitle(getString(R.string.app_name) + " - CView List");
        // the bind name below is essentially a disguise, since the cv
        // layout does not really exist. I'll use the already defined
        // RviewList layout resource.
        final ActivityRviewListBinding cviewListBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_rview_list);

        /**
         * Notice this will be exactly the same as the RviewList act
         * excpet that the adapter used for the display of the individual
         * rows - it will reference the cv version of the adapter.
         *
         * See comments on PersonBinding act regarding data/util
         * instance call.
         */
        final PersonUtil personUtil = new PersonUtil();
        List<Person> persons = personUtil.getPersons();

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cviewListBinding.rvRviewList.setLayoutManager(layoutManager);

        final RecyclerView.Adapter cvPersonAdapter = new cvPersonAdapter(persons);
        cviewListBinding.rvRviewList.setAdapter(cvPersonAdapter);
    }
}
