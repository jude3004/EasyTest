package com.example.easytest;

import static com.google.common.reflect.Reflection.initialize;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentsignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentsignupFragment extends Fragment {
    private FirebaseFirestore db;
    private Button signupbutton;
    private EditText name, birthday, teacher_name, area;
    private RadioGroup radioGroup;


    private View objectstudentsignup;
    private void attachComponents(){
            signupbutton=objectstudentsignup.findViewById(R.id.signupbtn2);
            db=FirebaseFirestore.getInstance();
        name=objectstudentsignup.findViewById(R.id.nameet);
        birthday=objectstudentsignup.findViewById(R.id.birthdayet);
        teacher_name=objectstudentsignup.findViewById(R.id.teacher);
        area=objectstudentsignup.findViewById(R.id.areaet);
        radioGroup = objectstudentsignup.findViewById(R.id.radio_group);
          String Name= name.getText().toString();
        String Birthday= birthday.getText().toString();
        String Teacher= teacher_name.getText().toString();
        String Area= area.getText().toString();
        Map<String, Object> student = new HashMap<>();
        student.put("Name", Name);
        student.put("Birthday", Birthday);
        student.put("Teacher", Teacher);
        student.put("Area", Area);




        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInFragment logInFragment=new LogInFragment();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.frameLayoutMain,logInFragment,logInFragment.getTag())
                        .commit();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_button) {
                    student.put("does he have teyoria?", "yes");
                }
                else if (checkedId == R.id.radio_button_2) {
                    student.put("does he have teyoria?", "no");
                }
            }
        });

        db.collection("Student")
                .add(student);
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentsignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentsignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentsignupFragment newInstance(String param1, String param2) {
        StudentsignupFragment fragment = new StudentsignupFragment();
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
        initialize();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        objectstudentsignup=inflater.inflate(R.layout.fragment_studentsignup,container,false);
        attachComponents();

        return objectstudentsignup;
    }
}