package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class salesListdialog extends AppCompatActivity {
    DatabaseHelperAddClient databaseHelperAddClient;
    databaseHelperSalesCD daatabaseHelperSalesCD;
    private ListView listView;
    ArrayList<ClientAllGS> arrayList;
    ArrayList<salesCD_gs> arrayListt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_listdialog);

        listView = (ListView) findViewById(R.id.sales_listdialog_listView);
        databaseHelperAddClient = new DatabaseHelperAddClient(this);
       daatabaseHelperSalesCD = new databaseHelperSalesCD(this);
       // populateListView();
    }

    private void populateListView() {
        //listView.clear();
        arrayListt = daatabaseHelperSalesCD.getAllSales();
        arrayList = databaseHelperAddClient.getAllClient();
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
        Log.v("xyz",arrayList.size()+"");
        Log.v("xyzz",arrayListt.size()+"");
   //     Sales_list_adapter myAdapter = new Sales_list_adapter(this,arrayList,arrayListt); //,arrayListt
   //     listView.setAdapter(myAdapter);
    //    myAdapter.notifyDataSetChanged();

        }
    }
