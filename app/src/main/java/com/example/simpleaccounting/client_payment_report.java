package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class client_payment_report extends AppCompatActivity implements ClientReportAdapter.OnClientReportListener{
    List<client_report_list> t= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_payment_report);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_client_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //report list

        t.add(new client_report_list("Account Aging"));
        t.add(new client_report_list("Outstanding Bills"));
        t.add(new client_report_list("Customer/Supplier list"));
        t.add(new client_report_list("Best Customer/Supplier"));
        t.add(new client_report_list("Purchase Register"));
        t.add(new client_report_list("Interest Calculations"));


        ClientReportAdapter clientReportAdapter = new ClientReportAdapter(client_payment_report.this, t, this);
        recyclerView.setAdapter(clientReportAdapter);
    }

    @Override
    public void onclientReportClick(int position) {

        t.get(position);
        if (position ==0) {
            Intent intent = new Intent(this, AccountAging.class);
            startActivity(intent);
        }
        if (position == 1){
            Intent intent1 = new Intent(this, OutstandingBills.class);
            startActivity(intent1);
        }
        if (position == 2){
            Intent intent1 = new Intent(this, CustomerSupplierList.class);
            startActivity(intent1);
        }
        if (position == 3){
            Intent intent1 = new Intent(this, BestCustomerSupplier.class);
            startActivity(intent1);
        }
        if (position == 4){
            Intent intent1 = new Intent(this,PurchaseRegister.class);
            startActivity(intent1);
        }
        if (position == 5){
            Intent intent1 = new Intent(this, InterestCalculations.class);
            startActivity(intent1);
        }
    }
}