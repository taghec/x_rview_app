package com.avilagroup.dev.x_rview_app.util;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityCviewListItemBinding;
import com.avilagroup.dev.x_rview_app.model.Person;

import java.util.List;

/**
 * Created by taghec on 22/09/2016.
 *
 * HEC - this is essentially exactly the same as the rvPersonAdapter,
 *      named "PersonAdapter" in this project (I didn't clarify the purpose originally)
 *      except for the layout inflater will point to the c-view layout. *
 */

public class cvPersonAdapter
    extends RecyclerView.Adapter<cvPersonAdapter.CviewHolder>{
    private List<Person> personList;

    public cvPersonAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public CviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cview_list_item, parent, false);
        CviewHolder holder = new CviewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CviewHolder holder, int position) {
        final Person person = personList.get(position);

        // the method getBinding is defined below, and the magic "BR"
        // android binding-resource is used to access the layout vars
        holder.getBinding().setVariable(BR.person, person);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class CviewHolder extends RecyclerView.ViewHolder {
        // This auto-class is avail only if at root, or importing the
        // magic "BR" binding-resource class as above
        private ActivityCviewListItemBinding rviewListItemBinding;

        public CviewHolder(View v) {
            super(v);
            rviewListItemBinding = DataBindingUtil.bind(v);
        }
        public ActivityCviewListItemBinding getBinding(){
            return rviewListItemBinding;
        }
    }
}

