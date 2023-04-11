package com.example.easytest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static android.content.ContentValues.TAG;
import static com.google.common.reflect.Reflection.initialize;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
private Button log,del;
private TextView usertypeTextView,emailTextView,passwordTextView,usernameTextView;
View objectProfile;
private void attachComponents(){
    log=objectProfile.findViewById(R.id.logout);
    del=objectProfile.findViewById(R.id.delete);
    usertypeTextView=objectProfile.findViewById(R.id.usertypeTextview);
    usernameTextView=objectProfile.findViewById(R.id.usernameTextview);
   emailTextView=objectProfile.findViewById(R.id.emailTextview);
    passwordTextView=objectProfile.findViewById(R.id.passwordTextview);
    log.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                mAuth.signOut();
                LogInFragment logInFragment = new LogInFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayoutMain, logInFragment, logInFragment.getTag())
                        .commit();
            } else {
                // handle case where there is no currently signed-in user
                Toast.makeText(getContext(), "No user is currently signed in", Toast.LENGTH_SHORT).show();
            }

        }
    });
    del.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                currentUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                    Log.d(TAG, "User account deleted.");
                                else
                                    Log.e(TAG, "Error deleting user account.", task.getException());
                            }
                        });
            }

        }
    });
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    if (auth.getCurrentUser() != null) {
        String userId = auth.getCurrentUser().getUid();
        DocumentReference userRef = db.collection("users").document(userId);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String email = document.getString("email");
                        String password = document.getString("password");
                        String username = document.getString("username");
                        String usertype = document.getString("usertype");


                        emailTextView.setText(email);
                        passwordTextView.setText(password);
                        usernameTextView.setText(username);
                        usertypeTextView.setText(usertype);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    } else {
        // handle case where there is no currently signed-in user
        Toast.makeText(getContext(), "No user is currently signed in", Toast.LENGTH_SHORT).show();
    }

}





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        objectProfile=inflater.inflate(R.layout.fragment_profile,container,false);
        attachComponents();

        return objectProfile;
    }
}