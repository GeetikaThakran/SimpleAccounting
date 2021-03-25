package com.example.simpleaccounting;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sales_additem_dialogboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sales_additem_dialogboxFragment extends Fragment {
    DatabaseHelperAddItem databaseHelperAddItem;
    private ListView listView;
    String s;
    ArrayList<String> listData;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sales_additem_dialogboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sales_additem_dialogboxFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sales_additem_dialogboxFragment newInstance(String param1, String param2) {
        sales_additem_dialogboxFragment fragment = new sales_additem_dialogboxFragment();
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

        listView =getView().findViewById(R.id.dialog_addItem_lv);
        databaseHelperAddItem = new DatabaseHelperAddItem(getContext());

        populateListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                s= listData.get(position);
            }
        });
    }
    private void populateListView() {
        //listView.clear();
        Cursor cursor = databaseHelperAddItem.getAllItems();
        listData =new ArrayList<>();
        while (cursor.moveToNext()){
            // listData.add(cursor.getString(0));
            listData.add(cursor.getString(1));
            // listData.add(cursor.getString(2));
            //listData.add(cursor.getString(3));
            //listData.add(cursor.getString(4));


        }
        cursor.close();

        ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sales_additem_dialogbox, container, false);
        return view;
    }
}