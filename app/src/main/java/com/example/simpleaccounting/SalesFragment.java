package com.example.simpleaccounting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import me.dm7.barcodescanner.core.IViewFinder;


import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesFragment extends Fragment {
    private static final String TAG = "SalesFragment";
    private static final int PICK_CONTACT =1;
    DatabaseHelperAddSales databaseHelperAddSales;
    EditText sales_invoice;
    EditText sales_date;
    EditText sales_itemName,sales_quantity,sales_price,sales_total,sales_clientName;
    Button sales_addItemButton,sales_addClientButton,sales_addSaleButton;
    FloatingActionButton floatingActionButton;
    public ListView lv;
    DatabaseHelperAddClient databaseHelperAddClient;
    databaseHelperSalesCD daatabaseHelperSalesCD;
    SearchView searchView;
    EditText clientPhoneNo;
    //ImageView clear;
    ArrayList<ClientAllGS> clientList;
    ArrayList<salesCD_gs> clientListt;
    private String phoneNumber;
    Sales_list_adapter myAdapter;
    String name;
    private ImageView speak;
    private final int REQ_CODE = 100;






//    DatabaseHelperAddItem databaseHelperAddItem;
//    private ListView listView;

   View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SalesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SalesFragment newInstance(String param1, String param2) {
        SalesFragment fragment = new SalesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)  {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton = getView().findViewById(R.id.floatingActionButton);
        searchView =getView().findViewById(R.id.searchView);
        lv = (ListView) getView().findViewById(R.id.lv);
        speak=getView().findViewById(R.id.voice);
        databaseHelperAddClient = new DatabaseHelperAddClient(getContext());
        daatabaseHelperSalesCD = new databaseHelperSalesCD(getContext());



//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent,final View view,final int position,final long id) {
//                Intent intent = new Intent(getContext(),sales_credit_debit.class);
//                startActivity(intent);
//            }
//        });


//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> arg0,final View arg1,
//                                           final int pos,final long id) {
//                Toast.makeText(getContext(),"heloooo",Toast.LENGTH_SHORT).show();
//                final int which_item = pos;
//                final ClientAllGS clientt = clientList.get(pos);
//
//                new AlertDialog.Builder(getContext())
//                        .setIcon(R.drawable.call)
//                        .setTitle("Are you sure ?")
//                        .setMessage("Do you want to delete this client")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                long user_id =clientt.getId();
//                                clientList.remove(which_item);
//                                myAdapter.notifyDataSetChanged();
//                                databaseHelperAddClient.deleteClient(user_id);
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//
//                return true;
//            }
//        });





        speak.setOnClickListener(new View.OnClickListener() {
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


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.INVISIBLE);
                AddClientFragment sf = new AddClientFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.mainLayout,sf);
                transaction.commit();

//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);
            }
        });

//
//        sales_invoice = getView().findViewById(R.id.sales_invoiceNo);
//        sales_date = getView().findViewById(R.id.sales_date);
//        sales_itemName = getView().findViewById(R.id.sales_itemName);
//        sales_quantity = getView().findViewById(R.id.sales_quantity);
//        sales_price = getView().findViewById(R.id.sales_price);
//        sales_total = getView().findViewById(R.id.sales_total);
//        //sales_addItemButton = getView().findViewById(R.id.sales_addItemBtn);
//        sales_clientName = getView().findViewById(R.id.sales_clientName);
//        //sales_addClientButton = getView().findViewById(R.id.sales_addClientBtn);
//        sales_addSaleButton = getView().findViewById(R.id.sales_addSaleBtn);
//
//        databaseHelperAddSales = new DatabaseHelperAddSales(getActivity());


        /////
//        Bundle b3 = getArguments();
//        String name=b3.getString("name");
//        sales_itemName.setText(name);

       // listView =getView().findViewById(R.id.item_list_listVieww);
        //databaseHelperAddItem = new DatabaseHelperAddItem(getContext());

        /////

//        sales_addSaleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(AddData()) {
//                    startActivity(new Intent(getActivity(), SalesList.class));
//                }
//            }
//        });
//
//
//
//
//        //set automatic date
//        Calendar calendar = Calendar.getInstance();
//        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
//        sales_date.setText(currentDate);
//

//        sales_itemName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /////
////                NavController navController = Navigation.findNavController(view);
////                navController.navigate(R.id.action_salesFragment_to_sales_additem_dialogboxFragment);
//                /////
//                Intent intent = new Intent(getActivity(),salesListdialog.class);
//                startActivity(intent);
//                /////
//                 // populateListView();
//                //////
//            }
//
//        });
//    }
//
//    public boolean AddData(){
//        String Sales_invoice = sales_invoice.getText().toString().trim();
//        String Sales_date = sales_date.getText().toString().trim();
//        String Sales_itemName = sales_itemName.getText().toString().trim();
//        String Sales_quantity = sales_quantity.getText().toString().trim();
//        String Sales_price = sales_price.getText().toString().trim();
//        String Sales_total = sales_total.getText().toString().trim();
//        String Sales_clientName = sales_clientName.getText().toString().trim();
//
//
//        if(Sales_invoice.isEmpty()){
//            sales_invoice.setError("Invoice Number can't be empty.");
//            sales_invoice.requestFocus();
//            return false;
//        }
//        if(Sales_itemName.isEmpty()){
//            sales_itemName.setError("Item Name can't be empty.");
//            sales_itemName.requestFocus();
//            return false;
//        }
//        if(Sales_quantity.isEmpty()){
//            sales_quantity.setError("Quantity can't be empty.");
//            sales_quantity.requestFocus();
//            return false;
//        }
//        if(Sales_price.isEmpty()){
//            sales_price.setError("Price can't be empty.");
//            sales_price.requestFocus();
//            return false;
//        }
//        if(databaseHelperAddSales.addData(Sales_invoice,Sales_date,Sales_itemName,Sales_quantity,Sales_price,Sales_total,Sales_clientName)){
//            Toast.makeText(getContext(),"Sale added successfully",Toast.LENGTH_SHORT).show();
//            return true;
//        }else{
//            Toast.makeText(getContext(),"Sale not added.",Toast.LENGTH_SHORT).show();
//            return  false;
//        }

    }

    @Override
    public void onStart() {
        super.onStart();
        populateListView();
    }

    private void populateListView() {
        //listView.clear();
        clientList = databaseHelperAddClient.getAllClient();
        clientListt = daatabaseHelperSalesCD.getAllSales();
        myAdapter = new Sales_list_adapter(getContext(), clientList,clientListt);  //,clientListt
        lv.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s.toString());
                return false;

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //Log.d("Sales",requestCode.+"");
            case 1:
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        //Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();

                        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            while (phones.moveToNext()) {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            phones.close();

                        }

                        //            clientPhoneNo.setText(phoneNumber);
//                        edClientName.setText(name);
                    }
                }
                break;

        }
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchView.setQuery((CharSequence) result.get(0), false);
                }
                break;
            }
        }
//    public boolean AddData() {
//        String clientName = name;
//        String firmName = name;
//        String gstin = name;
//        String phone = phoneNumber;
//
//        if (databaseHelperAddClient.addData(clientName, firmName, gstin, phone)) {
//            Toast.makeText(getContext(), "Client added successfully", Toast.LENGTH_SHORT).show();
//            return true;
//        } else {
//            Toast.makeText(getContext(), "Client not added.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//    }


//        ArrayList<String> listData =new ArrayList<>();
//        while (cursor.moveToNext()){
//            // listData.add(cursor.getString(0));
//            listData.add(cursor.getString(1));
//            listData.add(cursor.getString(2));
//            //listData.add(cursor.getString(3));
//            //listData.add(cursor.getString(4));
//
//
//        }
//
//
//        cursor.close();
//
//        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listData);
//        lv.setAdapter(adapter);


//        inputSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // myAdapter.getFilter().filter(s);
//            }
//        });

//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inputSearch.getText().clear();
//
//
//            }
//        });

    }





//    private void populateListView() {
//        //listView.clear();
//        Cursor cursor = databaseHelperAddSales.getAllItems();
//        ArrayList<String> listData =new ArrayList<>();
//        while (cursor.moveToNext()){
//            // listData.add(cursor.getString(0));
//            listData.add(cursor.getString(1));
//            // listData.add(cursor.getString(2));
//            //listData.add(cursor.getString(3));
//            //listData.add(cursor.getString(4));
//
//
//        }
//        cursor.close();
//
//        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listData);
//        listView.setAdapter(adapter);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sales, container, false);
        return view;
    }


}