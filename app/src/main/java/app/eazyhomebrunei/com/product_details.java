package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.Config.BaseURL;



import app.eazyhomebrunei.com.adapter.MainAdapter;
import app.eazyhomebrunei.com.adapter.product_cust_swipe_adapter;
import app.eazyhomebrunei.com.model.featured_products;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class product_details extends AppCompatActivity {
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    List<String>listGroup;
    MainAdapter adapter1;
    HashMap<String,List<String>>listitem;
    private List<featured_products> listItems;
    private Timer timer;
    private  int current_position=0;
    private LinearLayout dotslayout;
    private  int custom_postion=0;
    ViewPager viewPager;
    product_cust_swipe_adapter adapter2;
     private AppBarConfiguration mAppBarConfiguration;
     TextView ed_pname,ed_short,ed_price,ed_offer,ed_desc;
    Button cart,whatspp;
    int id,status,flag,wishid;
    String imageUrl,pname,short_disc,detailed_description,price,offer_price;
    ImageView img1,iv_share;
    ToggleButton tb_wishlist;
    String Token;
    ImageView arrow;
    LinearLayout home,cart1,wishlist,profile,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrow=(ImageView)findViewById(R.id.arrow);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", Context.MODE_PRIVATE);
        Token=pref.getString("Token", null);
        // tb_wishlist.setOnCheckedChangeListener(null);

        Intent intent=getIntent();
        imageUrl=intent.getStringExtra("image");
        id=intent.getIntExtra("id",0);
        pname=intent.getStringExtra("pname");
        short_disc=intent.getStringExtra("short_disc");
        detailed_description=intent.getStringExtra("detailed_description");
        status=intent.getIntExtra("status",0);
        flag=intent.getIntExtra("flag", 0);
        price =intent.getStringExtra("price");
        offer_price=intent.getStringExtra("offer_price");
        wishid=intent.getIntExtra("wishid",0);
        ed_pname=(TextView)findViewById(R.id.pname);
      //  ed_short=(TextView)findViewById(R.id.ed_short);
        ed_price = (TextView) findViewById(R.id.price);
        ed_offer = (TextView) findViewById(R.id.offer_price);
        ed_desc = (TextView) findViewById(R.id.desc);
        //img1=(ImageView)findViewById(R.id.im2) ;
        whatspp=(Button)findViewById(R.id.whatsapp);
        tb_wishlist = (ToggleButton) findViewById(R.id.tb_wishlist);
        cart=(Button)findViewById(R.id.cart);
     //Toast.makeText(getApplicationContext(),"flag"+wishid,Toast.LENGTH_LONG).show();


        ed_price.setText(price);
        ed_price.setPaintFlags(ed_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        ed_pname.setText(pname);
        ed_short.setText(short_disc);
        ed_offer.setText(offer_price);
        ed_desc.setText(detailed_description);

       // Toast.makeText(getApplicationContext(),"url"+imageUrl,Toast.LENGTH_LONG).show();

        Picasso with = Picasso.with(getApplicationContext());
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.PRODUCT_IMG_PATH);
        sb.append(imageUrl);
        with.load(sb.toString()).placeholder(getApplicationContext().getResources().getDrawable(R.drawable.ic_error_place_holder)).error(getApplicationContext().getResources().getDrawable(R.drawable.ic_error_place_holder)).into(img1);

        home=(LinearLayout)findViewById(R.id.home_layout);
        cart1=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search);

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



        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent( getApplicationContext(),Home.class);
                startActivity(i);
            }
        });



        whatspp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String smsNumber = "918281233725";
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                sendIntent.putExtra("jid",     PhoneNumberUtils.stripSeparators(smsNumber)+"@s.whatsapp.net");//phone number without "+" prefix
                startActivity(sendIntent);
            }
        });


        if (flag == 1) {
          tb_wishlist.setChecked(true);
           tb_wishlist.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_black));
        } else {
           tb_wishlist.setChecked(false);
           tb_wishlist.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_border_black));
        }
      tb_wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(getApplicationContext(),"flag"+flag,Toast.LENGTH_LONG).show();

                if (b) {
                    tb_wishlist.setChecked(true);
                 tb_wishlist.setBackgroundDrawable(product_details.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_black));
                    product_details.this.addToWishList();
                    return;
                }
               tb_wishlist.setChecked(false);
               tb_wishlist.setBackgroundDrawable(product_details.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_border_black));
                product_details.this.remove_wishlist();
            }
        });
        if (status == 0) {
            cart.setText("ADD TO CART");

            // holder.ll_add_to_cart.setTag(allProducts);
           cart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"ADD TO CART",Toast.LENGTH_LONG).show();

                    // all_featured_pro.getInstance().setSelectedAllProducts((product) view.getTag());
                    product_details.this.addToCart();
                }
            });
        } else if (status == 1) {
         cart.setText("IN CART");
         cart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    product_details.this.showDialog();
                }
            });
        }

iv_share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        shareProduct();
    }
});



        /////////////////////////////////////// slide show/////////////////////////////////////////////

        dotslayout=findViewById(R.id.dotcontainer);
        preparedots(custom_postion++);


      ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        recyclerView = findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager2);

        listItems=new ArrayList<>();

        for (int i=0;i<10;i++) {
            featured_products listitem1= new featured_products(
                    "heading" ,
                    "hcggcvcggc");


            listItems.add(listitem1);
        }
//     3
        ///////////////////////////////button click/////////////////////////
        cart=(Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),add_to_cart.class);
                startActivity(i);
            }
        });

    }
    private void addToCart() {
        String URL_ADD_TO_CART=PATH +"addTocart?pid="+id+"&quantity="+1;
        // Toast.makeText(mcontext,"res"+lid,Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject(response);
//


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    // Toast.makeText(Address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    public void showDialog() {
        String title = "Note!";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(this.getApplicationContext().getResources().getColor(R.color.colorPrimary));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
        builder1.setTitle(ssBuilder);
        builder1.setMessage("Product is already in cart.");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }
    private void addToWishList() {



        String URL_ADD_TO_WISHLIST=PATH +"AddToWishlist?pid="+id;
        //Toast.makeText(mcontext,"res"+lid,Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ADD_TO_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject(response);
//                    boolean status = jsonObject.getBoolean("status");
//                   // String string = jsonObject.getString("message");
//                    if (status) {
//                        Snackbar snackbar = Snackbar.make(featured_products_adapter.this.mcontext.findViewById(16908290), (CharSequence) "Added to WishList", 0);
//                        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(featured_products_adapter.this.mcontext.getResources().getColor(R.color.white));
//                        snackbar.show();
//                        ((featured_pro) featured_products_adapter.this.fdproductlist.get(position)).setStatus(1);
//                        featured_products_adapter.this.notifyDataSetChanged();
//                    } else {
//                        Toast.makeText(mcontext,"Failed to add in WishList",Toast.LENGTH_LONG).show();
//                    }
//                    int position = fdproductlist.indexOf(pid);
//                    fdproductlist.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, fdproductlist.size());

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    // Toast.makeText(Address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


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
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    private void remove_wishlist() {



        String URL_DELETE_ADDRESS=PATH +"RemoveWishlistProduct?wishId="+wishid;
        Toast.makeText(getApplicationContext(),"flag"+wishid,Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext(),"res"+response,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject(response);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    // Toast.makeText(Address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(Address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("wishId", String.valueOf(wishid));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
    public void shareProduct() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            StringBuilder sb = new StringBuilder();
            sb.append("Buy ");
            sb.append(this.pname);
            sb.append(" at best price from https://www.pitcostore.com/product_view/");
            sb.append(this.id);
            sendIntent.putExtra("android.intent.extra.TEXT", sb.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    private void initListData() {
        listGroup.add("Product Description");

        String [] array;
        List<String> list=new ArrayList<>();
        array=getResources().getStringArray(R.array.group1);
        for(String item:array)
        {
            list.add(item);
        }



        listitem.put(listGroup.get(0),list);

        adapter1.notifyDataSetChanged();

    }
    private  void preparedots(int current_position)
    {
        if(dotslayout.getChildCount()>0)
            dotslayout.removeAllViews();
        ImageView dots[]=new ImageView[3];
        for(int i=0;i<3;i++)
        {
            dots[i]=new ImageView(this);
            if(i==current_position)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            dotslayout.addView(dots[i],layoutParams);
        }
    }

}
