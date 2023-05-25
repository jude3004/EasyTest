package com.example.easytest.Fragments;

import android.os.Bundle;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherProfileFragment extends Fragment {
    private View objectTeacherProfileFragment;
private BottomNavigationView nav;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private CollectionReference teachersCollection;
    private Button signout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFirebase();
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        teachersCollection = db.collection("Teacher");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectTeacherProfileFragment = inflater.inflate(R.layout.fragment_teacher_profile, container, false);
        attachComponents();
        return objectTeacherProfileFragment;
    }

    private void attachComponents() {
        final TextView emaildis = objectTeacherProfileFragment.findViewById(R.id.emaildetail);
        final TextView iddis = objectTeacherProfileFragment.findViewById(R.id.iddetail);
        final TextView cartypedis = objectTeacherProfileFragment.findViewById(R.id.cartypedetail);
        final TextView namedis = objectTeacherProfileFragment.findViewById(R.id.namedetail);
        signout = objectTeacherProfileFragment.findViewById(R.id.signoutbutton);
        String userEmail = currentUser != null ? currentUser.getEmail() : "";
        Query query = teachersCollection.whereEqualTo("Email", userEmail);

        query.get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshot = task.getResult();
                    if (snapshot != null && !snapshot.isEmpty()) {
                        // Retrieve the Teacher object as a HashMap
                        Map<String, Object> teacherMap = snapshot.getDocuments().get(0).getData();
                        assert teacherMap != null;
                        final String name = (String) teacherMap.get("Name");
                        final String carType = (String) teacherMap.get("Cartype");
                        final String id = (String) teacherMap.get("ID");
                            namedis.setText(name);
                            cartypedis.setText(carType);
                            iddis.setText(id);
                            emaildis.setText(userEmail);
                    } else {
                        // No matching teacher found
                        Toast.makeText(requireContext(), "No matching teacher found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while fetching the teacher object
                    Exception e = task.getException();
                    Toast.makeText(requireContext(), "Error fetching teacher object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error fetching teacher object: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        signout.setOnClickListener(view -> Signout());
    }

    private void Signout() {
        mAuth.signOut();
        LogInFragment logInFragment = new LogInFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction()
                .replace(R.id.teacherfragprof, logInFragment, logInFragment.getTag())
                .commit();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     *+ this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentsignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherProfileFragment newInstance(String param1, String param2) {
        TeacherProfileFragment fragment = new TeacherProfileFragment();
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
        // Hide the navigation bar
        nav=requireActivity().findViewById(R.id.bottomNavigationView1);
       nav.setVisibility(View.VISIBLE);
    }
}
