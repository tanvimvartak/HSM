package com.example.hsm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class CustomAdapterMaintenance extends BaseAdapter {
    public Context context;
    public ArrayList<MaintenanceQuery> maintenanceQueries;
    private DatabaseReference db;
    private String callingActivity;


    public CustomAdapterMaintenance(Context context, ArrayList<MaintenanceQuery> maintenanceQueries, String callingActivity) {
        this.context = context;
        this.maintenanceQueries = maintenanceQueries;
        this.db = FirebaseDatabase.getInstance().getReference();
        this.callingActivity = callingActivity;
    }





    @Override
    public int getCount() {
        return maintenanceQueries.size();
    }

    @Override
    public Object getItem(int i) {
        return maintenanceQueries.get(i) ;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.admin_maintenance_row, null);

        TextView detailsBox = row.findViewById(R.id.maintenance_user_details);
        TextView emailBox = row.findViewById(R.id.maintenance_email);
        TextView maintenanceBox = row.findViewById(R.id.maintenance_maintenance);
        TextView sinkingfundBox = row.findViewById(R.id.maintenance_sinkingfund);
        TextView chargesBox = row.findViewById(R.id.maintenance_charges);

        Button remove =row.findViewById(R.id.maintenance_removeBtn);
        Button edit = row.findViewById(R.id.maintenance_editBtn);




        String uid = maintenanceQueries.get(i).getUid();

        String details = maintenanceQueries.get(i).getM_wing() + "/" + maintenanceQueries.get(i).getM_flat() + " - " +maintenanceQueries.get(i).getM_name() + "        " + maintenanceQueries.get(i).getMq();
        detailsBox.setText(details);

        emailBox.setText(maintenanceQueries.get(i).getM_email());
        maintenanceBox.setText(maintenanceQueries.get(i).getM_maintenance());
        sinkingfundBox.setText(maintenanceQueries.get(i).getM_sinkingfund());
        chargesBox.setText(maintenanceQueries.get(i).getM_charges());

        if (callingActivity.equals("User")){
            remove.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Maintenance").child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        maintenanceQueries.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Maintenance Removed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MaintenanceAdmin.class);
                intent.putExtra("calling", "editBtn");
                intent.putExtra("caseObject", maintenanceQueries.get(i));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });






        return row;

    }
}
