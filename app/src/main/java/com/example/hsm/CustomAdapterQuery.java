package com.example.hsm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapterQuery extends BaseAdapter {

    public Context context;
    public ArrayList<Query>queries;
    private DatabaseReference db;

    public CustomAdapterQuery(Context context, ArrayList<Query> queries) {
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
        View row = layoutInflater.inflate(R.layout.queries_row, null);

        TextView nameBox = row.findViewById(R.id.query_name);
        TextView phoneBox = row.findViewById(R.id.query_phone);
        TextView flatBox = row.findViewById(R.id.query_flat);
        TextView queryBox = row.findViewById(R.id.query_text);
        TextView dateBox = row.findViewById(R.id.query_date);
        CheckBox checkBox = row.findViewById(R.id.checkBox);

        String uid = queries.get(i).getUid();

        String timestamp = queries.get(i).getTimestamp();
        String date = new SimpleDateFormat("dd/MM/yy hh:mm a").format(new Date(Long.parseLong(timestamp) * 1000)).toString();

        nameBox.setText(queries.get(i).getName());
        phoneBox.setText(queries.get(i).getPhone());
        flatBox.setText(queries.get(i).getFlat());
        queryBox.setText(queries.get(i).getComplaint());
        dateBox.setText(date);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.child("queries").child(uid).child(timestamp).child("status").setValue("solved").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {

                            Toast.makeText(context, "Query Solved!", Toast.LENGTH_SHORT).show();
                            queries.remove(i);
                            notifyDataSetChanged();

                        }

                    }
                });

            }
        });

        return row;


    }
}
