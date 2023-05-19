package com.example.easytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignsActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    FloatingActionButton floatingbutton;
    FirebaseRecyclerOptions<Sign> options;
    FirebaseRecyclerAdapter<Sign,RecyclerViewAdapter>adapter;
    private RecyclerView.LayoutManager layoutManager;
  DatabaseReference Dataref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        attachComponents();
    }

    private void attachComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        floatingbutton=findViewById(R.id.floatingbtn);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Dataref= FirebaseDatabase.getInstance().getReference().child("Sign");
        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.frameLayoutMain, new AddSignFragment());
                transaction.commit();
            }
        });
        LoadData();
    }

    private void LoadData() {
options= new FirebaseRecyclerOptions.Builder<Sign>().setQuery(Dataref,Sign.class).build();
adapter=new FirebaseRecyclerAdapter<Sign, RecyclerViewAdapter>(options) {
    @Override
    protected void onBindViewHolder(@NonNull RecyclerViewAdapter holder, @SuppressLint("RecyclerView") final int position, @NonNull Sign model) {
        holder.textView.setText(model.getSignName());
        Picasso.get().load(model.getImageUrl()).into(holder.imageView);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewActivity.class);
                intent.putExtra("SignKey", getRef(position).getKey());
                view.getContext().startActivity(intent);
            }
        });
    }


    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        return new RecyclerViewAdapter(v);
    }
};
adapter.startListening();
recyclerView.setAdapter(adapter);
    }


}
