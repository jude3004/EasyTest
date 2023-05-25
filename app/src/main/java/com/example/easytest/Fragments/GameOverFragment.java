package com.example.easytest.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.easytest.Activities.HomePage;
import com.example.easytest.Activities.StartGame;
import com.example.easytest.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOverFragment extends Fragment {
    private TextView tvpoints, tvpersonalBest;
    SharedPreferences sharedPreferences;
    private Button restart,exit;
    View objectGameOverFragment;
    private void attachComponents() {
        tvpoints = objectGameOverFragment.findViewById(R.id.tvPointsover);
        tvpersonalBest = objectGameOverFragment.findViewById(R.id.tvPersonalBestover);
        restart = objectGameOverFragment.findViewById(R.id.restartbtnover);
        exit = objectGameOverFragment.findViewById(R.id.exitbtnover);

        Bundle arguments = getArguments();
        if (arguments != null) {
            int points = arguments.getInt("points");
            tvpoints.setText(String.valueOf(points));

            sharedPreferences = getActivity().getSharedPreferences("pref", 0);
            int pointsSP = sharedPreferences.getInt("pointsSP", 0);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (points > pointsSP) {
                pointsSP = points;
                editor.putInt("pointsSP", pointsSP);
                editor.apply();
            }

            tvpersonalBest.setText(String.valueOf(pointsSP));
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StartGame.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPageActivityIntent = new Intent(getContext(), HomePage.class);
                startActivity(mainPageActivityIntent);
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

    public GameOverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameOverFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameOverFragment newInstance(String param1, String param2) {
        GameOverFragment fragment = new GameOverFragment();
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
        objectGameOverFragment = inflater.inflate(R.layout.fragment_game_over, container, false);
        attachComponents();

        return objectGameOverFragment;
    }
}