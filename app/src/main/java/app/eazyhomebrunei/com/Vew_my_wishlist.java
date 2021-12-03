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
import android.widget.Button;
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


import app.eazyhomebrunei.com.adapter.View_my_wishlist_adapter;
import app.eazyhomebrunei.com.model.wishlist_pro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Vew_my_wishlist extends AppCompatActivity implements View_my_wishlist_adapter.CartItemClickListener {
    Button addre;
    RecyclerView recyclerView;
    List<wishlist_pro> addresslist;
    private View_my_wishlist_adapter fdadapter;
    String Token;
    int id;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;

    private ProgressBar progress,progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vew_my_wishlist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        left_arrow=(ImageView)findViewById(R.id.arrow);

        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id=intent.getIntExtra("id",0);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);

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


        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        addresslist=new ArrayList<>();
        view_all_wishlist();

        progresss();
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

    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);


    }
    private void view_all_wishlist() {
        final String URL_MY_WISHLIST=PATH +"MyWishlist?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progress.setVisibility(View.GONE);

//                   //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("wishlist");
                  //  Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                fdadapter = new View_my_wishlist_adapter(getApplicationContext(), addresslist);
                    recyclerView.setAdapter(fdadapter);
                    fdadapter.setOnItemClickListener(Vew_my_wishlist.this);
                    addresslist.clear();
//
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int wishlist_id=productobject.getInt("wishId");

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
//                        String short_descrption=productobject.getString("short_description");
//                        String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("Image");
                        int status=productobject.getInt("Status");
                        String price=productobject.getString("Price");
                        int flag= Integer.parseInt("1");
                        String offer_price=productobject.getString("OfferPrice");




                        wishlist_pro fd_product=new wishlist_pro(wishlist_id,id,pname,image,price,offer_price,status,flag);
                        addresslist.add(fd_product);




                    }
                } catch (JSONException e) {
                    TextView nodata=findViewById(R.id.nodata);
                    nodata.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
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


    @Override
    public void CartItemClick(int position) {
        cartcount();
    }
}
