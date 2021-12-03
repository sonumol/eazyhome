package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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


import app.eazyhomebrunei.com.adapter.catagory_adapter;
import app.eazyhomebrunei.com.adapter.viewallcatagory_adapter;
import app.eazyhomebrunei.com.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class view_allcategoriesproduct extends AppCompatActivity  {
    RecyclerView recyclerView, recyclerView1, recyclerView2, recyclerView3;
    List<Product> productList;
    private viewallcatagory_adapter ctadapter;
    private static final String URL_CATAGORY = PATH +"ListAllCategory";
    String Token;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;


    private ShimmerFrameLayout shimmer;
    private LinearLayout ll_main_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_allcategoriesproduct);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        shimmer = findViewById(R.id.shimmer);
        ll_main_layout= findViewById(R.id.ln);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView3 = findViewById(R.id.recy4);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2));

        productList = new ArrayList<>();
        progresss();
        loadProducts();
cartcount();

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
    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                     progress.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk = jsonObject.getJSONObject("data");
                    //JSONArray ar=new JSONArray(jk.getJSONArray("cat"));
                    JSONArray ar = jk.getJSONArray("cat");
                    ctadapter = new viewallcatagory_adapter(getApplicationContext(), productList);
                    recyclerView3.setAdapter(ctadapter);
//                    ctadapter.setOnItemClickListener(view_allcategoriesproduct.this);
                    // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <= ar.length(); i++) {
                        JSONObject productobject = ar.getJSONObject(i);

                        int CategoryId = productobject.getInt("CategoryId");
                        String catagory = productobject.getString("Category");
                        String image = productobject.getString("Image");
                        int product_count = productobject.getInt("pr_count");
                        int sub_count = productobject.getInt("sub_count");
                        Product product = new Product(CategoryId,sub_count, product_count,catagory, image );
                        productList.add(product);

                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                        ll_main_layout.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                  //  Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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
//    @Override
//    public void onItemClick(int position) {
//        Product clickeditem = productList.get(position);
//        int id = productList.get(position).getId();
//        int sub_cata = productList.get(position).getSub_count();
//        int pr_count = productList.get(position).getPr_count();
//        if (sub_cata <= 0) {
//            Intent i = new Intent(getApplicationContext(), subincategory.class);
//            i.putExtra("id", id);
//            i.putExtra("catagory", clickeditem.getCatagory());
//            i.putExtra("image", clickeditem.getImage());
//            i.putExtra("sub_cat", sub_cata);
//            i.putExtra("pr_count", pr_count);
//            startActivity(i);
//        } else {
//
//            Intent i = new Intent(getApplicationContext(), Subcatagory.class);
//
//
//            i.putExtra("id", id);
//            i.putExtra("catagory", clickeditem.getCatagory());
//            i.putExtra("image", clickeditem.getImage());
//            i.putExtra("sub_cat", sub_cata);
//            i.putExtra("pr_count", pr_count);
//            startActivity(i);
//        }
//    }
}
