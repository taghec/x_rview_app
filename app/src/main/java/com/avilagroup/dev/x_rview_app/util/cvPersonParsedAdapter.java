package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.R;
import com.avilagroup.dev.x_rview_app.databinding.ActivityCviewParListItemBinding;
import com.avilagroup.dev.x_rview_app.model.PersonParsed_Obs;

import java.util.List;

/**
 * Created by taghec on 03/10/2016.
 */

public class cvPersonParsedAdapter
        extends RecyclerView.Adapter<cvPersonParsedAdapter.CviewHolder>{
    private List<PersonParsed_Obs> personList;
    private Context mContext;

    // CONSTRUCTOR
    public cvPersonParsedAdapter(Context context, List<PersonParsed_Obs> personList){
        this.mContext = context;
        this.personList = personList;
    }

    @Override
    public CviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cview_par_list_item, parent,false);
        return new CviewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CviewHolder holder, int position) {
        // see notes in first adapter
        final PersonParsed_Obs person = personList.get(holder.getAdapterPosition());

        holder.getBinding().setVariable(BR.person, person);
        holder.getBinding().executePendingBindings();

        // Attach click response below.
        holder.getBinding().cvCviewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonParsed_Obs mPerson = personList.get(holder.getAdapterPosition());
                Toast.makeText(mContext,
                        "clicked: " + mPerson.getFirstname(),
                        Toast.LENGTH_SHORT).show();

                if( mPerson.getGender().equalsIgnoreCase("female"))
                    mPerson.setGender("male");
                else
                    mPerson.setGender("female");
            }
        });

    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class CviewHolder extends RecyclerView.ViewHolder {
        private ActivityCviewParListItemBinding rviewListItemBinding;

        CviewHolder(View v) {
            super(v);
            rviewListItemBinding = DataBindingUtil.bind(v);
        }

        public ActivityCviewParListItemBinding getBinding() {
            return rviewListItemBinding;
        }
    }

    public void itemRemove(int pos){
        personList.remove(pos);
        notifyItemRemoved(pos);
    }
}
