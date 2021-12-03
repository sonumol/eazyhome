package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
import com.facebook.shimmer.ShimmerFrameLayout;


import app.eazyhomebrunei.com.adapter.view_all_latest_products;
import app.eazyhomebrunei.com.model.latest_products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class view_all_latestproduct extends AppCompatActivity  implements view_all_latest_products.CartItemClickListener {
    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    List<latest_products> latest_productlist;
    private view_all_latest_products latest_adapter;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;
    String Token,phnumber;
    private ShimmerFrameLayout shimmer;
    private LinearLayout ll_main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_latestproduct);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shimmer = findViewById(R.id.shimmer);
        ll_main_layout= findViewById(R.id.ln);
        left_arrow=(ImageView)findViewById(R.id.arrow) ;

        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);

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
        search.setOnClickListener(new View.OnClickListener() {
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
        recyclerView2 = findViewById(R.id.recy3);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager1= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(new GridLayoutManager(this, 2));
        progresss();
        latest_productlist=new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
        phnumber=pref1.getString("number", null);
        Latest_product();
        cartcount();
    }
    private void progresss() {
        this.progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progress.setVisibility(View.GONE);
//            }
//        },5000);
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

    private void Latest_product() {
        final String URL_LATEST=PATH +"home?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {
                    progress.setVisibility(View.GONE);

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("latest_list");
                    // Toast.makeText(getApplicationContext(),"j"+ar,Toast.LENGTH_LONG).show();

                    latest_adapter = new view_all_latest_products(getApplicationContext(),latest_productlist);
                    recyclerView2.setAdapter(latest_adapter);
                    latest_adapter.setOnItemClickListener(view_all_latestproduct.this);

                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int id=productobject.getInt("id");
                        // String reference=productobject.getString("reference");

                        String pname=productobject.getString("product");
                        /// String short_descrption=productobject.getString("short_description");
                        // String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("thumbnail_image");
                        // int category_id=productobject.getInt("category_id");
                        //  int subcategory_id=productobject.getInt("subcategory_id");
                        //  int subincategory_id=productobject.getInt("subincategory_id");
                        String offer_price=productobject.getString("offer_price");
                        String price=productobject.getString("price");
                        String flag=productobject.getString("Flag");
                        int wishid=productobject.getInt("wishId");
                        int status=productobject.getInt("Status");




                        latest_products latest_product=new latest_products(id,pname,offer_price,price,flag,image,wishid,status);
                        latest_productlist.add(latest_product);

                        // Toast.makeText(getApplicationContext(),"jjj"+flag,Toast.LENGTH_LONG).show();
                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                        ll_main_layout.setVisibility(View.VISIBLE);


                    }
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

                params.put("mobile_no", phnumber);
                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }


    @Override
    public void CartItemClick(int position) {
        cartcount();
    }
}
