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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;


import app.eazyhomebrunei.com.adapter.Subcatagory_adapter;
import app.eazyhomebrunei.com.model.sub_cate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Subcatagory extends AppCompatActivity {
    RecyclerView recyclerView3;
    private Subcatagory_adapter ctadapter;

    List<sub_cate> productList;

    int id,sub_cata,pr_count;
    LinearLayout home,cart1,wishlist,profile,search;
    ImageView iv_share,arrow;
    TextView caption;
    int subin_count;
    TextView viewall;
    String catagory1;
    String Token;
    private ShimmerFrameLayout shimmer;
    private LinearLayout ll_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcatagory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shimmer = findViewById(R.id.shimmer);
        ll_main_layout= findViewById(R.id.ln);
        Intent intent=getIntent();
        final String imageUrl=intent.getStringExtra("image");
        id=intent.getIntExtra("id",0);
        sub_cata=intent.getIntExtra("sub_cat",0);
        pr_count=intent.getIntExtra("pr_count",0);
       catagory1=intent.getStringExtra("catagory");
        caption=findViewById(R.id.imageView0);
        viewall=findViewById(R.id.view_featured_product);
        caption.setText(catagory1);
        recyclerView3 = findViewById(R.id.recy4);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager3= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(layoutManager3);
        productList=new ArrayList<>();
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart1=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        arrow=(ImageView)findViewById(R.id.imageView5);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
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
        cart1.setOnClickListener(new View.OnClickListener() {
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

viewall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent( getApplicationContext(),viewallcategoryproducts.class);
        i.putExtra("id",id);
        i.putExtra("catagory",catagory1);
        startActivity(i);
    }
});

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent( getApplicationContext(),Home.class);

                startActivity(i);
            }
        });


        loadProducts();

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
    private void loadProducts() {
       final String URL_SUBCATAGORY=PATH +"ListAllSubCategory?CategoryId="+id+"&SubCategoryId="+sub_cata;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {


//
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("sub_cat");
                    ctadapter = new Subcatagory_adapter(getApplicationContext(), productList);
                    recyclerView3.setAdapter(ctadapter);
                    recyclerView3.setLayoutManager(new GridLayoutManager(Subcatagory.this, 2));




                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int catid=id;

                        int SubCategoryId=productobject.getInt("SubCategoryId");
                        String catagory=productobject.getString("SubCategory");
                        String image=productobject.getString("Image");
                        int product_count=productobject.getInt("pr_count");
                        subin_count=productobject.getInt("subin_count");
                        int subincatgory=0;


                        sub_cate product=new sub_cate(catid,SubCategoryId,product_count,catagory,image,subin_count,subincatgory);
                        productList.add(product);

                       // Toast.makeText(getApplicationContext(),"jjj"+image,Toast.LENGTH_LONG).show();
                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                        ll_main_layout.setVisibility(View.VISIBLE);

                    }

//
//
//
////                    }
                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    //e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("CategoryId", String.valueOf(id));


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
        //Toast.makeText(getApplicationContext(),"ji"+String.valueOf(id),Toast.LENGTH_LONG).show();

    }
}
