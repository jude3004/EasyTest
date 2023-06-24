package com.example.easytest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easytest.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    ImageView img;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        attachComponents();
    }

    private void attachComponents() {
        img = findViewById(R.id.detailphoto);
        tv = findViewById(R.id.detailtext);
        String imageName = getIntent().getStringExtra("imagename");
        String imageUrl = getIntent().getStringExtra("imageurl");

        TextView textView = findViewById(R.id.detailtext);
        textView.setText(imageName);

        ImageView imageView = findViewById(R.id.detailphoto);
        Picasso.get().load(imageUrl).into(imageView);
    }

}
