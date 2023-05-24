package com.example.easytest.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfileFragment extends Fragment {
     View objectStudentProfileFragment;
    private FirebaseAuth mAuth;
    private BottomNavigationView bottomNavigationView;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference studentCollectionRef = firestore.collection("Student");
    private CollectionReference userCollectionRef = firestore.collection("User");
    private FirebaseUser currentUser;
    private Button signout;
    private void attachComponents() {
        final TextView email = objectStudentProfileFragment.findViewById(R.id.emaildetail);
        final TextView id = objectStudentProfileFragment.findViewById(R.id.iddetail);
        final TextView area = objectStudentProfileFragment.findViewById(R.id.cartypedetail);
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

                        studentQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!documentSnapshots.isEmpty()) {
                                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                        String studentId = doc.getDocument().getString("ID");
                                        String studentName = doc.getDocument().getString("Name");
                                        String studentEmail = doc.getDocument().getString("Email");
                                        String studentarea = doc.getDocument().getString("Area");

                                        // Update the TextViews with the teacher data
                                        id.setText(studentId);
                                        name.setText(studentName);
                                        email.setText(studentEmail);
                                        area.setText(studentarea);
                                    }
                                } else
                                    Toast.makeText(getContext(), "Error fetching teacher data!", Toast.LENGTH_SHORT).show();

                            }
                        });
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
                Signout();


            }
        });
    }
    private void Signout(){
        mAuth.signOut();
        LogInFragment logInFragment = new LogInFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.studentfragprof, logInFragment, logInFragment.getTag())
                .commit();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentsignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectStudentProfileFragment = inflater.inflate(R.layout.fragment_student_profile, container, false);
        attachComponents();
        return objectStudentProfileFragment;
    }
}
