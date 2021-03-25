package com.example.simpleaccounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.security.acl.Owner;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddCompanyActivity extends AppCompatActivity {

    private Button buttonAddCompany;
    private DatabaseManager databaseManager;
    private EditText editTextCompanyName;
    private EditText editTextOwnerName;
   // private EditText editTextGstin;
    private EditText editTextAddress;
    private EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);


        buttonAddCompany = findViewById(R.id.button_add_company);
        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        editTextOwnerName = findViewById(R.id.edit_text_owner_name);
        //editTextGstin = findViewById(R.id.edit_text_gstin);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextPhone = findViewById(R.id.edit_text_phone);

        databaseManager = new DatabaseManager(this);

       buttonAddCompany.setOnClickListener((view) ->{
           if(addCompany()) {
               startActivity(new Intent(AddCompanyActivity.this, MainActivity.class));
           }
       });
    }

    private boolean addCompany(){
        String companyName = editTextCompanyName.getText().toString().trim();
        String ownerName = editTextOwnerName.getText().toString().trim();
        //String gstin = editTextGstin.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if(companyName.isEmpty()){
            editTextCompanyName.setError("Company name can't be empty.");
            editTextCompanyName.requestFocus();
            return false;
        }
        if(ownerName.isEmpty()){
            editTextOwnerName.setError("Owner name can't be empty.");
            editTextOwnerName.requestFocus();
            return false;
        }
        if(databaseManager.addCompany(companyName,ownerName,address,phone)){
            Toast.makeText(this,"Company added successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this,"Company not added.",Toast.LENGTH_SHORT).show();
            return  false;
        }
    }
}