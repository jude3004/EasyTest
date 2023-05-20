package com.example.easytest.Fragments;

import android.content.Intent;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepagefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepagefragment extends Fragment {
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
View objectHome;
public void attachcomponents()
{
    recyclerView = objectHome.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    int[] photos = {R.drawable.signsactivityphoto, R.drawable.addsignsfragment, R.drawable.quizphoto, R.drawable.questionphoto};
    adapter = new PhotoAdapter(photos, getActivity());
    recyclerView.setAdapter(adapter);

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homepagefragment() {
        // Required empty public constructor
    }

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
        objectHome=inflater.inflate(R.layout.fragment_homepagefragment,container,false);
        attachcomponents();

        return objectHome;
    }
}