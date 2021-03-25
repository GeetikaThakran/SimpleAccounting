package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Tax_report extends AppCompatActivity implements TaxReportAdapter.OnTaxReportListener {
    List<tax_report_list> t= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tax_report);


        RecyclerView recyclerView = findViewById(R.id.recyclerView_tax_report);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //report list

        t.add(new tax_report_list("GSTR-3B"));
        t.add(new tax_report_list("GSTR-1"));
        t.add(new tax_report_list("GSTR-2"));
        t.add(new tax_report_list("GSTR-4"));
        t.add(new tax_report_list("Tax Computation"));
        t.add(new tax_report_list("Tax Register"));



        TaxReportAdapter taxReportAdapter =new TaxReportAdapter(Tax_report.this,t,this);
        recyclerView.setAdapter(taxReportAdapter);
    }
    @Override
    public void onTaxReportClick(int position) {
        t.get(position);
        if (position ==0) {
            Intent intent = new Intent(this, gstr_3b.class);
            startActivity(intent);
        }
        if (position == 1){
            Intent intent1 = new Intent(this, gstr_1.class);
            startActivity(intent1);
        }
        if (position == 2){
            Intent intent1 = new Intent(this, gstr_2.class);
            startActivity(intent1);
        }
        if (position == 3){
            Intent intent1 = new Intent(this, gstr_4.class);
            startActivity(intent1);
        }
        if (position == 4){
            Intent intent1 = new Intent(this, TaxComputation.class);
            startActivity(intent1);
        }
        if (position == 5){
            Intent intent1 = new Intent(this, TaxRegister.class);
            startActivity(intent1);
        }
    }
}