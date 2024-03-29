package com.example.easytest.Fragments;

import static android.content.ContentValues.TAG;
import static com.google.common.reflect.Reflection.initialize;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.easytest.R;
import com.example.easytest.UserManagement.LogInFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentSignupFragment extends Fragment {
    private FirebaseFirestore db;
    private Button signupbutton;
    private EditText name, ID, teacher_name, area;
    private String email;
     RadioGroup radioGroupstu;
    private RadioButton yes,no;
     View objectstudentsignup;
    private SharedPreferences sharedPreferences;

    private void attachComponents(){
        signupbutton=objectstudentsignup.findViewById(R.id.signupbtn2);
        db=FirebaseFirestore.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        name=objectstudentsignup.findViewById(R.id.nameet);
        ID=objectstudentsignup.findViewById(R.id.stuID);
        teacher_name=objectstudentsignup.findViewById(R.id.teacher);
        area=objectstudentsignup.findViewById(R.id.areaet);
        radioGroupstu = objectstudentsignup.findViewById(R.id.Radiogrpstu);
        yes = objectstudentsignup.findViewById(R.id.radioButton1stu);
        no = objectstudentsignup.findViewById(R.id.radioButton2stu);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToFirestore();
                LogInFragment logInFragment=new LogInFragment();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.signupstufrag,logInFragment,logInFragment.getTag())
                        .commit();

            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    public void addUserToFirestore() {

        Map<String, Object> Student = new HashMap<>();
        String Name= name.getText().toString();
        String id= ID.getText().toString();
        String Teacher= teacher_name.getText().toString();
        String Area= area.getText().toString();
        Student.put("Name", Name);
        Student.put("ID", id);
        Student.put("Teacher_Name", Teacher);
        Student.put("Area", Area);
        email = sharedPreferences.getString("email", "");
            Student.put("Email", email);

        if (yes.isChecked())
            Student.put("does the student have teyoria?", "yes");
        if (no.isChecked())
            Student.put("does the student have teyoria?", "no");

        db.collection("Student")
                    .add(Student)
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

    public StudentSignupFragment() {
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
    public static StudentSignupFragment newInstance(String param1, String param2) {
        StudentSignupFragment fragment = new StudentSignupFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectstudentsignup=inflater.inflate(R.layout.fragment_student_signup,container,false);
        attachComponents();

        return objectstudentsignup;
    }
}