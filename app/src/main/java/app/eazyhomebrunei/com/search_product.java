package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import app.eazyhomebrunei.com.adapter.search_adapter;
import app.eazyhomebrunei.com.model.search_pro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class search_product extends AppCompatActivity implements search_adapter.CartItemClickListener {
    RecyclerView recyclerView;
    List<search_pro> productList;
    ImageView search;
    EditText ed_search;
    String txtsearch;
    private search_adapter ctadapter;
    String Token;
    LinearLayout home,cart,wishlist,profile,search1;
    ImageView left_arrow;
    TextView nodata;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        left_arrow=(ImageView)findViewById(R.id.arrow);
      nodata=findViewById(R.id.nodata);
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search1=(LinearLayout)findViewById(R.id.search1);
        search=(ImageView) findViewById(R.id.searchimg);
        progress = findViewById(R.id.progress);
cartcount();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Vew_my_wishlist.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                startActivity(i);
            }
        });
        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),search_product.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),my_profile.class);
                startActivity(i);
            }
        });


        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });

            //progresss();

     //   search=(ImageView)findViewById(R.id.search);
        ed_search=(EditText)findViewById(R.id.searchEditText);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);


        recyclerView= findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager3= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager3);
        productList=new ArrayList<>();


// ed=(EditText)findViewById(R.id.searchEditText);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productList.clear();
                recyclerView.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {
//                filter(s.toString());

               // search_products();
                txtsearch=ed_search.getText().toString();
                recyclerView.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.GONE);
                productList.clear();
                progress.setVisibility(View.VISIBLE);
                search_products();

            }
        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtsearch=ed_search.getText().toString();
//                recyclerView.setVisibility(View.VISIBLE);
//                nodata.setVisibility(View.GONE);
//                search_products();
//            }
//        });
    }
    private void filter(String text) {
        ArrayList<search_pro> filteredList=new ArrayList<>();
        for (search_pro item:productList){
            if(item.getPname().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            else
            {
//                TextView nodata=findViewById(R.id.nodata);
//                nodata.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(),"jjj",Toast.LENGTH_LONG).show();
            }
        }
        ctadapter.filterList(filteredList);



    }
    private void cartcount() {
        final String URL_LATEST=PATH +"cartCount?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();
                    int    count1=jk.getInt("count");
                    TextView count=findViewById(R.id.cou);
                    // Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();

                    ImageView imageView4=findViewById(R.id.imageView4);
                    // Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();
                    imageView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                            startActivity(i);
                        }
                    });
                        count.setText(String. valueOf ( count1 )) ;



                    //Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                // params.put("mobile_no", phnumber);
                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
//    private void progresss() {
//        this.;
//
//    }
    private void search_products() {

        final String URL_SUBCATAGORY=PATH +"SearchProduct?&search_key="+txtsearch;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               //  Toast.makeText(getApplicationContext(),"jjj"+URL_SUBCATAGORY,Toast.LENGTH_LONG).show();
                try {
progress.setVisibility(View.GONE);

//
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("search_products");
                  // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();

                    ctadapter = new search_adapter(getApplicationContext(), productList);
                    recyclerView.setAdapter(ctadapter);
                    ctadapter.setOnItemClickListener(search_product.this);
                    recyclerView.setLayoutManager(new GridLayoutManager(search_product.this, 2));
                    productList.clear();



//
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
//                        String short_description=productobject.getString("short_description");
//                        String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("Image");
                        String price=productobject.getString("Price");
                        String OfferPrice=productobject.getString("OfferPrice");
                        int Status=productobject.getInt("Status");
                        int Flag=productobject.getInt("Flag");
                        int wishid=productobject.getInt("wishId");



//
                        search_pro product=new search_pro(id,pname,image,price,OfferPrice,Status,Flag,wishid);
                        productList.add(product);




                    }
//
////
////
////                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();

                                    recyclerView.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
              //  Toast.makeText(getApplicationContext(),"jjj",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
        //Toast.makeText(getApplicationContext(),"ji"+txtsearch,Toast.LENGTH_LONG).show();

    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);


    }


    @Override
    public void CartItemClick(int position) {
        cartcount();
    }
}
