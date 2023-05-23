package com.example.easytest.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytest.R;
import com.example.easytest.UserManagement.LogInFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfileFragment extends Fragment {
    private View objectStudentProfileFragment;
    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference studentCollectionRef = firestore.collection("Student");
    private CollectionReference userCollectionRef = firestore.collection("User");
    private FirebaseUser currentUser;
    private Button signout;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    public static StudentProfileFragment newInstance() {
        StudentProfileFragment fragment = new StudentProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void attachComponents() {
        final TextView email = objectStudentProfileFragment.findViewById(R.id.emaildetail);
        final TextView id = objectStudentProfileFragment.findViewById(R.id.iddetail);
        final TextView area = objectStudentProfileFragment.findViewById(R.id.areadetail);
        final TextView name = objectStudentProfileFragment.findViewById(R.id.namedetail);
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigationView1);
        bottomNavigationView.setVisibility(View.VISIBLE);
        signout = objectStudentProfileFragment.findViewById(R.id.signoutbtn);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String currentUserEmail = currentUser.getEmail();

            // Query to fetch the user with the current user's email
            Query userQuery = userCollectionRef.whereEqualTo("Email", currentUserEmail);

            userQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Retrieve the user document
                        DocumentSnapshot userDocument = queryDocumentSnapshots.getDocuments().get(0);

                        // Get the email from the user document
                        String userEmail = userDocument.getString("Email");

                        // Query to fetch the teacher with the matching email
                        Query studentQuery = studentCollectionRef.whereEqualTo("Email", userEmail);

                        studentQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Retrieve the teacher document
                                    DocumentSnapshot studentDocument = queryDocumentSnapshots.getDocuments().get(0);

                                    // Get the teacher data
                                    String studentId = studentDocument.getString("ID");
                                    String studentName = studentDocument.getString("Name");
                                    String studentEmail = studentDocument.getString("Email");
                                    String studentArea = studentDocument.getString("Area");

                                    // Update the TextViews with the teacher data
                                    id.setText(studentId);
                                    name.setText(studentName);
                                    email.setText(studentEmail);
                                    area.setText(studentArea);
                                } else {
                                    Toast.makeText(getContext(), "Error fetching teacher data!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error fetching teacher data!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Error fetching user data!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error fetching user data!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // User is not authenticated, handle the case accordingly
            Toast.makeText(getContext(), "User not authenticated!", Toast.LENGTH_SHORT).show();
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call signOut() to sign out the user
                mAuth.signOut();
                LogInFragment logInFragment = new LogInFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.studentfragprof, logInFragment, logInFragment.getTag())
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectStudentProfileFragment = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        attachComponents();
        return objectStudentProfileFragment;
    }
}
