package com.example.easytest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import static android.content.ContentValues.TAG;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
    del.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            LogInFragment logInFragment = new LogInFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.frameLayoutMain, logInFragment, logInFragment.getTag())
                    .commit();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null)
                mAuth.signOut();

        }
    });
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    String userId = auth.getCurrentUser().getUid();

    db.collection("users").document(userId).get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        HashMap<String, Object> userMap = (HashMap<String, Object>) documentSnapshot.getData();
                        String email = (String) userMap.get("email");
                        String password = (String) userMap.get("password");
                        String username = (String) userMap.get("username");
                        String usertype = (String) userMap.get("usertype");
                        emailTextView.setText(email);
                        passwordTextView.setText(password);
                        usernameTextView.setText(username);
                        usertypeTextView.setText(usertype);
                        // use the userMap object as needed
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "Error getting document: ", e);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectProfile=inflater.inflate(R.layout.fragment_profile,container,false);
        attachComponents();

        return objectProfile;
    }
}