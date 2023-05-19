package com.example.easytest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {
private ImageView imageView;
 TextView textView;
 Button btn;
 DatabaseReference ref,DataRef;
 StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        attatchcomponents();
    }

    private void attatchcomponents() {
        imageView=findViewById(R.id.viewimageView);
        textView=findViewById(R.id.viewtextView);
        btn=findViewById(R.id.btnDelete);
        String SignKey=getIntent().getStringExtra("SignKey");
        DataRef=FirebaseDatabase.getInstance().getReference().child("Sign").child(SignKey);
        storageRef= FirebaseStorage.getInstance().getReference().child("SignImage").child(SignKey+".jpg");
        ref= FirebaseDatabase.getInstance().getReference().child("Sign");
        ref.child(SignKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String SignName=dataSnapshot.child("SignName").getValue().toString();
                    String ImageUrl=dataSnapshot.child("ImageUrl").getValue().toString();
                    Picasso.get().load(ImageUrl).into(imageView);
                    textView.setText(SignName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
        storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(getApplicationContext(),SignsActivity.class));
            }
        });
    }
});
            }
        });
    }
}