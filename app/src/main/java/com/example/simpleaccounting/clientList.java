package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class clientList extends AppCompatActivity {
    DatabaseHelperAddClient databaseHelperAddClient;
   databaseHelperSalesCD daatabaseHelperSalesCD;
    private ListView listView;
    ArrayList<ClientAllGS> arrayList;
    ArrayList<salesCD_gs> arrayListt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        listView = (ListView) findViewById(R.id.client_all_listView);
        databaseHelperAddClient = new DatabaseHelperAddClient(this);
        daatabaseHelperSalesCD = new databaseHelperSalesCD(this);

        populateListView();
    }
    private void populateListView() {
        //listView.clear();
        arrayList = databaseHelperAddClient.getAllClient();
        arrayListt = daatabaseHelperSalesCD.getAllSales();
        Sales_list_adapter myAdapter = new Sales_list_adapter(this,arrayList,arrayListt); //,arrayListt
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }
}