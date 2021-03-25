package com.example.simpleaccounting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;
    TextView companyNam;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        //for opening dialog box in fragment_sales
        SalesFragment fragment = new SalesFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.mainLayout,fragment);

        AddClientFragment fragment2 = new AddClientFragment();
        FragmentManager fm2 = getSupportFragmentManager();
        fm2.beginTransaction().add(R.id.mainLayout2,fragment2);
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.container,fragment,"SalesFragment");
//        transaction.commit();



        // Tabber Activity
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();





        //viewPagerAdapter
        viewPagerAdapter.AddFragment(new SalesFragment(),"");
        viewPagerAdapter.AddFragment(new PayInFragment(),"");
        viewPagerAdapter.AddFragment(new PurchaseFragment(),"");
        viewPagerAdapter.AddFragment(new PayOutFragment(),"");
        //viewPagerAdapter.AddFragment(new AddClientFragment(),"");
        viewPagerAdapter.AddFragment(new AddItemFragment(),"");
//        viewPagerAdapter.AddFragment(new SalesReturnFragment(),"");
//        viewPagerAdapter.AddFragment(new PurchaseReturnFragment(),"");
//        viewPagerAdapter.AddFragment(new MaterialIssuedFragment(),"");
//        viewPagerAdapter.AddFragment(new MaterialReceivedFragment(),"");
//        viewPagerAdapter.AddFragment(new EstimateFragment(),"");
//        viewPagerAdapter.AddFragment(new PurchaseOrderFragment(),"");
//        viewPagerAdapter.AddFragment(new IncomeFragment(),"");
//        viewPagerAdapter.AddFragment(new ExpenseFragment(),"");
//        viewPagerAdapter.AddFragment(new JournalFragment(),"");
//        viewPagerAdapter.AddFragment(new CreditNoteFragment(),"");
//        viewPagerAdapter.AddFragment(new DebitNoteFragment(),"");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Client");//old name is Sales
        tabLayout.getTabAt(1).setText("Pay In");
        tabLayout.getTabAt(2).setText("Supplier");//old name is Purchase
        tabLayout.getTabAt(3).setText("Pay Out");
        //tabLayout.getTabAt(4).setText("Add Client");
        tabLayout.getTabAt(4).setText("Add Item");
//        tabLayout.getTabAt(6).setText("Sales Return");
//        tabLayout.getTabAt(7).setText("Purchase Return");
//        tabLayout.getTabAt(8).setText("Material Issued");
//        tabLayout.getTabAt(9).setText("Material Received");
//        tabLayout.getTabAt(10).setText("Estimate");
//        tabLayout.getTabAt(11).setText("Purchase Order");
//        tabLayout.getTabAt(12).setText("Income");
//        tabLayout.getTabAt(13).setText("Expense");
//        tabLayout.getTabAt(14).setText("Journal");
//        tabLayout.getTabAt(15).setText("Credit Note");
//        tabLayout.getTabAt(16).setText("Debit Note");






            //for setting company name in the navigation drawer
        navigationView=(NavigationView)findViewById(R.id.navigation_menu);
        //navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        companyNam = header.findViewById(R.id.textView10);
        String s=getIntent().getStringExtra("keyname");
        toolbar.setTitle(s);
        //companyNam.setText(s);
        companyNam.setText(getString(R.string.app_name));

        //setting tabbed toolbar
        //toolbar =findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle(getString(R.string.app_name));
        //getSupportActionBar().setTitle(s);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setUpToolbar();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_account_list:
//                        //if(item.getItemId() == R.id.nav_clients_list){
//                            fragmentManager = getSupportFragmentManager();
//                            fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.add(R.id.container_fragment, new ClientAllFragment());
//                            fragmentTransaction.commit();
                        Intent intent = new Intent(MainActivity2.this, account_list.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_transaction:
                        Intent intent1 = new Intent(MainActivity2.this , transaction.class);

                        startActivity(intent1);
                        break;
                    case R.id.nav_item_list:
                        Intent intent2 = new Intent(MainActivity2.this , itemList.class);

                        startActivity(intent2);
                        break;
                    case R.id.nav_client_list:
                        Intent intent3 = new Intent(MainActivity2.this , clientList.class);

                        startActivity(intent3);
                        break;
                    case R.id.nav_sale_list:
                        Intent intent4 = new Intent(MainActivity2.this , SalesList.class);

                        startActivity(intent4);
                        break;
                    case R.id.nav_purchase_list:
                        Intent intent5 = new Intent(MainActivity2.this , PurchaseList.class);

                        startActivity(intent5);
                        break;
                    case R.id.nav_report:
                        Intent intent6 = new Intent(MainActivity2.this , report.class);
                        startActivity(intent6);
                        break;
                    case R.id.nav_settings:
                    Intent intent7 = new Intent(MainActivity2.this , SettingsActivity.class);
                    startActivity(intent7);
                    break;
                    case R.id.nav_help_center:
                        Intent intent8 = new Intent(MainActivity2.this , helpCenter.class);
                        startActivity(intent8);
                        break;
                    case R.id.nav_about:
                        Intent intent9 = new Intent(MainActivity2.this , about_us.class);
                        startActivity(intent9);
                        break;
                    case R.id.nav_logout:
//                        Intent intent8 = new Intent(MainActivity2.this , about_us.class);
//                        startActivity(intent8);
//                        break;
                        FirebaseAuth.getInstance().signOut();
                        Intent intent10 = new Intent(getApplicationContext(), Login.class);
                        intent10.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent10);
                        finish();
                        break;

                    case R.id.nav_chat:
                        Intent intent11 = new Intent(MainActivity2.this , chat.class);
                        startActivity(intent11);
                        break;


                    case  R.id.nav_share:{

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =  "http://play.google.com/store/apps/detail?id=" + getPackageName();
                        String shareSub = "Try now";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));

                    }
                    break;
                }
                return false;
            }
        });
    }



    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    public void setUpToolbar() {
//        drawerLayout = findViewById(R.id.drawerLayout);
//        //Toolbar toolbar = findViewById(R.id.toolbar);
//        //setSupportActionBar(toolbar);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
//        actionBarDrawerToggle.syncState();
//
//    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.nav_clients_list){
//            fragmentManager = getSupportFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(R.id.container_fragment, new ClientAllFragment());
//            fragmentTransaction.commit();
//
//        }
//        return true;
//    }
}