package com.avilagroup.dev.x_rview_app.util;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.databinding.ActivityRviewListItemBinding;
import com.avilagroup.dev.x_rview_app.model.Person;

import java.util.List;

/**
 * Created by temp on 22/09/2016.
 */

public class PersonAdapter
    extends RecyclerView.Adapter<PersonAdapter.RviewHolder>{
    private List<Person> personList;

    public PersonAdapter(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_rview_list_item, parent, false);
        RviewHolder holder = new RviewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RviewHolder holder, int position) {
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

    public static class RviewHolder extends RecyclerView.ViewHolder {
        // This auto-class is avail only if at root, or importing the
        // magic "BR" binding-resource class as above
        private ActivityRviewListItemBinding rviewListItemBinding;

        public RviewHolder(View v) {
            super(v);
            rviewListItemBinding = DataBindingUtil.bind(v);
        }
        public ActivityRviewListItemBinding getBinding(){
            return rviewListItemBinding;
        }
    }
}

