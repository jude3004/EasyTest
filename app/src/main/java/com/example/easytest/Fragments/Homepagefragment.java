package com.example.easytest.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.easytest.Activities.QuizActivity;
import com.example.easytest.Classes.PhotoAdapter;
import com.example.easytest.R;
import com.example.easytest.Activities.SignsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepagefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepagefragment extends Fragment {
    private RecyclerView recyclerView;
    private BottomNavigationView nav;
    private PhotoAdapter adapter;
    private View objectHome;

private void attatchcomponents(){
    recyclerView = objectHome.findViewById(R.id.recyclerViewhome);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    int[] photos = {R.drawable.signsactivityphoto, R.drawable.addsignsfragment, R.drawable.quizphoto, R.drawable.questionphoto};
    adapter = new PhotoAdapter(photos, requireContext());
    recyclerView.setAdapter(adapter);
}

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homepagefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Homepagefragment newInstance(String param1, String param2) {
        Homepagefragment fragment = new Homepagefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Homepagefragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        objectHome = view;


    }

    @Override
    public void onStart() {
        super.onStart();
        attatchcomponents();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepagefragment, container, false);
    }
    public void onResume() {
        super.onResume();
        // Hide the navigation bar
        nav=requireActivity().findViewById(R.id.bottomNavigationView1);
        nav.setVisibility(View.VISIBLE);
    }

   }