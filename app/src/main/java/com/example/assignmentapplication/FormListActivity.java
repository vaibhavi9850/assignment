package com.example.assignmentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class FormListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    List<FormModel> formModelList = new ArrayList<>();
    RecyclerView recycleForm;
    FormAdapter formAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
        initView();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Form List");
        drawerLayout = findViewById(R.id.drawerLayout);
        recycleForm = findViewById(R.id.recycleForm);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(FormListActivity.this);
        toggleDrawer();
        DatabaseHelper databaseHelperClass = new DatabaseHelper(this);
       formModelList =  databaseHelperClass.getFormList();
        formAdapter = new FormAdapter(FormListActivity.this,formModelList);
        recycleForm.setHasFixedSize(true);
        recycleForm.setLayoutManager(new LinearLayoutManager(FormListActivity.this));
        recycleForm.setAdapter(formAdapter);
    }
    private void toggleDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.itemForm:
                toolbar.setTitle("Form List");
                closeDrawer();
                break;
            case R.id.itemAbout:
                closeDrawer();
                break;
            case R.id.itemHome:
                Intent i = new Intent(FormListActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                closeDrawer();
                break;
            case R.id.itemServices:
                closeDrawer();
                break;
            case R.id.itemContact:
                closeDrawer();
                break;


        }
        return true;
    }
    private void closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}
