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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class add_address extends AppCompatActivity {
    Button add_address;
    EditText ed_name,ed_email,_ed_phone,ed_address,ed_pincode,ed_city;
    String Token;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ed_name=(EditText)findViewById(R.id.name);
        ed_email=(EditText)findViewById(R.id.email);
        _ed_phone=(EditText)findViewById(R.id.phone);
        ed_address=(EditText)findViewById(R.id.address);
        ed_pincode=(EditText)findViewById(R.id.pincode);
        ed_city=(EditText)findViewById(R.id.city);

        add_address=(Button)findViewById(R.id.checkout);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
       // Toast.makeText(getApplicationContext(),"id"+lid,Toast.LENGTH_LONG).show();

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
    private void address() {

//        loading.setVisibility(View.VISIBLE);
//        lnr1.setVisibility(View.GONE);

        final    String name=ed_name.getText().toString().trim();
        final  String email=ed_email.getText().toString().trim();
        final   String phone=_ed_phone.getText().toString().trim();
        final   String address=ed_address.getText().toString().trim();
        final   String pincode=ed_pincode.getText().toString().trim();
        final   String city=ed_city.getText().toString().trim();
        if(name.equals(""))
        {
            ed_name.setError("Enter name");
        }
        else if(!name.matches("^[A-Za-z]+$")){
           ed_name.setError("Enter name");
        }
        else if(phone.equals(""))
        {
            _ed_phone.setError("Enter phone number");
        }
        else if(email.equals(""))
        {
            ed_email.setError("Enter email");
        }

        else if(address.equals(""))
        {
            ed_address.setError("Enter address");
        }
        else if(city.equals(""))
        {
            ed_city.setError("Enter city");
        }
        else if(pincode.equals(""))
        {
            ed_pincode.setError("Enter Postal code");

        }
        else {


            String URL_CREATE_ACCOUNT = PATH +"addAddress?&name=" + name + "&phone=" + phone + "&email=" + email + "&address=" + address + "&city=" + city + "&postal=" + pincode;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                 //   Toast.makeText(getApplicationContext(), "res" + response, Toast.LENGTH_LONG).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
                        Intent i = new Intent(add_address.this, Address.class);
                        Toast.makeText(add_address.this, "Successfully added", Toast.LENGTH_LONG).show();


                        startActivity(i);
                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(add_address.this, "add address error" + e.toString(), Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(add_address.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        }
                    }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("Token", Token);

                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
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
