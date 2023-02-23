package com.example.assignmentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    UniversityAdapter universityAdapter;
    List<UniversityModel> universityModels = new ArrayList<>();
    public ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recycleUniversity;
    SliderView sliderView;
    int[] images = {R.drawable.savitribai_university,
            R.drawable.rashtrasant_university,
            R.drawable.babasaheb_university,
            R.drawable.maharashtra_university};
    String selectedFilePath="";
    TextView txtFileName,txtUniversityName;
    String name,email,number;
    TextInputEditText edtFullName,edtEmail,edtMobile;
    UniversityAdapter.ItemClickListener itemClickListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initView();
    }
   public void  initView()
    {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        recycleUniversity = findViewById(R.id.recycleUniversity);
        AddListData();
        itemClickListener = this::FormInfo;
        universityAdapter = new UniversityAdapter(MainActivity.this,universityModels,itemClickListener);
        recycleUniversity.setHasFixedSize(true);
        recycleUniversity.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recycleUniversity.setAdapter(universityAdapter);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        toggleDrawer();
        sliderView = findViewById(R.id.sliderView);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

    }

    private void FormInfo(UniversityModel universityModel) {
        final BottomSheetDialog bottomSheet = new BottomSheetDialog(MainActivity.this);
        bottomSheet.setContentView(R.layout.layout_form);
         txtUniversityName = bottomSheet.findViewById(R.id.txtUniversityName);
         edtFullName = bottomSheet.findViewById(R.id.edtFullName);
         edtEmail = bottomSheet.findViewById(R.id.edtEmail);
         txtFileName = bottomSheet.findViewById(R.id.txtFileName);
         edtMobile = bottomSheet.findViewById(R.id.edtMobile);
        AppCompatButton btnUploadFile = bottomSheet.findViewById(R.id.btnUploadFile);
        AppCompatButton btnSubmit = bottomSheet.findViewById(R.id.btnSubmit);
        txtUniversityName.setText(universityModel.getUniversity_Name());
        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),100);
                }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFill())
                {
                    SubmitData();
                    bottomSheet.cancel();
                }

            }
        });

        bottomSheet.show();
    }

    private void SubmitData() {
        DatabaseHelper databaseHelperClass = new DatabaseHelper(this);
        FormModel formModel = new FormModel(name,email,number,selectedFilePath,txtUniversityName.getText().toString().trim());
        databaseHelperClass.addEvent(formModel);
    }

    private boolean isFill() {
        name = edtFullName.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        number = edtMobile.getText().toString().trim();
        if(name.isEmpty())
        {
            edtFullName.setError("Enter Name");
            edtFullName.requestFocus();
        }else if(email.isEmpty())
        {
            edtEmail.setError("Enter Email");
            edtEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            edtEmail.setError("Enter Valid Email");
            edtEmail.requestFocus(); 
        }else if(number.isEmpty())
        {
            edtMobile.setError("Enter Mobile Number");
            edtMobile.requestFocus();
        }
        else if(number.length() !=10)
        {
            edtMobile.setError("Enter  Valid Mobile Number");
            edtMobile.requestFocus();
        }else if(selectedFilePath.isEmpty())
        {
            Toast.makeText(this, "Select File ", Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }
        return false;
    }

    private void AddListData() {
        Bitmap icon = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.rashtrasant_university);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        icon.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        UniversityModel universityModel1 = new UniversityModel("Rashtrasant Tukadoji Maharaj Nagpur University ",imageEncoded);
        universityModels.add(universityModel1);
        Bitmap icon1 = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.savitribai_university);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        icon1.compress(Bitmap.CompressFormat.PNG, 90, baos1);
        byte[] b1 = baos1.toByteArray();
        String imageEncoded1 = Base64.encodeToString(b1, Base64.DEFAULT);
        UniversityModel universityModel2 = new UniversityModel("Savitribai Phule Pune University ",imageEncoded1);
        universityModels.add(universityModel2);
        Bitmap icon2 = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.maharashtra_university);
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        icon2.compress(Bitmap.CompressFormat.PNG, 90, baos2);
        byte[] b2 = baos2.toByteArray();
        String imageEncoded2 = Base64.encodeToString(b2, Base64.DEFAULT);
        UniversityModel universityModel3 = new UniversityModel("Maharashtra University of Health Sciences",imageEncoded2);
        universityModels.add(universityModel3);
        Bitmap icon3 = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.babasaheb_university);
        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
        icon3.compress(Bitmap.CompressFormat.PNG, 90, baos3);
        byte[] b3 = baos3.toByteArray();
        String imageEncoded3 = Base64.encodeToString(b3, Base64.DEFAULT);
        UniversityModel universityModel4 = new UniversityModel("Dr. Babasaheb Ambedkar Technological University ",imageEncoded3);
        universityModels.add(universityModel4);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 100){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                Uri FilePath;
                 selectedFilePath = selectedFileUri.getPath();
                Log.i("TAG","Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    txtFileName.setText(selectedFilePath);
                    Toast.makeText(this, selectedFilePath, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void CheckPermission() {
        if (
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
        } else {
            Intent intent = new Intent();
            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),100);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),100);
            }
            else
            {
                CheckPermission();
            }


        }
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

            case R.id.itemHome:
                toolbar.setTitle("Home");
                closeDrawer();
                break;
            case R.id.itemAbout:
                closeDrawer();
                break;
            case R.id.itemForm:
                Intent i = new Intent(MainActivity.this,FormListActivity.class);
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
