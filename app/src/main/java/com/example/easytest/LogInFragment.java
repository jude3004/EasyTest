package com.example.easytest;

import static com.google.common.reflect.Reflection.initialize;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {

    private View objectLogInFragment;
    private Button logInBtn;
    private EditText mailEt, passEt;
    private FirebaseAuth mAuth;
    private TextView logInToSignUpTxt, forgotpasswordtxt;

    private void attachComponents() {
        logInBtn = objectLogInFragment.findViewById(R.id.btnLogIn);
        mailEt = objectLogInFragment.findViewById(R.id.etMailLogIn);
        passEt = objectLogInFragment.findViewById(R.id.etPassLogIn);
        logInToSignUpTxt = objectLogInFragment.findViewById(R.id.txtSignUpLogIn);
        forgotpasswordtxt = objectLogInFragment.findViewById(R.id.txtforgotpasswordlogin);
        mAuth = FirebaseAuth.getInstance();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });

        logInToSignUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frameLayoutMain, signUpFragment, signUpFragment.getTag())
                        .commit();
            }
        });

        forgotpasswordtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordFragment forgotpasswordFragment = new ForgotPasswordFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frameLayoutMain, forgotpasswordFragment, forgotpasswordFragment.getTag())
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

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInPage.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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

    private void logInUser() {

        if (!mailEt.getText().toString().isEmpty() && !passEt.getText().toString().isEmpty()) {
            if (mAuth != null) {
                mAuth.signInWithEmailAndPassword(mailEt.getText().toString(), passEt.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getContext(), "User signed in successfully.", Toast.LENGTH_SHORT).show();
                                Intent mainPageActivityIntent = new Intent(getContext(), HomePage.class);
                                startActivity(mainPageActivityIntent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } else {
            Toast.makeText(getContext(), "Missing fields identified.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectLogInFragment = inflater.inflate(R.layout.fragment_log_in, container, false);
        attachComponents();

        return objectLogInFragment;
    }

}