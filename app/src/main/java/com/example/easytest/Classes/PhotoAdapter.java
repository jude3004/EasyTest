package com.example.easytest.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytest.Activities.QuizActivity;
import com.example.easytest.Activities.SignsActivity;
import com.example.easytest.Activities.StartGame;
import com.example.easytest.Fragments.AddSignFragment;
import com.example.easytest.R;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private int[] photos;
    private Context context;

    public PhotoAdapter(int[] photos, Context context) {
        this.photos = photos;
        this.context = context;
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
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        int photoResId1 = photos[position * 2];
        int photoResId2 = photos[position * 2 + 1];
        holder.imageView1.setImageResource(photoResId1);
        holder.imageView2.setImageResource(photoResId2);
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
            imageView2 = itemView.findViewById(R.id.Image2re
            );

            imageView1.setOnClickListener(this);
            imageView2.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            int clickedImageIndex = position * 2 + view.getId() - R.id.Image1re;

            switch (clickedImageIndex) {
                case 0:
                    // Handle click event for photo1
                    Intent signsIntent = new Intent(context, SignsActivity.class);
                    context.startActivity(signsIntent);
                    break;
                case 1:
                    // Handle click event for photo2
                    Fragment addSignsFragment = new AddSignFragment();
                    // Add code to display the fragment in the desired container
                    break;
                case 2:
                    // Handle click event for photo3
                    Intent timedQuizIntent = new Intent(context, StartGame.class);
                    context.startActivity(timedQuizIntent);
                    break;
                case 3:
                    // Handle click event for photo4
                    Intent questionsIntent = new Intent(context, QuizActivity.class);
                    context.startActivity(questionsIntent);
                    break;
            }
        }
    }
}

