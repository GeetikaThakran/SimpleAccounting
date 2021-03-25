package com.example.simpleaccounting;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton buttonAdd;
    private RecyclerView recyclerView;
    private List<Company> companyList;
    private DatabaseManager databaseManager;
    private CompanyAdapter adapter;
    private ImageView imageViewNoCompany;
    private TextView press_company,update_company,delete_company;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonAdd = findViewById(R.id.button_add);
        recyclerView = findViewById(R.id.list_company);

        imageViewNoCompany = findViewById(R.id.imageView_no_company);
        press_company = findViewById(R.id.press);
        update_company = findViewById(R.id.update);
        delete_company = findViewById(R.id.delete);
        companyList = new ArrayList<>();



        databaseManager = new DatabaseManager(this);

        loadCompanyFromDatabase();
        adapter = new CompanyAdapter(this, companyList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                if (dy < 0) {
//                    buttonAdd.show();
//
//                } else if (dy > 0) {
//                    buttonAdd.hide();
//                }
//            }
//        });



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            final Company company = companyList.get(position);
            switch (direction){
                case ItemTouchHelper.LEFT:
                    openUpdateDialog(company);
                    break;
                case ItemTouchHelper.RIGHT:
                    companyList.remove(position);
                    adapter.notifyItemRemoved(position);
                    databaseManager.deleteCompany(company.getId());
                    Snackbar.make(recyclerView,company.getCompanyName(),Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    companyList.add(position,company);
                                    adapter.notifyItemInserted(position);
                                    databaseManager.returnCompany(company);


                                    imageViewNoCompany.setVisibility(View.INVISIBLE);
                                    press_company.setVisibility(View.VISIBLE);
                                    update_company.setVisibility(View.VISIBLE);
                                    delete_company.setVisibility(View.VISIBLE);
                                    buttonAdd.setVisibility(View.INVISIBLE);

                                }
                            }).show();
                    if (companyList.isEmpty()){
                        imageViewNoCompany.setVisibility(View.VISIBLE);
                        press_company.setVisibility(View.INVISIBLE);
                        update_company.setVisibility(View.INVISIBLE);
                        delete_company.setVisibility(View.INVISIBLE);
                        buttonAdd.setVisibility(View.VISIBLE);
                    }else {
                        buttonAdd.setVisibility(View.INVISIBLE);
                        press_company.setVisibility(View.VISIBLE);
                        update_company.setVisibility(View.VISIBLE);
                        delete_company.setVisibility(View.VISIBLE);
                        imageViewNoCompany.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
    };

    private void openUpdateDialog(final Company company) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_update_dialog, null, false);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editTextCompanyName = view.findViewById(R.id.edit_text_company_name);
        final EditText editTextOwnerName = view.findViewById(R.id.edit_text_owner_name);
       // final EditText editTextGstin = view.findViewById(R.id.edit_text_gstin);
        final EditText editTextAddress = view.findViewById(R.id.edit_text_address);
        final EditText editTextPhone = view.findViewById(R.id.edit_text_phone);


        editTextCompanyName.setText(company.getCompanyName());
        editTextOwnerName.setText(company.getOwnerName());
        //editTextGstin.setText(company.getCompanyName());
        editTextAddress.setText(company.getAddress());
        editTextPhone.setText(String.valueOf(company.getPhoneNumber()));

        view.findViewById(R.id.button_update_company).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyName = editTextCompanyName.getText().toString().trim();
                String ownerName = editTextOwnerName.getText().toString().trim();
               // String gstin = editTextGstin.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                if (companyName.isEmpty()) {
                    editTextCompanyName.setError("Company name can't be empty!");
                    editTextCompanyName.requestFocus();
                    return;
                }
                if (ownerName.isEmpty()) {
                    editTextOwnerName.setError("Owner name can't be empty!");
                    editTextOwnerName.requestFocus();
                    return;
                }

                if (databaseManager.updateCompany(
                        company.getId(),
                        companyName,
                        ownerName,
                       // gstin,
                        address,
                        phone
                )) {
                    loadCompanyFromDatabase();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Company updated successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Company not updated.Please try again", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });
    }

    private void loadCompanyFromDatabase() {
        companyList.clear();
        Cursor cursor = databaseManager.getAllCompany();
        if (cursor.moveToFirst()) {
            do {
                companyList.add(new Company(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)

                ));
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        if (companyList.isEmpty()) {
            imageViewNoCompany.setVisibility(View.VISIBLE);
            press_company.setVisibility(View.INVISIBLE);
            update_company.setVisibility(View.INVISIBLE);
            delete_company.setVisibility(View.INVISIBLE);
            buttonAdd.setVisibility(View.VISIBLE);
        }
        else {
            buttonAdd.setVisibility(View.INVISIBLE);
            update_company.setVisibility(View.VISIBLE);
            delete_company.setVisibility(View.VISIBLE);
            press_company.setVisibility(View.VISIBLE);
            imageViewNoCompany.setVisibility(View.INVISIBLE);
        }
    }

    private void openAddActivity() {
        Intent intent = new Intent(MainActivity.this, AddCompanyActivity.class);
        startActivity(intent);
        finish();
    }
}