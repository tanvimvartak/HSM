package com.example.hsm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapterResidents extends BaseAdapter {

    public Context context;
    private String callingActivity;

    public CustomAdapterResidents(Context context, ArrayList<ResidentQuery> residents , String callingActivity) {
        this.context = context;
        this.residents = residents;
        this.callingActivity = callingActivity;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    public ArrayList<ResidentQuery> residents;
    private DatabaseReference db;

    @Override
    public int getCount() {
        return residents.size();
    }

    @Override
    public Object getItem(int i) {
        return residents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.residents_row, null);


        TextView nameBox = row.findViewById(R.id.residents_name);
        TextView addressBox = row.findViewById(R.id.residents_address);
        Button remove = row.findViewById(R.id.removeAdmin);


        String uid = residents.get(i).getUid();

        String add = residents.get(i).getRwing() + "/" + residents.get(i).getRflat();

        nameBox.setText(residents.get(i).getRname());
        addressBox.setText(add);

        if (callingActivity.equals("User"))
            remove.setVisibility(View.GONE);


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("users").child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        residents.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "User Removed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return  row;

    }
}
