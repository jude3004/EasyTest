package com.example.easytest.UserManagement;

import static com.google.common.reflect.Reflection.initialize;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easytest.Activities.HomePage;
import com.example.easytest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPasswordFragment extends Fragment {
  private  EditText etemail;
   private FirebaseAuth mAuth;
   private ImageButton arrow;
   private Button btn;
     View objectForgotPasswordFragment;

    private void attachComponents() {
        arrow=objectForgotPasswordFragment.findViewById(R.id.imageButtonfrgt);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPageActivityIntent = new Intent(getContext(), HomePage.class);
                startActivity(mainPageActivityIntent);
            }
        });
        etemail = objectForgotPasswordFragment.findViewById(R.id.frgtpsswrdmail);
        btn = objectForgotPasswordFragment.findViewById(R.id.btnfrgtpsswrd);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword();
            }
        });
    }

    private void ResetPassword() {
        String mail = etemail.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            etemail.setError("please enter a valid email");
            etemail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Please check your Email to reset password", Toast.LENGTH_SHORT).show();
                    LogInFragment logInFragment = new LogInFragment();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.frameLayoutMain, logInFragment, logInFragment.getTag())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "failed to reset password", Toast.LENGTH_SHORT).show();
                }
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

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        objectForgotPasswordFragment = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        attachComponents();

        return objectForgotPasswordFragment;
    }
}