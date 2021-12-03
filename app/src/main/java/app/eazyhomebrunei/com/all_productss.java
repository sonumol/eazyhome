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

import app.eazyhomebrunei.com.adapter.all_products_adapter;
import app.eazyhomebrunei.com.model.all_products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class all_productss extends AppCompatActivity  implements  all_products_adapter.CartItemClickListener{

    RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3;
    List<all_products> fdproductlist;
    private all_products_adapter fdadapter;
    String Token;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        left_arrow=(ImageView)findViewById(R.id.arrow) ;

        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        progresss();
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





        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fdproductlist = new ArrayList<>();
        featured_product();
        cartcount();
    }
    private void progresss() {
        this.progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

    }

    private void featured_product() {
      final String URL_ALL_products=PATH +"AllProducts?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ALL_products, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                   // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
progress.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("all_products");
                    //Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();

                    fdadapter = new all_products_adapter(getApplicationContext(), fdproductlist);
                    recyclerView.setAdapter(fdadapter);
                    fdadapter.setOnItemClickListener(all_productss.this);
                    // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();

//
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
//                        String short_descrption=productobject.getString("short_description");
//                        String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("Image");
                        int status=productobject.getInt("Status");
                        int flag=productobject.getInt("Flag");
                        String price=productobject.getString("Price");
                        String offer_price=productobject.getString("OfferPrice");
                        int wishId=productobject.getInt("wishId");

//
                        all_products fd_product=new all_products(id,pname,image,price,offer_price,status,flag,wishId);
                        fdproductlist.add(fd_product);




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

                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
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

    @Override
    public void CartItemClick(int position) {
        cartcount();
    }
}

