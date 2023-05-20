package com.example.easytest.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easytest.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {
 ImageView imageView;
 TextView textView;
 Button btn;
    DocumentReference documentRef;

 StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        attachComponents();
    }

    private void attachComponents() {
        imageView = findViewById(R.id.viewimageView);
        textView = findViewById(R.id.viewtextView);
        btn = findViewById(R.id.btnDelete);

        String signKey = getIntent().getStringExtra("SignKey");

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference signCollectionRef = firestore.collection("Sign");
        documentRef = signCollectionRef.document(signKey);
        storageRef = FirebaseStorage.getInstance().getReference().child("SignImage").child(signKey + ".jpg");

        documentRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String signName = documentSnapshot.getString("SignName");
                    String imageUrl = documentSnapshot.getString("ImageUrl");

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Load image using your preferred image loading library (e.g., Picasso, Glide, etc.)
                        Picasso.get().load(imageUrl).into(imageView);
                    }

                    textView.setText(signName);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle the failure case
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(getApplicationContext(), SignsActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle the failure case
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure case
                    }
                });
            }
        });
    }}