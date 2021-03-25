package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PurchaseList extends AppCompatActivity {
    DatabaseHelperAddPurchase databaseHelperAddPurchase;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);

        listView =findViewById(R.id.purchase_list_listView);
        databaseHelperAddPurchase = new DatabaseHelperAddPurchase(this);

        populateListView();
    }
    private void populateListView() {
        //listView.clear();
        Cursor cursor = databaseHelperAddPurchase.getAllItems();
        ArrayList<String> listData =new ArrayList<>();
        while (cursor.moveToNext()){
            // listData.add(cursor.getString(0));
            listData.add(cursor.getString(1));
            // listData.add(cursor.getString(2));
            //listData.add(cursor.getString(3));
            //listData.add(cursor.getString(4));


        }
        cursor.close();

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(adapter);

    }
}