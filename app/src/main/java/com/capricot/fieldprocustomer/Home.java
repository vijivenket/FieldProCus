package com.capricot.fieldprocustomer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.capricot.fieldprocustomer.FragmentHome.ComingSoon;
import com.capricot.fieldprocustomer.FragmentHome.ComingSoonNew;
import com.capricot.fieldprocustomer.FragmentHome.MyProduct;
import com.capricot.fieldprocustomer.FragmentHome.RaiseTicket;

public class Home extends AppCompatActivity {


    private TabLayout tabLayout;

    private Fragment ticketrise;
    private Fragment myproduct;
    private Fragment tickethistory;
    private Fragment amccontract;

     private SharedPreferences app_preferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(Home.this);
        editor = app_preferences.edit();


        tabLayout = (TabLayout) findViewById(R.id.tabs_home_dashboard);

        bindWidgetsWithAnEvent();

        setupTabLayout();
        bindWidgetsWithAnEvent();


        editor.putBoolean("isFirstTime", false);
        editor.apply();

    }

    private void setupTabLayout() {

//        dashboard = new DashBoard();
        ticketrise = new RaiseTicket();
        amccontract = new ComingSoon();
        tickethistory = new ComingSoonNew();
        myproduct = new MyProduct();


        replaceFragment(ticketrise);
//        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_blue), true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ticket), true);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.settings),false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.history),false);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.my_products),false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(Color.parseColor("#2962FF"), PorterDuff.Mode.SRC_IN);
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(Color.parseColor("#2962FF"), PorterDuff.Mode.SRC_IN);
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private void setCurrentTabFragment(int tabPosition) {


        switch (tabPosition) {

//            case 0:
//                getSupportActionBar().setTitle("Home");
//                replaceFragment(dashboard);
//                break;
            case 0:
//                getSupportActionBar().setTitle("Raise Ticket");
                replaceFragment(ticketrise);
                break;
            case 1:
//                getSupportActionBar().setTitle("AMC Contract");
                replaceFragment(amccontract);
                break;
            case 2:
//                getSupportActionBar().setTitle("Ticket History");
                replaceFragment(tickethistory);
                break;

            case 3:
//                getSupportActionBar().setTitle("My Products");
                replaceFragment(myproduct);
                break;

        }

    }

    private void replaceFragment(Fragment fragment) {

        try {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frame_container, fragment);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile_dashboard) {

            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);
            finish();

            tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#2962FF"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(3).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(R.drawable.exit).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                }).setNegativeButton("No", null).show();

    }


}
