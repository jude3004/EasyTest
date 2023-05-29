package com.example.easytest.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.easytest.Activities.HomePage;
import com.example.easytest.Activities.StartGame;
import com.example.easytest.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimedQuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimedQuizFragment extends Fragment {
 View objectTimedQuizFragment;
private Button start;
private ImageButton arrow;
private void attachComponents(){
    arrow=objectTimedQuizFragment.findViewById(R.id.imageButtontimedquiz);
    arrow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent mainPageActivityIntent = new Intent(getContext(), HomePage.class);
            startActivity(mainPageActivityIntent);
        }
    });
    requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    start=objectTimedQuizFragment.findViewById(R.id.startbutton);
    start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getContext(), StartGame.class);
            getContext().startActivity(myIntent);
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

    public TimedQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimedQuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimedQuizFragment newInstance(String param1, String param2) {
        TimedQuizFragment fragment = new TimedQuizFragment();
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

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        objectTimedQuizFragment = view;
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView1);
        navBar.setVisibility(View.GONE);
        attachComponents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        objectTimedQuizFragment = inflater.inflate(R.layout.fragment_timed_quiz, container, false);
        return objectTimedQuizFragment;
    }
}