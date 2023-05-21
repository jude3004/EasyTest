package com.example.easytest.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        attachComponents();
        checkUserType();
    }

    private void checkUserType() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = null;
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference userRef = firestore.collection("User").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String userType = documentSnapshot.getString("Usertype");
                    if (userType != null) {
                        if (userType.equals("Teacher")) {
                            flag = true;
                        } else if (userType.equals("Student")) {
                            flag = false;
                        }
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Missing fields identified.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attachComponents() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView1);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int[] photos = {R.drawable.signsactivityphoto, R.drawable.addsignsfragment, R.drawable.quizphoto, R.drawable.questionphoto};
        adapter = new PhotoAdapter(photos, this);
        recyclerView.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.profile:
                        if (flag) {
                            transaction.replace(R.id.homepageactive, new TeacherProfileFragment());
                        } else {
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
    }
}
