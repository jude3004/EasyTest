package com.example.easytest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytest.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
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
private static final int REQUEST_CODE_IMAGE=101;
    private Button upload;
DatabaseReference Dataref;
StorageReference Storageref;
private ProgressBar progressBar;
private TextView textViewProgress;
private ImageView image;
private EditText editText;
Uri imageUri;
boolean isImageAdded=false;

View objectAddSignFragment;
    private void attachComponents()
    {
Dataref= FirebaseDatabase.getInstance().getReference().child("Sign");
        Storageref= FirebaseStorage.getInstance().getReference().child("SignImage");

        upload=objectAddSignFragment.findViewById(R.id.btnUpload);
        progressBar=objectAddSignFragment.findViewById(R.id.progressbar);
        textViewProgress=objectAddSignFragment.findViewById(R.id.textProgress);
        editText=objectAddSignFragment.findViewById(R.id.inputImageName);
        image=objectAddSignFragment.findViewById(R.id.imageVIewAdd);
        progressBar.setVisibility(View.GONE);
        textViewProgress.setVisibility(View.GONE);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,REQUEST_CODE_IMAGE);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String imageName= editText.getText().toString();
            if (isImageAdded!=false&&imageName!=null){
                uploadImage(imageName);
            }

            }

            private void uploadImage(final String imageName) {
textViewProgress.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
              final  String key=Dataref.push().getKey();
                Storageref.child(key+"jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Storageref.child(key+"jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
HashMap hashmap=  new HashMap();
hashmap.put("SignName", imageName);
hashmap.put("ImageUrl",uri.toString());
Dataref.child(key).setValue(hashmap).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
        startActivity(new Intent(getContext(),SignsActivity.class));
        Toast.makeText(getContext(), "Data successfully uploaded!", Toast.LENGTH_SHORT).show();
    }
});
                            }
                        });
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
double progress= (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
progressBar.setProgress((int) progress);
textViewProgress.setText(progress+"%");
                    }
                });
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {

            Uri imageUri = data.getData();


            image.setImageURI(imageUri);


            progressBar.setVisibility(View.VISIBLE);
            textViewProgress.setVisibility(View.VISIBLE);

        }
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



