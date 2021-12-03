package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import app.eazyhomebrunei.com.adapter.address_fragment_adapter;
import app.eazyhomebrunei.com.model.addres_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Address extends AppCompatActivity {

    Button addre;
    RecyclerView recyclerView;
    List<addres_list> addresslist;
    private address_fragment_adapter adresadapter;
    String Token;
    int id;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        id=intent.getIntExtra("id",0);
       // userid=intent.getIntExtra("userid",0);
        // Toast.makeText(getApplicationContext(),"id"+id,Toast.LENGTH_LONG).show();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
     //   Toast.makeText(getApplicationContext(),"jjj"+Token,Toast.LENGTH_LONG).show();
        addre=(Button)findViewById(R.id.address);
        addre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),add_address.class);
                startActivity(i);
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        progresss();
        addresslist=new ArrayList<>();
        view_all_address();
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        left_arrow=(ImageView)findViewById(R.id.arrow);
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

    }
    private void view_all_address() {
         final String URL_FEATURED=PATH +"UserAddressList?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FEATURED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
progress.setVisibility(View.GONE);
//                   //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jsonObject.getJSONArray("data");
//
                    adresadapter = new address_fragment_adapter(getApplicationContext(), addresslist);
                    recyclerView.setAdapter(adresadapter);
                    // Toast.makeText(getApplicationContext(),"jjj"+adresadapter,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int id=productobject.getInt("id");
                        int customer_id=productobject.getInt("customer_id");
                        String name=productobject.getString("name");
                        String phone=productobject.getString("phone");
                        String email=productobject.getString("email");
                        String address=productobject.getString("address");
                        String state=productobject.getString("state");
                        String city=productobject.getString("city");
                        String postal_code=productobject.getString("postal_code");
                        String status="";
                        String date="";




                        addres_list fd_product=new addres_list(id,customer_id,name,phone,email,address,state,city,postal_code,status,date);
                        addresslist.add(fd_product);




                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
                    TextView nodata=findViewById(R.id.nodata);
                    nodata.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
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
}
