package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class report extends AppCompatActivity implements ReportAdapter.OnReportListener {
    List<report_list> t= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //report list

        t.add(new report_list("Invoice/Expense Report"));
        t.add(new report_list("Client/Payment Report"));
        t.add(new report_list("Accounting Report"));
        t.add(new report_list("Tax Report"));



        ReportAdapter reportAdapter =new ReportAdapter(report.this,t,this);
        recyclerView.setAdapter(reportAdapter);
    }

    @Override
    public void onReportClick(int position) {
        t.get(position);
        if (position ==0) {
            Intent intent = new Intent(this, Invoice_ExpenseReport.class);
            startActivity(intent);
        }
        if (position == 1){
            Intent intent1 = new Intent(this, client_payment_report.class);
            startActivity(intent1);
        }
        if (position == 2){
            Intent intent1 = new Intent(this, Accounting_report.class);
            startActivity(intent1);
        }
        if (position == 3){
            Intent intent1 = new Intent(this, Tax_report.class);
            startActivity(intent1);
        }
    }
}