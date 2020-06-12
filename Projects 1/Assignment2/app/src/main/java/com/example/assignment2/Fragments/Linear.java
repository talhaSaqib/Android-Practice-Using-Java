package com.example.assignment2.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

import java.util.ArrayList;
import java.util.List;


public class Linear extends Fragment
{

    private static final String TAG = "MyActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> strings = new ArrayList<>();
    private RecyclerView.Adapter mAdapter = new MyAdapter(strings);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linear, container, false);

        try
        {
            recyclerView = view.findViewById(R.id.recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(mAdapter);

            strings.clear();
            fillStrings();
            mAdapter.notifyDataSetChanged();

            Log.i(TAG, "onCreateView()");
        }
        catch (Exception e)
        {
            Log.e(TAG, "onCreateView: Error = " + e);
        }

        return view;
    }

     void fillStrings()
     {
         strings.add("Talha");
         strings.add("Saqib");
         strings.add("Awan");
         strings.add("Town");
         strings.add("Lahore");
         strings.add("Garrison");
         strings.add("Grammar");
         strings.add("School");
         strings.add("How");
         strings.add("Do");
         strings.add("You");
         strings.add("Walk");
         strings.add("Man");
         strings.add("Max");
         strings.add("Bill");
     }

}


 class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    private List<String> strings;
    private final SparseBooleanArray array1 = new SparseBooleanArray();
    private final SparseBooleanArray array2 = new SparseBooleanArray();

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        // Provide a reference to the views for each data item
        public TextView string;
        public CheckBox checkBox;
        public Switch switch1;

        public MyViewHolder(View view)
        {
            super(view);
            string = view.findViewById(R.id.textView99);

            checkBox = view.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(array1.get(getAdapterPosition()))
                        array1.put(getAdapterPosition(), false);
                    else
                        array1.put(getAdapterPosition(), true);
                    notifyDataSetChanged();
                }
            });

            switch1 = view.findViewById(R.id.switch1);
            switch1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(array2.get(getAdapterPosition()))
                        array2.put(getAdapterPosition(), false);
                    else
                        array2.put(getAdapterPosition(), true);
                    notifyDataSetChanged();
                }
            });
        }
    }

    public MyAdapter(List<String> strings)
    {
       this.strings = strings;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_row, parent, false);

        Log.i("TAG", "onCreateViewHolder: " );
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        Log.i("TAG", "onBindViewHolder: " + position);
        try
        {
            String string = strings.get(position);
            holder.string.setText(string);

            if(array1.get(position))
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);

            if(array2.get(position))
                holder.switch1.setChecked(true);
            else
                holder.switch1.setChecked(false);

        }
        catch (Exception e)
        {
            Log.e("TAG", "onBindViewHolder: Error " + e);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return strings.size();
    }
}
