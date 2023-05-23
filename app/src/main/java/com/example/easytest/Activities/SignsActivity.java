package com.example.easytest.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class SignsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingbutton;
    FirestoreRecyclerOptions<Sign> options;
    FirestoreRecyclerAdapter<Sign, RecyclerViewAdapter> adapter;
     RecyclerView.LayoutManager layoutManager;
    CollectionReference collectionRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signs);
        attachComponents();
    }

    private void attachComponents() {
        recyclerView = findViewById(R.id.recyclerViewsigns);
        layoutManager = new GridLayoutManager(this, 2);
        floatingbutton = findViewById(R.id.floatingbtn);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        collectionRef = FirebaseFirestore.getInstance().collection("SignImages");

        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.Signlayout, new AddSignFragment());
                transaction.commit();
            }
        });

        LoadData();
    }

    private void LoadData() {
        Query query = collectionRef.orderBy("SignName", Query.Direction.ASCENDING);
        options = new FirestoreRecyclerOptions.Builder<Sign>()
                .setQuery(query, Sign.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Sign, RecyclerViewAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position, @NonNull Sign model) {
                holder.textView.setText(model.getSignName());
                Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            Intent intent = new Intent(view.getContext(), ViewActivity.class);
                            intent.putExtra("SignKey", getSnapshots().getSnapshot(adapterPosition).getId());
                            view.getContext().startActivity(intent);
                        }
                    }
                });
            }


            @NonNull
            @Override
            public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
                return new RecyclerViewAdapter(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
