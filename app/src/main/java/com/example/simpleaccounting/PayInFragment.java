package com.example.simpleaccounting;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayInFragment extends Fragment {
    private EditText date;
    View view;
    private ImageButton datebtn;
    private DatePickerDialog datepicker;
    TextInputEditText clientName;
    ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayInFragment newInstance(String param1, String param2) {
        PayInFragment fragment = new PayInFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date = getView().findViewById(R.id.payin_date);
        datebtn=getView().findViewById(R.id.imageButton_payin);
        clientName=getView().findViewById(R.id.payin_clientName);

        clientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientListFragment_payin sf = new clientListFragment_payin();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout_payin,sf);
                transaction.commit();
//                progressDialog.getWindow().setBackgroundDrawableResource(
//                        android.R.color.transparent
//                );
            }
        });

        //set automatic date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(currentDate);

        datebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_pay_in, container, false);
        return view;
    }
}