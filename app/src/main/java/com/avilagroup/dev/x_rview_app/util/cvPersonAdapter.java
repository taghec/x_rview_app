package com.avilagroup.dev.x_rview_app.util;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avilagroup.dev.x_rview_app.BR;
import com.avilagroup.dev.x_rview_app.PersonBinding;
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
    private Context mContext;

    public cvPersonAdapter(Context context, List<Person> personList) {
        this.personList = personList;
        this.mContext = context;
    }

    @Override
    public CviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cview_list_item, parent, false);
        return new CviewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CviewHolder holder, int position) {
        // don't use position, as it could be out of sync
        final Person person = personList.get(holder.getAdapterPosition());

        // the method getBinding is defined below, and the magic "BR"
        // android binding-resource is used to access the layout vars
        holder.getBinding().setVariable(BR.person, person);
        holder.getBinding().executePendingBindings();

        // click each of the list, response
        holder.getBinding().cvCviewList.setOnClickListener(new View.OnClickListener() {
//            Context context = mContext;
            @Override
            public void onClick(View v) {
                int listLocation = holder.getAdapterPosition();
                Person mPerson = personList.get(listLocation);
                Toast.makeText(mContext,"clicked: "+mPerson.getFirstname(),Toast.LENGTH_SHORT).show();

                startPersonBinding(listLocation);
            }
        });
    }

    private void startPersonBinding(int listLoc) {
        Intent launchPersonBinding = new Intent(mContext, PersonBinding.class);
        launchPersonBinding.putExtra("listLoc", listLoc);
        mContext.startActivity(launchPersonBinding);
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class CviewHolder extends RecyclerView.ViewHolder {
        // This auto-class is avail only if at root, or importing the
        // magic "BR" binding-resource class as above
        private ActivityCviewListItemBinding rviewListItemBinding;

        CviewHolder(View v) {
            super(v);
            rviewListItemBinding = DataBindingUtil.bind(v);
        }
        public ActivityCviewListItemBinding getBinding(){
            return rviewListItemBinding;
        }
    }
}

