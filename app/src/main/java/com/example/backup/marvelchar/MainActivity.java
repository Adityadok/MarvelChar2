package com.example.backup.marvelchar;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://gateway.marvel.com/v1/public/characters?apikey=773b220d2281fa956cfd6e962a2855c5&hash=";
    private static final String privateKey="84c8c8d2fb3e6df265e83ec3294f34a3caaccde3";
    private static final String publiceKey="773b220d2281fa956cfd6e962a2855c5";
    public String ImageURL;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        loadRecyclerViewData();


    }

    private void loadRecyclerViewData() {
        StringBuilder builder = new StringBuilder(URL_DATA);
        long timestamp = 0;
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
             timestamp = System.currentTimeMillis();
            String t = String.valueOf(timestamp) + privateKey + publiceKey;
            md.update(t.getBytes());
            hash = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        String url = builder.append(hash).append("&ts=").append(String.valueOf(timestamp)).toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {



                    @Override
                    public void onResponse(String s) {
                        Log.d("Response",s);
                        progressDialog.dismiss();
                        try {
                            JSONArray array = new JSONObject(s).getJSONObject("data").getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                JSONObject tnail= o.getJSONObject("thumbnail");
                                String ext = tnail.getString("extension");
                                String path = tnail.getString("path");
                                String url= path+"."+ext;
                                ListItem item = new ListItem(
                                        o.getString("name"),
                                        o.getString("description"),
                                        url,
                                        o.getString("modified"),
                                        o.getString("id"),
                                        o.getString("resourceURI")


                                );




                                listItems.add(item);


                            }
                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
