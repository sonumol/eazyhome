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


import app.eazyhomebrunei.com.adapter.subincategoryadapter;
import app.eazyhomebrunei.com.model.sub_cate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class subincategory extends AppCompatActivity {
    RecyclerView recyclerView3;
    private subincategoryadapter ctadapter;

    List<sub_cate> productList;

    int id,sub_cata,pr_count;
    LinearLayout lnt_no_data;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;
    TextView caption;
    int catid;
    TextView viewall;
    String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nocatagory);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String imageUrl=intent.getStringExtra("image");
        final String  capt=intent.getStringExtra("catagory");

        catid=intent.getIntExtra("catid",0);
        id=intent.getIntExtra("id",0);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
cartcount();
       pr_count=intent.getIntExtra("pr_count",0);
      //  Toast.makeText(getApplicationContext(),"idddd"+pr_count,Toast.LENGTH_LONG).show();
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        left_arrow=(ImageView)findViewById(R.id.arrow);
        caption=findViewById(R.id.imageView0);
        caption.setText(capt);
        viewall=findViewById(R.id.view_featured_product);
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

        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent( getApplicationContext(),viewallsubincategoryproducts.class);
                i.putExtra("sub_cat",id);
                i.putExtra("category",capt);
                startActivity(i);
            }
        });
        recyclerView3 = findViewById(R.id.recy4);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager3= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(layoutManager3);
        productList=new ArrayList<>();
       // lnt_no_data=(LinearLayout)findViewById(R.id.ll_no_data) ;

        loadProducts();
      //  showEmptyLayout();

    }
    private void progresss() {
        this.progress=findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

    }
//
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
        final String URL_SUBCATAGORY=PATH+"ListAllSubinCategory?CategoryId="+catid+"&SubCategoryId="+id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.setVisibility(View.GONE);
               //Toast.makeText(getApplicationContext(),"jjj"+URL_SUBCATAGORY,Toast.LENGTH_LONG).show();
                try {


//
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("subin_cat");
                    ctadapter = new subincategoryadapter(getApplicationContext(), productList);
                    recyclerView3.setAdapter(ctadapter);
                    recyclerView3.setLayoutManager(new GridLayoutManager(subincategory.this, 2));




                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int categoryid=catid;

                        int SubinCategoryId=productobject.getInt("SubinCategoryId");
                        String catagory=productobject.getString("SubinCategory");
                        String image=productobject.getString("Image");
                        int product_count=productobject.getInt("pr_count");
                        int subcount=0;
                      int  subcategoryid=id;

                        sub_cate product=new sub_cate(categoryid,SubinCategoryId,product_count,catagory,image,subcount,subcategoryid);
                        productList.add(product);

                       // Toast.makeText(getApplicationContext(),"jjj"+image,Toast.LENGTH_LONG).show();



                    }
//
//
//
////                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
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