package com.example.easytest.Classes;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easytest.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

 public class RecyclerViewAdapter extends RecyclerView.ViewHolder {
     public ImageView imageView;
     public TextView textView;
     public View v;
     public RecyclerViewAdapter(@NonNull View itemView) {
super(itemView);
imageView=itemView.findViewById(R.id.reimageView);
         textView=itemView.findViewById(R.id.retextView);
         v=itemView;
     }

}
