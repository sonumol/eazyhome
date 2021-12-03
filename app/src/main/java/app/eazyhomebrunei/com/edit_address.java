package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class edit_address extends AppCompatActivity {
    Button add_address;
    EditText ed_name,ed_email,ed_phone,ed_address,ed_pincode,ed_city;
    String lid;
    int id;
    int  userid;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    private ProgressBar progress,progress1;
    String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token = pref.getString("Token", null);
        cartcount();
        ed_name=(EditText)findViewById(R.id.name);
        ed_email=(EditText)findViewById(R.id.email);
        ed_phone=(EditText)findViewById(R.id.phone);
        ed_address=(EditText)findViewById(R.id.address);
        ed_pincode=(EditText)findViewById(R.id.pincode);
        ed_city=(EditText)findViewById(R.id.city);

        add_address=(Button)findViewById(R.id.checkout);


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
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit_addres();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

       id=intent.getIntExtra("id",0);
          userid=intent.getIntExtra("userid",0);
       // Toast.makeText(getApplicationContext(),"id"+id,Toast.LENGTH_LONG).show();
        view_address();

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
    private void view_address() {



        String URL_CREATE_ACCOUNT=PATH +"EditAddress?id="+id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progress.setVisibility(View.GONE);
               // Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                   //JSONObject jk=jsonObject.getJSONObject("data");
                   JSONArray ar=jsonObject.getJSONArray("data");
                    //Toast.makeText(getApplicationContext(),"res"+ar,Toast.LENGTH_LONG).show();

                    for (int i = 0; i <ar.length(); i++) {
                        JSONObject productobject = ar.getJSONObject(i);

                        String name = productobject.getString("name");
                        String phone = productobject.getString("phone");
                        String email = productobject.getString("email");

                        String address = productobject.getString("address");
                        String state = productobject.getString("state");
                        String city = productobject.getString("city");
                        String postal_code = productobject.getString("postal_code");
                        ed_name.setText(name);
                        ed_phone.setText(phone);
                        ed_email.setText(email);
                        ed_address.setText(address);
                        //ed_s.setText(state);
                        ed_city.setText(city);
                        ed_pincode.setText(postal_code);


//}

                    }
//
////
//
//



                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(edit_address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(ed.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(edit_address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("id", String.valueOf(id));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void edit_addres() {

//        loading.setVisibility(View.VISIBLE);
//        lnr1.setVisibility(View.GONE);

        final    String name=ed_name.getText().toString().trim();
        final  String email=ed_email.getText().toString().trim();
        final   String phone=ed_phone.getText().toString().trim();
        final   String address=ed_address.getText().toString().trim();
        final   String pincode=ed_pincode.getText().toString().trim();
        final   String city=ed_city.getText().toString().trim();

        String URL_Edit_ACCOUNT=PATH +"UpdateAddress?id="+id+"&name="+name+"&phone="+phone+"&email="+email+"&address="+address+"&city="+city+"&postal="+pincode;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_Edit_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
                    Intent i = new Intent(edit_address.this, Address.class);
                    Toast.makeText(edit_address.this,"Edited Successfully",Toast.LENGTH_LONG).show();


                    startActivity(i);
                    finish();




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(edit_address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(edit_address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("id", String.valueOf(id));
                params.put("name",name);
                params.put("phone",phone);
                params.put("email",email);
                params.put("address",address);
                params.put("city",city);
                params.put("postal",pincode);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
