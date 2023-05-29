package com.example.easytest.Fragments;

import static com.google.common.reflect.Reflection.initialize;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytest.Activities.HomePage;
import com.example.easytest.R;
import com.example.easytest.Activities.SignsActivity;
import com.example.easytest.UserManagement.ForgotPasswordFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class AddSignFragment extends Fragment {
    private static final int REQUEST_CODE_IMAGE = 101;
    private Button uploadButton;
    private FirebaseFirestore firestore;
    private StorageReference storageRef;
    private ProgressBar progressBar;
    private TextView textViewProgress;
    private ImageView imageView;
    private EditText editText;
    private Uri imageUri;
    private boolean isImageAdded = false;
private ImageButton arrows;
     View rootView;



    private void attachcomponents() {
        imageView = rootView.findViewById(R.id.imageVIewAdd);
        editText = rootView.findViewById(R.id.inputImageName);
        uploadButton = rootView.findViewById(R.id.btnUpload);
        progressBar = rootView.findViewById(R.id.progressbar);
        textViewProgress = rootView.findViewById(R.id.textProgress);
arrows=rootView.findViewById(R.id.arrowadd);
arrows.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent mainPageActivityIntent = new Intent(getContext(), HomePage.class);
        startActivity(mainPageActivityIntent);
    }
});
        progressBar.setVisibility(View.GONE);
        textViewProgress.setVisibility(View.GONE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    private void uploadImage() {
        final String imageName = editText.getText().toString().trim();

        if (!isImageAdded || imageName.isEmpty()) {
            Toast.makeText(getContext(), "Please select an image and enter a name.", Toast.LENGTH_SHORT).show();
            return;
        }

        showUploadProgress();

        final String key = firestore.collection("SignImages").document().getId();
        final StorageReference imageRef = storageRef.child(key + ".png");

        UploadTask uploadTask = imageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        saveImageDataToFirestore(key, imageName, uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideUploadProgress();
                        Toast.makeText(getContext(), "Failed to retrieve download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideUploadProgress();
                Toast.makeText(getContext(), "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(getActivity(), new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                updateUploadProgress((int) progress);
            }
        });
    }

    private void saveImageDataToFirestore(String key, String imageName, String imageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("SignName", imageName);
        data.put("ImageUrl", imageUrl);

        firestore.collection("SignImages").document(key)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Data successfully uploaded!", Toast.LENGTH_SHORT).show();
                        resetUploadForm();
                        startActivity(new Intent(getContext(), SignsActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideUploadProgress();
                        Toast.makeText(getContext(), "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showUploadProgress() {
        progressBar.setVisibility(View.VISIBLE);
        textViewProgress.setVisibility(View.VISIBLE);
        uploadButton.setEnabled(false);
    }

    private void hideUploadProgress() {
        progressBar.setVisibility(View.GONE);
        textViewProgress.setVisibility(View.GONE);
        uploadButton.setEnabled(true);
    }

    private void updateUploadProgress(int progress) {
        progressBar.setProgress(progress);
        textViewProgress.setText(progress + "%");
    }

    private void resetUploadForm() {
        editText.setText("");
        isImageAdded = false;
        hideUploadProgress();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            isImageAdded = true;
        }
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddSignFragment() {
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
    // Rest of the code...

    // TODO: Rename and change types and number of parameters
    public static AddSignFragment newInstance(String param1, String param2) {
        AddSignFragment fragment = new AddSignFragment();
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference().child("SignImage");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView1);
        navBar.setVisibility(View.GONE);
        attachcomponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_sign, container, false);
        return rootView;
    }

}

