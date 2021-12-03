package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import app.eazyhomebrunei.com.adapter.view_my_cart_adapter;
import app.eazyhomebrunei.com.model.cart_pro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class view_my_cart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener , view_my_cart_adapter.CartItemClickListener {

    Button checkout;
    RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3;
    List<cart_pro> fdproductlist;
    private view_my_cart_adapter fdadapter;
    String Token;
    TextView grand_total,add_more;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    SwipeRefreshLayout refreshLayout;
    public view_my_cart_adapter adapter;
    String gd_total;
    ScrollView scr;
  //  private double quantity_sum = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    private static DecimalFormat df = new DecimalFormat("0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_cart);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        cartcount();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scr=findViewById(R.id.scr);
        left_arrow=(ImageView)findViewById(R.id.arrow) ;
        checkout=(Button)findViewById(R.id.checkout);
        add_more=(TextView)findViewById(R.id.addmore) ;
        grand_total=(TextView)findViewById(R.id.grand_total);
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);

        //

       //this.progress=findViewById(R.id.progress);
      //  progress.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progress.setVisibility(View.GONE);
//            }
//        },5000);
//


        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe);
        this.refreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
        this.refreshLayout.setColorSchemeResources(R.color.swprefresh1, R.color.swprefresh2, R.color.swprefresh3);


//
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
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),checkout.class);
                startActivity(i);
            }
        });
        add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),all_productss.class);
                startActivity(i);
            }
        });

        /////////////////////Latest Products//////////////////////////////////

        recyclerView2 = findViewById(R.id.recy1);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager1= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager1);

        fdproductlist=new ArrayList<>();



        //////////////////////////////////////////////////////////////////////

    }
    public void onResume() {
        super.onResume();
        this.refreshLayout.post(new Runnable() {
            public void run() {
                view_my_cart.this.refreshLayout.setRefreshing(true);
                view_my_cart.this.Cart_product();
//               Home.this.Latest_product();
//                Home.this.offfer_product();
//                Home.this.featured_product();
//                Home.this.loadProducts();
            }
        });
    }
    @Override
    public void onRefresh() {
        Cart_product();

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

    private void Cart_product() {
        final String URL_CART=PATH +"MyCart?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    view_my_cart.this.refreshLayout.setRefreshing(false);

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");

                    JSONArray ar=jk.getJSONArray("cart");

                    fdadapter = new view_my_cart_adapter(getApplicationContext(), fdproductlist);
                    recyclerView2.setAdapter(fdadapter);
                    fdadapter.setcartOnItemClickListener(view_my_cart.this);
                    fdproductlist.clear();

//
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int cartid=productobject.getInt("cartId");

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
                        String image=productobject.getString("image");
                        String quantity=productobject.getString("quantity");
                        String price=productobject.getString("price");
                        String total_price=productobject.getString("total_price");
                        String reference=productobject.getString("reference");
                        String offer_price=productobject.getString("offer_price");


                        cart_pro fd_product=new cart_pro(cartid,id,pname,image,quantity,price,total_price,reference,offer_price);
                        fdproductlist.add(fd_product);
                      //  fdproductlist.clear();




                    }
                     gd_total = jk.getString("total");
                    final Float gdto= Float.parseFloat(gd_total);
                    grand_total.setText("BND."+df.format(gdto));


                   // Toast.makeText(getApplicationContext(),"jjj"+gd_total,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                  // Toast.makeText(getApplicationContext(),"No data found",Toast.LENGTH_LONG).show();
                    scr.setVisibility(View.GONE);
                    TextView textView=findViewById(R.id.text);
                    textView.setVisibility(View.VISIBLE);
//                    e.printStackTrace();
                    checkout.setVisibility(View.GONE);
                    grand_total.setText("");
                    TextView textView1=findViewById(R.id.amo);
                    textView1.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
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
       // updateData();

    }


    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Home.class);
        startActivity(i);


    }




    @Override
    public void cartItemClick(int position) {
       // cart_pro clickeditem = fdproductlist.get(position);
cartcount();

        final String URL_CART=PATH +"MyCart?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();

                try {


                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");

                    JSONArray ar=jk.getJSONArray("cart");

//                    fdadapter = new view_my_cart_adapter(getApplicationContext(), fdproductlist);
//                    recyclerView2.setAdapter(fdadapter);
//                   // fdadapter.setcartOnItemClickListener(view_my_cart.this);

//
                    gd_total = jk.getString("total");
                    final Float gdto= Float.parseFloat(gd_total);
                    grand_total.setText("₹"+df.format(gdto));


                   //  Toast.makeText(getApplicationContext(),"jjj"+gd_total,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
//                     Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                  //  e.printStackTrace();
                    scr.setVisibility(View.GONE);
                    TextView textView=findViewById(R.id.text);
                    textView.setVisibility(View.VISIBLE);
//                    e.printStackTrace();
                    checkout.setVisibility(View.GONE);
                    grand_total.setText("");
                    TextView textView1=findViewById(R.id.amo);
                    textView1.setVisibility(View.GONE);
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
//        // updateData();
//        private void progresss() {
//            this.progress=findViewById(R.id.progress);
//            progress.setVisibility(View.VISIBLE);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progress.setVisibility(View.GONE);
//                }
//            },5000);
//        }

    }
}
