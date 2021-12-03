package app.eazyhomebrunei.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import app.eazyhomebrunei.com.Config.BaseURL;
import app.eazyhomebrunei.com.Fragment.my_coupon_fragment;
import app.eazyhomebrunei.com.Fragment.my_order_fragments;
import app.eazyhomebrunei.com.Fragment.settingfargment;


import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class my_profile extends AppCompatActivity {
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    ImageView profle,edit;
    private final int IMG_REQUEST=1;
    private  Bitmap bitmap;
    String Token;
    SharedPreferences prefs;
    TextView name,phn;
    CircleImageView prf;
    private ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        left_arrow=(ImageView)findViewById(R.id.arrow);
        profle=(ImageView)findViewById(R.id.prof);
        name=(TextView)findViewById(R.id.name);
        phn=(TextView)findViewById(R.id.ph);
        edit=(ImageView)findViewById(R.id.edit);
        prf=(CircleImageView)findViewById(R.id.prof);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        cartcount();
        /////////////// toolbarr///////////////////////////////////////////

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //////////////////////////////////////////////////////////
        /////////////////////////view pager////////////////////////////////////
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        prf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Editprofilenew.class);
                startActivity(i);
            }
        });

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
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Editprofilenew.class);
                startActivity(i);
            }
        });
        progresss();
        view_details();

        ///////////////////////////////////////////////////////////////////////////
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
    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"MyProfile?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
progress.setVisibility(View.GONE);
                    //Toast.makeText(getActivity(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONObject productobject=jk.getJSONObject("profile");
                    //Toast.makeText(getApplicationContext(),"jjj"+productobject,Toast.LENGTH_LONG).show();
//
//
//
////
//                    for (int i = 0; i <ar.length(); i++)
//                    {
                    String name1=productobject.getString("name");

                    String phone1=productobject.getString("phone");
                    String email1=productobject.getString("email");
                    String address1=productobject.getString("address");
                    String image=productobject.getString("image");
                    //int status=productobject.getInt("Status");
//                        String price=productobject.getString("Price");
       //Toast.makeText(getApplicationContext(),"img"+image,Toast.LENGTH_LONG).show();
                    Picasso with = Picasso.with(getApplicationContext());
                    StringBuilder sb = new StringBuilder();
                    sb.append(BaseURL.PROFILE_IMAGE);
                    sb.append(image);
                    with.load(sb.toString()).placeholder(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).error(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).into(profle);

                    name.setText(name1);
                    phn.setText(phone1);
                   // phn.setText(phone1);
                   // Toast.makeText(getApplicationContext(),"img"+image,Toast.LENGTH_LONG).show();
//
//
//
//                    }
//


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
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && requestCode==RESULT_OK && data!=null)
        {
            Uri path=data.getData();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                profle.setImageBitmap(bitmap);
               // Toast.makeText(getApplicationContext(),"nn"+bitmap,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(my_profile my_profile, FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    my_order_fragments tab1 = new my_order_fragments();//theatre
                    return tab1;
                case 1:
                    my_coupon_fragment tab2 = new my_coupon_fragment();//theatre
                    return tab2;
                case 2:
                    settingfargment tab3 = new settingfargment();//theatre
                    return tab3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:return "My order";
                case 1:return "My Coupons";
                case 2:return "settings";


            }
            return null;
        }

    }


}
