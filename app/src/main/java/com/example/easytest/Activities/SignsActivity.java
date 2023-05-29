package com.example.easytest.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytest.Classes.RecyclerViewAdapter;
import com.example.easytest.Classes.Sign;
import com.example.easytest.Fragments.AddSignFragment;
import com.example.easytest.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class SignsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<Sign, RecyclerViewAdapter.ViewHolder> adapter;
    private CollectionReference collectionRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        attachComponents();

    }

    private void attachComponents() {
        recyclerView = findViewById(R.id.recyclerViewsigns);
        collectionRef = FirebaseFirestore.getInstance().collection("SignImages");

        // Define and set the layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        LoadData();
    }

    private void LoadData() {
        Query query = collectionRef.orderBy("SignName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Sign> options = new FirestoreRecyclerOptions.Builder<Sign>()
                .setQuery(query, Sign.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Sign, RecyclerViewAdapter.ViewHolder>(options) {
            @NonNull
            @Override
            public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
                return new RecyclerViewAdapter.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position, @NonNull Sign model) {
                holder.bind(model);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}

