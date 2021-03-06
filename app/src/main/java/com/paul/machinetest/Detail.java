package com.paul.machinetest;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;


import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    ImageView imageView;

    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        imageView = findViewById(R.id.pic);
        textView1 = findViewById(R.id.tv_title1);
        textView2 = findViewById(R.id.tv_title2);
        textView3 = findViewById(R.id.tv_title3);
        textView4 = findViewById(R.id.tv_title4);
        textView5 = findViewById(R.id.tv_title5);
        textView6 = findViewById(R.id.tv_title6);
        textView7 = findViewById(R.id.tv_title7);


        Intent intent = getIntent();
        String json = intent.getStringExtra("list");

        Gson gson = new Gson();

        DownloadModel responseModel = gson.fromJson(json,
                DownloadModel.class);
        Log.d("Detail", "onCreate: "+responseModel.getName());

        getSupportActionBar(). setTitle(responseModel.getName());
        getSupportActionBar().setHomeButtonEnabled(true);

        if (responseModel.getProfileImage()!=null){
            Picasso.with(Detail.this)
                    .load(responseModel.getProfileImage())
                    .into(imageView);}
        textView1.setText("Name : "+responseModel.getName());
        textView3.setText("Email : "+responseModel.getEmail());
        if(responseModel.getPhone()!=null){
            textView5.setText("Phone : "+responseModel.getPhone());
        }
        textView2.setText("Username : "+responseModel.getUsername());
        textView6.setText("Website : "+responseModel.getWebsite());
        textView4.setText("Address : "+responseModel.getAddress().getSuite()+","+responseModel.getAddress().getStreet()+","+responseModel.getAddress().getCity()+","+responseModel.getAddress().getZipcode());
        if (responseModel.getCompany()!=null) {
            textView7.setText("Company : "+responseModel.getCompany().getName() + "," + responseModel.getCompany().getBs() + "," + responseModel.getCompany().getCatchPhrase());
        }

    }
}