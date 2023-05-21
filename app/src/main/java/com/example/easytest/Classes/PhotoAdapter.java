package com.example.easytest.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytest.Activities.QuizActivity;
import com.example.easytest.Activities.SignsActivity;
import com.example.easytest.Activities.StartGame;
import com.example.easytest.Fragments.AddSignFragment;
import com.example.easytest.Fragments.TimedQuizFragment;
import com.example.easytest.R;
import com.example.easytest.UserManagement.SignUpFragment;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private int[] photos;
    private Context context;
    private FragmentManager fragmentManager;

    public PhotoAdapter(int[] photos, Context context,FragmentManager fragmentManager) {
        this.photos = photos;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);

        // Calculate the height of each row to occupy half the screen height
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int itemHeight = screenHeight / 2;

        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int photoResId1 = photos[position * 2];
        int photoResId2 = photos[position * 2 + 1];
        holder.imageView1.setImageResource(photoResId1);
        holder.imageView2.setImageResource(photoResId2);

        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedImageIndex = position * 2;

                switch (clickedImageIndex) {
                    case 0:
                        // Handle click event for photo1
                        Intent signsIntent = new Intent(context, SignsActivity.class);
                        context.startActivity(signsIntent);
                        break;
                    case 2:
                        // Handle click event for photo3
                        TimedQuizFragment timedQuizFragment = new TimedQuizFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frameLayouttimedquiz, timedQuizFragment, timedQuizFragment.getTag())
                                .commit();
                        break;
                    default:
                        // For other positions, you can handle the click event as needed
                        break;
                }
            }
        });

        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedImageIndex = position * 2 + 1;

                switch (clickedImageIndex) {
                    case 1:
                        // Handle click event for photo2
                        AddSignFragment addSignFragment = new AddSignFragment();
                        fragmentManager.beginTransaction()
                                .replace(R.id.framelayoutAddsigns, addSignFragment, addSignFragment.getTag())
                                .commit();
                        break;
                    case 3:
                        // Handle click event for photo4
                        Intent questionsIntent = new Intent(context, QuizActivity.class);
                        context.startActivity(questionsIntent);
                        break;
                    default:
                        // For other positions, you can handle the click event as needed
                        break;
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return (int) Math.ceil(photos.length / 2.0);
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView1;
        ImageView imageView2;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.Image1re);
            imageView2 = itemView.findViewById(R.id.Image2re);


            imageView1.setOnClickListener(this);
            imageView2.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
