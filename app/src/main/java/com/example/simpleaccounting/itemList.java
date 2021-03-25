package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class itemList extends AppCompatActivity {
    DatabaseHelperAddItem databaseHelperAddItem;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

//        ImageView backIcon = findViewById(R.id.left_icon);
//        TextView toolbarTv = findViewById(R.id.toolbar_title);

//        backIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
       // toolbarTv.setText("Items List");


        listView =findViewById(R.id.item_list_listView);
        databaseHelperAddItem = new DatabaseHelperAddItem(this);

        populateListView();
    }

    private void populateListView() {
        //listView.clear();
        Cursor cursor = databaseHelperAddItem.getAllItems();
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