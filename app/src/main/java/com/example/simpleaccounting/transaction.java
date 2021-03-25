package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.Adapter;

import java.util.ArrayList;
import java.util.List;

public class transaction extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_tranaction);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //transaction_list[] transaction = new transaction_list[]{
        List<transaction_list> tl= new ArrayList<transaction_list>();
                tl.add(new transaction_list("Sales"));
                tl.add(new transaction_list("Purchase"));
                tl.add(new transaction_list("Purchase"));
                tl.add(new transaction_list("Sales Returns"));
                tl.add(new transaction_list("Purchase Returns"));
                tl.add(new transaction_list("Pay In"));
                tl.add(new transaction_list("Pay Out"));
                tl.add(new transaction_list("Journal"));
                tl.add(new transaction_list("Credit Note"));
                tl.add(new transaction_list("Debit Note"));
                tl.add(new transaction_list("Material Issued"));
                tl.add(new transaction_list("Material Received"));
                tl.add(new transaction_list("Estimate"));
                tl.add(new transaction_list("Purchase Order"));
                tl.add(new transaction_list("Income"));
                tl.add(new transaction_list("Expense"));



        TransactionAdapter transactionAdapter =new TransactionAdapter(transaction.this,tl);
        recyclerView.setAdapter(transactionAdapter);
    }
}