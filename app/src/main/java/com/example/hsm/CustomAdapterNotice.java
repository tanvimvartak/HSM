package com.example.hsm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomAdapterNotice extends BaseAdapter {

    private Context context;
    private ArrayList<Notice> noticeArrayList;
    private String calling;

    private DatabaseReference db;

    public CustomAdapterNotice(Context context, ArrayList<Notice> noticeArrayList, String calling) {
        this.context = context;
        this.noticeArrayList = noticeArrayList;
        this.calling = calling;
        this.db = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return noticeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.notice_row, null);

        TextView titleDisplay = row.findViewById(R.id.notice_titleDisplay);
        TextView descDisplay = row.findViewById(R.id.notice_descriptionDisplay);
        ImageView imgDisplay = row.findViewById(R.id.notice_imgDisplay);

        String timestamp = noticeArrayList.get(i).getTimestamp();

        titleDisplay.setText(noticeArrayList.get(i).getTitle());
        descDisplay.setText(noticeArrayList.get(i).getDescription());

        Glide
                .with(context)
                .load(noticeArrayList.get(i).getImgLink())
                .into(imgDisplay);

        Button delBtn = row.findViewById(R.id.notice_delBtn);

        if(!calling.equals("Admin")) {
            delBtn.setVisibility(View.GONE);
        }

        imgDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, ViewImage.class);
                intent.putExtra("imageUrl", noticeArrayList.get(i).getImgLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               db.child("notice").child(timestamp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       noticeArrayList.remove(i);
                       notifyDataSetChanged();
                       Toast.makeText(context, "Notice Deleted", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });

        return row;

    }
}
