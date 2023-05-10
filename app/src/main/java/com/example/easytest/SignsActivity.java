package com.example.easytest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;

public class SignsActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int[] arr = {R.drawable.noentry, R.drawable.noovertake, R.drawable.nouturn, R.drawable.oneway, R.drawable.right, R.drawable.rocks, R.drawable.roundabout, R.drawable.stop, R.drawable.triangle, R.drawable.working};
    private String[] messages = {"this sign forbids you to enter the road", "you're not allowed to overtake the car ahead", "you cannot do a U-turn here", "this street is a one way, two cars cannot go in the opposite ways", "you only can go right", "beware! some rocks may be falling", "watch out! there is roundabout ahead", "Stop the car! stop and wait for 3 seconds at least", "the priority on the road is for the other cars", "beware! men are working in the road ahead"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        attachComponents();
    }

    private void attachComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(arr, messages, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(int position) {
        String message = messages[position]; //get the message for the clicked item
        showDialog(message); //show the customized alert dialog
    }

    private void showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
