package com.example.easytest.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytest.Classes.PhotoAdapter;
import com.example.easytest.Fragments.AddSignFragment;
import com.example.easytest.Fragments.Homepagefragment;
import com.example.easytest.Fragments.StudentProfileFragment;
import com.example.easytest.Fragments.TeacherProfileFragment;
import com.example.easytest.R;
import com.example.easytest.UserManagement.ForgotPasswordFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
     String usertype;
    private CollectionReference userCollection;
    private FirebaseFirestore db;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Homepagefragment homepagefragment = new Homepagefragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.homepageactive, homepagefragment, homepagefragment.getTag())
                .commit();
        attachComponents();
    }

    private void attachComponents() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView1);
        db = FirebaseFirestore.getInstance();
        userCollection = db.collection("User");
        if (user != null) {
            String email = user.getEmail();
            Query query = userCollection.whereEqualTo("Email", email);
            query.get().addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();
                        if (snapshot != null && !snapshot.isEmpty()) {
                            Map<String, Object> userMap = snapshot.getDocuments().get(0).getData();
                            assert userMap != null;
                            usertype = (String) userMap.get("Usertype");
                        } else {
                            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Exception e = task.getException();
                        Toast.makeText(this, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    switch (item.getItemId()) {
                        case R.id.profile:
                            if (usertype!=null && usertype.equals("Teacher")) {
                                transaction.replace(R.id.homepageactive, new TeacherProfileFragment());
                            } else if (usertype!=null && usertype.equals("Student")) {
                                transaction.replace(R.id.homepageactive, new StudentProfileFragment());
                            }
                            break;
                        case R.id.home:
                            transaction.replace(R.id.homepageactive, new Homepagefragment());
                            break;
                    }

                    transaction.commit();
                    return true;
                }
            });

        }}


    @Override
    protected void onStart() {
        super.onStart();
    }
}

