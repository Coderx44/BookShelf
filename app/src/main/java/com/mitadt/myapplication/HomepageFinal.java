package com.mitadt.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomepageFinal extends AppCompatActivity {
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Books> booksArrayList;
    private ArrayList<String> arrayAdapter;
    private RequestQueue requestQueue;
    private AutoCompleteTextView searchbar;
    private String[] str = new String[]{
            "Harry potter", "prasad", "ezg", "abc"
    };
    private String url;
    private Set<String> a= new HashSet<String>();
    private int before=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_final);
        searchbar = (AutoCompleteTextView) findViewById(R.id.searchBar);
        requestQueue = Volley.newRequestQueue(this);
searchbar.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    before = after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Toast.makeText(HomepageFinal.this, searchbar.getEditableText().toString(), Toast.LENGTH_SHORT).show();
        Log.d("text", Integer.toString(count));
        if (searchbar.getEditableText().toString().length() > 0  && count>before) {

            requestQueue = Volley.newRequestQueue(HomepageFinal.this);
            url = "https://08ebbaaef6c3.ngrok.io/api/search/auto?search="+searchbar.getEditableText().toString()  ;
//                    searchbar.getEditableText().toString();
            try {


                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d("response", searchbar.getEditableText().toString());
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                            a.add(response.getJSONObject(i).getString("name"));
                                            Log.d("json", response.getJSONObject(i).getString("name"));
                                    }
                                } catch (JSONException e) {
                                    Log.d("search", "Not received");
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Log.d("email", "Not received");
                            }
                        });

// Access the RequestQueue through your singleton class.
                requestQueue.add(jsonArrayRequest);
                if (a != null) {
                    String[] items = a.toArray(new String[0]);

                    ArrayAdapter<String> string = new ArrayAdapter<String>(HomepageFinal.this, android.R.layout.simple_list_item_1, items);
                    searchbar.setThreshold(1);
                    string.getFilter().filter(searchbar.getEditableText().toString());
                    searchbar.setAdapter(string);
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
});

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksArrayList = new ArrayList<>();
         url = "https://08ebbaaef6c3.ngrok.io/api/home";
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequestRequest = new JsonArrayRequest
                (Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {

                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    Toast.makeText(HomepageFinal.this,
                                            response.getJSONObject(0).getString("price"),
                                            Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i<response.length(); i++)
                                    {
                                        Books book = new Books();
                                        book.bookName = response.getJSONObject(i).getString("name");
                                        book.price = response.getJSONObject(i).getString("price");
                                        book.imgurl = response.getJSONObject(i).getString("coverImage");
                                        book.imgurl = "https://30a9c4a17804.ngrok.io/"+book.imgurl.substring(0,7)+'/'+book.imgurl.substring(8);
//                                        book.imgurl = book.imgurl.substring(0,7)+'/'+book.imgurl.substring(7+1);
//                                        book.imgurl = " https://30a9c4a17804.ngrok.io/"+book.imgurl;
//                                        book.imgurl = "https://30a9c4a17804.ngrok.io/uploads/1621446745241prasad.jpg";
                                        booksArrayList.add(book);
                                    }
                                    recyclerViewAdapter = new RecyclerViewAdapter(HomepageFinal.this, booksArrayList);
                                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
                                    recyclerView.setAdapter(recyclerViewAdapter);


                                } catch (JSONException jsonException) {
                                    jsonException.printStackTrace();
                                }
                            }

    }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("error","Not received");
                    }
                });

// Access the RequestQueue through your singleton class.
        requestQueue.add(jsonArrayRequestRequest);


    }


}