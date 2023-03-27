package com.example.easytest;

import static android.content.ContentValues.TAG;
import static com.google.common.reflect.Reflection.initialize;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    View objectSignUpFragment;
    private RadioButton rbstudent, rbteacher;
    private Button signUpBtn;
    private EditText mailEt,passEt,confirmPassEt, etusername;
    private FirebaseAuth mAuth;
    private TextView signUpToLogInTxt;
    private FirebaseFirestore db;
    private RadioGroup radioGroup;
    private boolean worked ;



    private void attachComponents() {
        signUpBtn = objectSignUpFragment.findViewById(R.id.btnSignUp);
        db = FirebaseFirestore.getInstance();
        mailEt = objectSignUpFragment.findViewById(R.id.etEmailSignUp);
        passEt = objectSignUpFragment.findViewById(R.id.etPassSignUp);
        confirmPassEt = objectSignUpFragment.findViewById(R.id.etPassConfirmSignUp);
        signUpToLogInTxt = objectSignUpFragment.findViewById(R.id.signUpToLogInTxt);
        etusername = objectSignUpFragment.findViewById(R.id.etUsername);
        radioGroup = objectSignUpFragment.findViewById(R.id.radio_group);
        rbstudent = objectSignUpFragment.findViewById(R.id.radio_button_2);
        rbteacher = objectSignUpFragment.findViewById(R.id.radio_button_1);
        mAuth = FirebaseAuth.getInstance();
        signUpBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createUser();
                if (rbteacher.isChecked()) {
                    TeacherSignupFragment teachersignupFragment = new TeacherSignupFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayoutMain, teachersignupFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                else if  (rbstudent.isChecked()) {
                    StudentSignupFragment studentsignupFragment = new StudentSignupFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayoutMain, studentsignupFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }


        });
            signUpToLogInTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        LogInFragment logInFragment = new LogInFragment();
                        FragmentManager manager = getFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.frameLayoutMain, logInFragment, logInFragment.getTag())
                                .commit();
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

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void addUserToFirestore() {
        Map<String, Object> user = new HashMap<>();
        String email = mailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();
        String username = etusername.getText().toString().trim();
        user.put("Email", email);
        user.put("Password", password);
        user.put("Username", username);
        if (rbteacher.isChecked())
            user.put("Usertype", "Teacher");

        else if  (rbstudent.isChecked())
            user.put("Usertype", "Student");
        db.collection("user")
                .add(user)
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


    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPasswordValid(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public void createUser()
    {
            if(!mailEt.getText().toString().isEmpty()&&!passEt.getText().toString().isEmpty()&&!confirmPassEt.getText().toString().isEmpty()&&!etusername.getText().toString().isEmpty()&&radioGroup.getCheckedRadioButtonId() != -1)
            {
                if(passEt.getText().toString().equals(confirmPassEt.getText().toString())){
                    mAuth.createUserWithEmailAndPassword(mailEt.getText().toString(),passEt.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    worked=true;
                                    Toast.makeText(getContext(), "Account created.", Toast.LENGTH_SHORT).show();
                                    if(mAuth.getCurrentUser()!=null){
                                        mAuth.signOut();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(worked==true){
                                addUserToFirestore();
                                worked=false;
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getContext(), "Missing fields identified.", Toast.LENGTH_SHORT).show();
            }
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
        objectSignUpFragment=inflater.inflate(R.layout.fragment_sign_up,container,false);
        attachComponents();

        return objectSignUpFragment;
    }
}