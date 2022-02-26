package com.paul.machinetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getName();

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://www.mocky.io/v2/5d565297300000680030a986";

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TEXT = "ahhsaushhuuashu";
    private static final String KEY = "myKey";
    ArrayList<DownloadModel> arrayList = new ArrayList<DownloadModel>();

    String downdata="0";
    ListView list;

    String responsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        if (isfirst(MainActivity.this)){
            downloaddata();
        }else{
            responsed = loadData(MainActivity.this);
            Viewonscreen(responsed);
        }




    }

    private void Viewonscreen(String responsed) {

       JSONArray mainObjectArray = null;

        try {
            mainObjectArray = new JSONArray(responsed);
            for (int i = 0; i<mainObjectArray.length();i++){
                DownloadModel downloadModel = new DownloadModel();
                DownloadModel.Address address = new DownloadModel.Address();
                DownloadModel.Address.Geo geo= new DownloadModel.Address.Geo();
                DownloadModel.Company company = new DownloadModel.Company();
                JSONObject jsonObject = mainObjectArray.getJSONObject(i);
                downloadModel.setName(jsonObject.getString("name"));
                downloadModel.setEmail(jsonObject.getString("email"));
                downloadModel.setUsername(jsonObject.getString("username"));
                downloadModel.setId(jsonObject.getInt("id"));
                downloadModel.setProfileImage(jsonObject.getString("profile_image"));
                downloadModel.setPhone(jsonObject.getString("phone"));
                downloadModel.setWebsite(jsonObject.getString("website"));
                JSONObject addres = jsonObject.getJSONObject("address");
                address.setCity(addres.getString("city"));
                address.setZipcode(addres.getString("zipcode"));
                address.setSuite(addres.getString("suite"));
                address.setStreet(addres.getString("street"));
                JSONObject geol = addres.getJSONObject("geo");
                geo.setLat(geol.getString("lat"));
                geo.setLng(geol.getString("lng"));
                address.setGeo(geo);
                if (!jsonObject.isNull("company")){
                JSONObject comp = jsonObject.getJSONObject("company");
                company.setBs(comp.getString("bs"));
                company.setName(comp.getString("name"));
                company.setCatchPhrase(comp.getString("catchPhrase"));
                downloadModel.setCompany(company);}
                downloadModel.setAddress(address);

                Log.d(TAG, "downloadmodel: "+ downloadModel);

                arrayList.add(downloadModel);

            }
            CustomAdapter customAdapter = new CustomAdapter(this, arrayList);
            list.setAdapter(customAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void downloaddata() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+ response.toString());
                saveData(MainActivity.this,response);
                Viewonscreen(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

    }

    public Boolean isfirst(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return !sharedPreferences.contains(KEY);
    }

    public static void saveData(Context context, String response) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, response);
        editor.apply();
    }

    public static String loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String text = sharedPreferences.getString(KEY, "");
        return text;
    }
}