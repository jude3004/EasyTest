package com.example.easytest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homepagefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homepagefragment extends Fragment {
private Button signs,questions, quiz;
View objectHome;
public void attachcomponents()
{
    signs=objectHome.findViewById(R.id.btnsigns);
    questions=objectHome.findViewById(R.id.btnquestions);
    quiz=objectHome.findViewById(R.id.quizbtn);
    signs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getContext(), SignsActivity.class);
            getContext().startActivity(myIntent);

        }
    });
    quiz.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getContext(), TimedQuizActivity.class);
            getContext().startActivity(myIntent);

        }
    });
    questions.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent myIntent = new Intent(getContext(), QuizActivity.class);
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