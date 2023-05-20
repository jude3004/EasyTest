package com.example.easytest.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.easytest.Fragments.Homepagefragment;
import com.example.easytest.Fragments.StudentProfileFragment;
import com.example.easytest.Fragments.TeacherProfileFragment;
import com.example.easytest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.FrameLayout, new Homepagefragment());
        transaction.commit();
        attachComponents();
    }

    private void attachComponents() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView1);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.profile:
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = null;
                        if (firebaseUser != null) {
                            userId = firebaseUser.getUid();
                            // Use the userId as needed
                        }

                        // Fetch the user data from Firebase and retrieve the Usertype field
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    HashMap<String, Object> userMap = (HashMap<String, Object>) dataSnapshot.getValue();
                                    if (userMap != null) {
                                        String userType = (String) userMap.get("Usertype");
                                        if (userType != null) {
                                            if (userType.equals("Teacher")) {
                                                // User is a teacher, navigate to TeacherProfileFragment
                                                transaction.replace(R.id.frameLayoutMain, new TeacherProfileFragment());
                                            } else if (userType.equals("Student")) {
                                                // User is a student, navigate to StudentProfileFragment
                                                transaction.replace(R.id.frameLayoutMain, new StudentProfileFragment());
                                            }
                                            transaction.commit();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle database error
                            }
                        });
                        return true;
                    case R.id.home:
                        transaction.replace(R.id.FrameLayout, new Homepagefragment());
                        break;
                }

                transaction.commit();
                return true;
            }
        });

    }
}
