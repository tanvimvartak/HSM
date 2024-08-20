package com.example.hsm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapterUserQueries extends BaseAdapter {

    public Context context;
    public ArrayList<Query> queries;
    private DatabaseReference db;

    public CustomAdapterUserQueries(Context context, ArrayList<Query> queries) {
        this.context = context;
        this.queries = queries;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return queries.size();
    }

    @Override
    public Object getItem(int i) {
        return queries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.user_query_row, null);

        TextView queryBox = row.findViewById(R.id.query_text);
        TextView dateBox = row.findViewById(R.id.query_date);
        TextView statusBox = row.findViewById(R.id.query_status);

        String timestamp = queries.get(i).getTimestamp();
        String date = new SimpleDateFormat("dd/MM/yy hh:mm a").format(new Date(Long.parseLong(timestamp) * 1000)).toString();


        queryBox.setText(queries.get(i).getComplaint());
        statusBox.setText(queries.get(i).getStatus());
        dateBox.setText(date);

        return row;

    }
}
