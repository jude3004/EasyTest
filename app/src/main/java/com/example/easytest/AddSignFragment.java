package com.example.easytest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easytest.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddSignFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSignFragment extends Fragment {
    private static final int REQUEST_SELECT_PHOTO = 1;
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private Button select;
    private Button upload;

    private Uri selectedPhotoUri;

View objectAddSignFragment;
    private void attachComponents()
    {
        select=objectAddSignFragment.findViewById(R.id.selectimagebtn);
        upload=objectAddSignFragment.findViewById(R.id.uploadimagebtn);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndSelectPhoto();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhotoToFirestore();
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

    public AddSignFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddSignFragment.
     */
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void checkPermissionAndSelectPhoto() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
        }



    private void selectPhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhotoFromGallery();
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                selectedPhotoUri = data.getData();
                requireContext().getContentResolver().takePersistableUriPermission(
                        selectedPhotoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Toast.makeText(requireContext(), "Photo selected", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void uploadPhotoToFirestore() {
        if (selectedPhotoUri == null) {
            Toast.makeText(requireContext(), "Please select a photo first", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String filename = UUID.randomUUID().toString();

        StorageReference photoRef = storageRef.child("photos/" + filename);

        UploadTask uploadTask = photoRef.putFile(selectedPhotoUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        storeDownloadUrlInFirestore(downloadUri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to upload photo", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireContext(), "Failed to upload photo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void storeDownloadUrlInFirestore(String downloadUrl) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> photoData = new HashMap<>();
        photoData.put("downloadUrl", downloadUrl);

        db.collection("photos")
                .add(photoData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Photo uploaded successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to upload photo", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectAddSignFragment = inflater.inflate(R.layout.fragment_add_sign, container, false);
        attachComponents();

        return objectAddSignFragment;
    }
}



