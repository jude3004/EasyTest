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

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentProfileFragment extends Fragment {
     View objectStudentProfileFragment;
     private BottomNavigationView nav;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private CollectionReference teachersCollection;
    private Button signoutstu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFirebase();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        teachersCollection = db.collection("Student");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectStudentProfileFragment = inflater.inflate(R.layout.fragment_student_profile, container, false);
        attachComponents();
        return objectStudentProfileFragment;
    }

    private void attachComponents() {
        final TextView emaildis = objectStudentProfileFragment.findViewById(R.id.emaildetailstu);
        final TextView iddis = objectStudentProfileFragment.findViewById(R.id.iddetailstu);
        final TextView areaedis = objectStudentProfileFragment.findViewById(R.id.areadetailstu);
        final TextView namedis = objectStudentProfileFragment.findViewById(R.id.namedetailstu);
        signoutstu = objectStudentProfileFragment.findViewById(R.id.signoutbuttonstu);
        String userEmail = currentUser != null ? currentUser.getEmail() : "";
        Query query = teachersCollection.whereEqualTo("Email", userEmail);

        query.get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null && !snapshot.isEmpty()) {
                        Map<String, Object> teacherMap = snapshot.getDocuments().get(0).getData();
                        assert teacherMap != null;
                        final String name = (String) teacherMap.get("Name");
                        final String area = (String) teacherMap.get("Area");
                        final String id = (String) teacherMap.get("ID");
                        namedis.setText(name);
                            areaedis.setText(area);
                        iddis.setText(id);
                   emaildis.setText(userEmail);
                    } else {
                        // No matching teacher found
                        Toast.makeText(requireContext(), "No matching student found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while fetching the teacher object
                    Exception e = task.getException();
                    Toast.makeText(requireContext(), "Error fetching student object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error fetching student object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        signoutstu.setOnClickListener(view -> Signout());
    }

    private void Signout() {
        LogInFragment logInFragment = new LogInFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.studentfragprof, logInFragment, logInFragment.getTag())
                .commit();
        mAuth.signOut();
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
    public void onStart() {
        super.onStart();
    }
    public void onResume() {
        super.onResume();
        nav=requireActivity().findViewById(R.id.bottomNavigationView1);
        nav.setVisibility(View.VISIBLE);
    }
}
