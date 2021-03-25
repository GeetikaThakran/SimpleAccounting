package com.example.simpleaccounting;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {

    private static final String TAG = "AddItemFragment";
    DatabaseHelperAddItem databaseHelperAddItem;
    EditText edItemName;
    EditText edHsnCode;
    EditText edPrice;
    EditText etGST;
    public static EditText barcodeEt;
    Button addItem;
    ImageButton scanBarcodeBtn;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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

        edItemName = getView().findViewById(R.id.add_item_itemName);
        edHsnCode = getView().findViewById(R.id.add_item_hsnCode);
        scanBarcodeBtn = getView().findViewById(R.id.add_item_barcodeButton);
        barcodeEt = getView().findViewById(R.id.add_item_barcode);
        edPrice = getView().findViewById(R.id.add_item_purchasePrice);
        etGST = getView().findViewById(R.id.add_item_gstinP);
        addItem = getView().findViewById(R.id.add_item_Btn);
        databaseHelperAddItem = new DatabaseHelperAddItem(getActivity());

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddData()) {
                    startActivity(new Intent(getActivity(), itemList.class));
                }
            }
        });



        scanBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScanCodeActivity.class);
                startActivity(intent);
            }
        });


    }


    public boolean AddData(){
        String itemName = edItemName.getText().toString().trim();
        String hsnCode = edHsnCode.getText().toString().trim();
        String barcode = barcodeEt.getText().toString().trim();
        String price = edPrice.getText().toString().trim();
        String gst = etGST.getText().toString().trim();

        if(itemName.isEmpty()){
            edItemName.setError("Item name can't be empty.");
            edItemName.requestFocus();
            return false;
        }
//            if(hsnCode.isEmpty()){
//                edHsnCode.setError("HSN Code can't be empty.");
//                edHsnCode.requestFocus();
//                return false;
//            }
        if(databaseHelperAddItem.addData(itemName,hsnCode,barcode,price,gst)){
            Toast.makeText(getContext(),"Item added successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(getContext(),"Item not added.",Toast.LENGTH_SHORT).show();
            return  false;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);

    }
}