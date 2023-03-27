package com.example.easytest;

import static android.content.ContentValues.TAG;
import static com.google.common.reflect.Reflection.initialize;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherSignupFragment extends Fragment {
    private Button signupbutton;
    private FirebaseFirestore db;
    private  View objectteachersignup;

    private EditText name, car_type, experitaion_date, no_students;
    private void attachComponents(){

        signupbutton=objectteachersignup.findViewById(R.id.signupbtn);
        db= FirebaseFirestore.getInstance();

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInFragment logInFragment=new LogInFragment();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frameLayoutMain,logInFragment,logInFragment.getTag())
                        .commit();
                addUserToFirestore();
            }
        });
    }
    public void addUserToFirestore() {
        Map<String, Object> student = new HashMap<>();
        String Name= name.getText().toString();
        String cartype= car_type.getText().toString();
        String expire= experitaion_date.getText().toString();
        String noofstu= no_students.getText().toString();
        student.put("Name", Name);
        student.put("Car type", cartype);
        student.put("expiration date", expire);
        student.put("number of students", noofstu);
        db.collection("Student")
                .add(student)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeachersignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherSignupFragment newInstance(String param1, String param2) {
        TeacherSignupFragment fragment = new TeacherSignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectteachersignup=inflater.inflate(R.layout.fragment_teacher_signup,container,false);
        attachComponents();

        return objectteachersignup;
    }
}