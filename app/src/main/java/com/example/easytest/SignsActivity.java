package com.example.easytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class SignsActivity extends AppCompatActivity {
private RecyclerView recyclerview;
RecyclerView.LayoutManager layoutManager;
RecyclerViewAdapter recyclerViewAdapter;
int [] arr={R.drawable.noentry,R.drawable.noovertake,R.drawable.nouturn,R.drawable.oneway,R.drawable.right,R.drawable.rocks,R.drawable.roundabout,R.drawable.stop,R.drawable.triangle,R.drawable.working};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        attachcomponents();
    }
    public void attachcomponents()
    {
        recyclerview=findViewById(R.id.recyclerView);
        layoutManager=new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(layoutManager);
        recyclerViewAdapter= new RecyclerViewAdapter(arr);
        recyclerview.setAdapter(recyclerViewAdapter);
        recyclerview.setHasFixedSize(true);
    }
}