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
    private String[] messages = {"No Entry", "No Overtaking", "No U-Turn", "One Way", "Turn Right", "Rocks Ahead", "Roundabout Ahead", "Stop Sign", "Warning Triangle", "Road Works"};

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
