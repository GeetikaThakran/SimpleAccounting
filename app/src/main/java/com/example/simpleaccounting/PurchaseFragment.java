package com.example.simpleaccounting;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PurchaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PurchaseFragment extends Fragment {
//    private static final String TAG = "PurchaseFragment";
//    DatabaseHelperAddPurchase databaseHelperAddPurchase;
//    EditText purchase_invoice;
//    EditText purchase_date;
//    EditText purchase_itemName,purchase_quantity,purchase_price,purchase_total,purchase_clientName;
//    Button purchase_addItemButton,purchase_addClientButton,purchase_addPurchaseButton;

    FloatingActionButton floatingActionButtonSupplier;
    private RecyclerView recyclerView;
    MainAdapterP mainAdapter;
    SearchView searchViewSupplier;
   // public ListView lvSupplier;
    private ImageView speakSupplier;
    private final int REQ_CODE = 100;
    ArrayList<SupplierAllGS> supplierList;//this is used
    ArrayList<purchaseCD_gs> clientListt;
    DatabaseHelperAddSupplier databaseHelperAddSupplier;//this is used
    //Purchase_list_adapter myAdapter;
    databaseHelperPurchaseCD daatabaseHelperPurchaseCD;

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PurchaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PurchaseFragment newInstance(String param1, String param2) {
        PurchaseFragment fragment = new PurchaseFragment();
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

        floatingActionButtonSupplier = getView().findViewById(R.id.floatingActionButtonSupplier);
        searchViewSupplier =getView().findViewById(R.id.searchViewSupplier);
//        lvSupplier = (ListView) getView().findViewById(R.id.lvSupplier);
        recyclerView = getView().findViewById(R.id.list_supplier);
        speakSupplier=getView().findViewById(R.id.voiceSupplier);
        databaseHelperAddSupplier = new DatabaseHelperAddSupplier(getContext());
        supplierList = new ArrayList<>();

        populateListView();
        mainAdapter = new MainAdapterP(getContext(), supplierList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mainAdapter);

        speakSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        floatingActionButtonSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewSupplier.setVisibility(View.INVISIBLE);
                floatingActionButtonSupplier.setVisibility(View.INVISIBLE);
                speakSupplier.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                AddSupplierFragment sf = new AddSupplierFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayoutSupplier,sf);
                transaction.commit();

//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);
            }
        });
//        purchase_invoice = getView().findViewById(R.id.purchase_invoiceNo);
//        purchase_date = getView().findViewById(R.id.purchase_date);
//        purchase_itemName = getView().findViewById(R.id.purchase_itemName);
//        purchase_quantity = getView().findViewById(R.id.purchase_quantity);
//        purchase_price = getView().findViewById(R.id.purchase_price);
//        purchase_total = getView().findViewById(R.id.purchase_total);
//       // purchase_addItemButton = getView().findViewById(R.id.purchase_addItemBtn);
//        purchase_clientName = getView().findViewById(R.id.purchase_clientName);
//       // purchase_addClientButton = getView().findViewById(R.id.purchase_addClientBtn);
//        purchase_addPurchaseButton = getView().findViewById(R.id.purchase_addPurchaseBtn);
//
//        databaseHelperAddPurchase = new DatabaseHelperAddPurchase(getActivity());
//
//        purchase_addPurchaseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(AddData()) {
//                    startActivity(new Intent(getActivity(), PurchaseList.class));
//                }
//            }
//        });
//
//        //set automatic date
//        Calendar calendar = Calendar.getInstance();
//        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//        purchase_date.setText(currentDate);

    }

    private void populateListView() {
        supplierList.clear();
        Cursor cursor = (Cursor) databaseHelperAddSupplier.getAllSupplier();
        if (cursor.moveToFirst()) {
            do {
                supplierList.add(new SupplierAllGS(
                        cursor.getLong(0),
                        cursor.getString(1),//image
                        cursor.getString(2),//name
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)

                ));
                Log.v( "rrr",cursor.getString(1)+"firm");//image
                Log.v( "rrr",cursor.getString(2)+"name");//
                Log.v( "rrr",cursor.getString(3)+"phone");//firm
                Log.v( "rrr",cursor.getString(4)+"image");//email
                Log.v( "rrr",cursor.getString(5)+"email");//phone
            }
            while (cursor.moveToNext());
        }

        cursor.close();
    }
//    public boolean AddData(){
//        String Purchase_invoice = purchase_invoice.getText().toString().trim();
//        String Purchase_date = purchase_date.getText().toString().trim();
//        String Purchase_itemName = purchase_itemName.getText().toString().trim();
//        String Purchase_quantity = purchase_quantity.getText().toString().trim();
//        String Purchase_price = purchase_price.getText().toString().trim();
//        String Purchase_total = purchase_total.getText().toString().trim();
//        String Purchase_clientName = purchase_clientName.getText().toString().trim();
//
//
//        if(Purchase_invoice.isEmpty()){
//            purchase_invoice.setError("Invoice Number can't be empty.");
//            purchase_invoice.requestFocus();
//            return false;
//        }
//        if(Purchase_itemName.isEmpty()){
//            purchase_itemName.setError("Item Name can't be empty.");
//            purchase_itemName.requestFocus();
//            return false;
//        }
//        if(Purchase_quantity.isEmpty()){
//            purchase_quantity.setError("Quantity can't be empty.");
//            purchase_quantity.requestFocus();
//            return false;
//        }
//        if(Purchase_price.isEmpty()){
//            purchase_price.setError("Price can't be empty.");
//            purchase_price.requestFocus();
//            return false;
//        }
//        if(databaseHelperAddPurchase.addData(Purchase_invoice,Purchase_date,Purchase_itemName,Purchase_quantity,Purchase_price,Purchase_total,Purchase_clientName)){
//            Toast.makeText(getContext(),"Purchase added successfully",Toast.LENGTH_SHORT).show();
//            return true;
//        }else{
//            Toast.makeText(getContext(),"Purchase not added.",Toast.LENGTH_SHORT).show();
//            return  false;
//        }
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_purchase, container, false);
        return view;
    }
}