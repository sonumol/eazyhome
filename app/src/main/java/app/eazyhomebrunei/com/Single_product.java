package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.view.View;
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
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.facebook.shimmer.ShimmerFrameLayout;

import app.eazyhomebrunei.com.Fragment.ExapleDialog;



import app.eazyhomebrunei.com.adapter.Related_products_adapter;
import app.eazyhomebrunei.com.adapter.Singleproduct_imageAdapter;
import app.eazyhomebrunei.com.model.related_pro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Single_product extends AppCompatActivity implements Related_products_adapter.CartItemClickListener{
    String Token;
    String imageUrl,pname,short_disc,detailed_description,price,offer_price;
    int id,status,flag,wishid,ip;
    TextView ed_pname,ed_price,ed_offer,offer,referen;
    LinearLayout home,cart1,wishlist,profile,search;
    ImageView iv_share,arrow;
    ToggleButton tb_wishlist;
    Button cart,whatspp;
    ViewPager viewPager;
    LinearLayout sliderdots;
    private  int dotcount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderimg;
    Singleproduct_imageAdapter viewPagerAdapter;
    SharedPreferences pref;
    Intent intent;
    ReadMoreTextView textview;
    RecyclerView recyclerView;
    List<related_pro> fdproductlist;
    private Related_products_adapter fdadapter;
    String whatsp;
    int pid;
    String reference;
    String   url_text;


//    SwipeRefreshLayout refreshLayout;
private ShimmerFrameLayout shimmer;
    private LinearLayout ll_main_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product);
       pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);

        arrow=(ImageView)findViewById(R.id.arrow);
        ed_pname=(TextView)findViewById(R.id.pname);
       // ed_short=(TextView)findViewById(R.id.ed_short);
        ed_price = (TextView) findViewById(R.id.price);
        ed_offer = (TextView) findViewById(R.id.offer_price);
       // ed_desc = (TextView) findViewById(R.id.desc);
        offer=(TextView)findViewById(R.id.offer);
        referen=(TextView)findViewById(R.id.reference);
cartcount();
        whatspp=(Button)findViewById(R.id.whatsapp);
        tb_wishlist = (ToggleButton) findViewById(R.id.tb_wishlist);
        cart=(Button)findViewById(R.id.cart);
        iv_share=(ImageView)findViewById(R.id.iv_share);
        textview=findViewById(R.id.text_view);

        shimmer = findViewById(R.id.shimmer);
        ll_main_layout= findViewById(R.id.ln);//
//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refres);
//        this.refreshLayout = swipeRefreshLayout;
//        swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
//        this.refreshLayout.setColorSchemeResources(R.color.swprefresh1, R.color.swprefresh2, R.color.swprefresh3);



        rq=Volley.newRequestQueue(this);
        sliderimg=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        sliderdots=(LinearLayout)findViewById(R.id.sliderdots);


//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progress.setVisibility(View.GONE);
//                }
//            },5000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for(int i=0;i<dotcount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dots));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new Single_product.myTimerTask(),2000,4000);


        Intent intent=getIntent();
        imageUrl=intent.getStringExtra("image");
        id=intent.getIntExtra("id",0);
        ip=intent.getIntExtra("id",0);
      //  Toast.makeText(getApplicationContext(),"id"+id,Toast.LENGTH_LONG).show();
        pname=intent.getStringExtra("pname");
        short_disc=intent.getStringExtra("short_disc");
        detailed_description=intent.getStringExtra("detailed_description");
//        status=intent.getIntExtra("status",0);
//     Toast.makeText(getApplicationContext(),"id"+status,Toast.LENGTH_LONG).show();

       // flag=intent.getIntExtra("flag", 0);
        price =intent.getStringExtra("price");
        offer_price=intent.getStringExtra("offer_price");
        wishid=intent.getIntExtra("wishid",0);

        Send_req();
        view_details();
        home=(LinearLayout)findViewById(R.id.home_layout);
        cart1=(LinearLayout)findViewById(R.id.cart_layout);
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
                String w1=whatsp;
            String w=w1.replace("+", "");

                String smsNumber = w;//twi
//                Intent sendIntent = new Intent("android.intent.action.MAIN");
////                sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
////
////                sendIntent.putExtra("jid",     PhoneNumberUtils.stripSeparators(smsNumber)+"@s.whatsapp.net");//phone number without "+" prefix
//
//
//                startActivity(sendIntent);
//                //Toast.makeText(getApplicationContext(),"h"+w,Toast.LENGTH_LONG).show();

                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("jid",     PhoneNumberUtils.stripSeparators(smsNumber)+"@s.whatsapp.net");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Hello EAZYHOME I would like to order "+pname+"("+reference+")");


                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(whatsappIntent);





            }
        });
//        private void openWhatsApp(String smsNumber) {
//            Intent sendIntent = new Intent(Intent.ACTION_SEND);
//            sendIntent.setType("text/plain");
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, This is " + PreferenceManager.get(this, Constants.USERNAME));
//            sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
//            sendIntent.setPackage("com.whatsapp");
//            if (sendIntent.resolveActivity(getPackageManager()) == null) {
//                Toast.makeText(this, "Error/n", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            startActivity(sendIntent);
//        }



        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareProduct();
            }
        });

        recyclerView = findViewById(R.id.recy1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager2);

        fdproductlist=new ArrayList<>();
        Related_products();

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

                        count.setText(String. valueOf ( count1 )) ;
                    ImageView imageView4=findViewById(R.id.imageView4);
                  //  Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();
                    imageView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                            startActivity(i);
                        }
                    });


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
//    public void onResume() {
//        super.onResume();
//        this.refreshLayout.post(new Runnable() {
//            public void run() {
//                Single_product.this.refreshLayout.setRefreshing(true);
//                Single_product.this.view_details();
////                Home.this.offfer_product();
////                Home.this.featured_product();
////                Home.this.loadProducts();
//            }
//        });
//    }
//    @Override
//    public void onRefresh() {
//        view_details();
//
//    }
    private void Related_products() {

        final String URL_FEATURED=PATH +"SingleProduct?pid="+ip;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FEATURED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("related_products");
                    //Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();

                    fdadapter = new Related_products_adapter(getApplicationContext(), fdproductlist);
                    recyclerView.setAdapter(fdadapter);
                    fdadapter.setOnItemClickListener(Single_product.this);
                    // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <10; i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
//                        String short_descrption=productobject.getString("short_description");
//                        String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("Image");
                         status=productobject.getInt("Status");
                        int flag=productobject.getInt("Flag");
                        String price=productobject.getString("Price");
                        String offer_price=productobject.getString("OfferPrice");
                        int wishid=productobject.getInt("wishId");


                        related_pro fd_product=new related_pro(id,pname,image,price,offer_price,status,flag,wishid);
                        fdproductlist.add(fd_product);




                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
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

    public  class  myTimerTask extends TimerTask {

        @Override
        public void run() {
            Single_product.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0)
                    {
                        viewPager.setCurrentItem(1);
                    }
                    else  if(viewPager.getCurrentItem()==1)
                    {
                        viewPager.setCurrentItem(2)
                        ;
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    private void Send_req() {
        final String URL_SUBCATAGORY=PATH +"SingleProduct?pid="+ip;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

//
                    JSONObject jsonObject = new JSONObject(response);
                   // Toast.makeText(getApplicationContext(),"jjj"+jsonObject,Toast.LENGTH_LONG).show();

                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("product_images");



                    for (int i = 0; i <ar.length(); i++)
                    {
                        SliderUtils sliderUtils=new SliderUtils();

                        JSONObject productobject = ar.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(productobject.getString("image"));

                        sliderimg.add(sliderUtils);


                    }
                    viewPagerAdapter=new Singleproduct_imageAdapter(sliderimg,Single_product.this);
                    viewPager.setAdapter(viewPagerAdapter);
                    dotcount=viewPagerAdapter.getCount();
                    dots=new ImageView[dotcount];
                    for (int i=0;i<dotcount;i++)
                    {
                        dots[i]=new ImageView(Single_product.this);
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dots));
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(8,0,8,0);
                        sliderdots.addView(dots[i],params);
                    }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

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


    }
    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"SingleProduct?pid="+id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
//                    Single_product.this.refreshLayout.setRefreshing(false);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("single_products");
                    //  Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                    shimmer.stopShimmer();
                    shimmer.setVisibility(View.GONE);
                    ll_main_layout.setVisibility(View.VISIBLE);
//
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int wishlist_id=productobject.getInt("wishId");

                         pid=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
                        String short_descrption=productobject.getString("short_description");
                        String detailed_description=productobject.getString("detailed_description");
                       String discount=productobject.getString("discount");
                      status=productobject.getInt("Status");
                      flag=productobject.getInt("Flag");
                        String price=productobject.getString("Price");
                        String offer_price=productobject.getString("OfferPrice");
                     reference=productobject.getString("reference");
                        whatsp=productobject.getString("Whatsapp");
                        url_text =productobject.getString("url_text");

if(offer_price.equals("0.00"))
{
    ed_price.setText("BND."+price);
    ed_price.setTextColor(Color.BLACK);
}
else
{
    ed_offer.setText("BND."+offer_price+" ");
    ed_price.setText("BND."+price);
    offer.setText(" "+discount);

    ed_price.setPaintFlags(ed_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

}


                        ed_pname.setText(pname);
//                        textview.setText(short_descrption);
                        textview.setText(Html.fromHtml(detailed_description));

                       // ed_desc.setText(detailed_description);
                        referen.setText(reference);
                        if (status == 0) {
                            cart.setText("ADD TO CART");

                            // holder.ll_add_to_cart.setTag(allProducts);
                            cart.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
//                                    Toast.makeText(getApplicationContext(),"ADD TO CART",Toast.LENGTH_LONG).show();
//                                    cart.setText("IN CART");
//                                    // all_featured_pro.getInstance().setSelectedAllProducts((product) view.getTag());
//                                    Single_product.this.addToCart();
//                                    cartcount();

                                    if (cart.getText().toString().matches("IN CART")) {
                                      showDialog();
                                    } else {

                                                    Single_product.this.addToCart();

                                                  cart.setText("IN CART");


                                            }

                                    }


                            });
                        } else if (status == 1) {
                            cart.setText("IN CART");
                            cart.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    showDialog();
                                }
                            });
                        }



                        if (flag == 1) {
                            tb_wishlist.setChecked(true);
                            tb_wishlist.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_black));
                            tb_wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    // Toast.makeText(getApplicationContext(),"flag"+flag,Toast.LENGTH_LONG).show();


                                    tb_wishlist.setChecked(false);
                                    tb_wishlist.setBackgroundDrawable(Single_product.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_border_black));
                                    remove_wishlist();
                                }
                            });
                        } else {
                            tb_wishlist.setChecked(false);
                            tb_wishlist.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_border_black));

                            tb_wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    // Toast.makeText(getApplicationContext(),"flag"+flag,Toast.LENGTH_LONG).show();


                                        tb_wishlist.setChecked(true);
                                        tb_wishlist.setBackgroundDrawable(Single_product.this.getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_black));
                                        addToWishList();

                                    }


                            });

                        }


                    }
                    String gd_total = jk.getString("product_images");
                    //Toast.makeText(getApplicationContext(),""+status,Toast.LENGTH_LONG).show();


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
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void addToCart() {
        cartcount();
        String URL_ADD_TO_CART=PATH +"addTocart?pid="+id+"&quantity="+1;
        // Toast.makeText(getApplicationContext(),"res"+id,Toast.LENGTH_LONG).show();


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
        ExapleDialog exapleDialog=new ExapleDialog();
        exapleDialog.show(getSupportFragmentManager(),"exampledialog");
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
       // Toast.makeText(getApplicationContext(),"flag"+wishid,Toast.LENGTH_LONG).show();


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
            sb.append(" at best price from https://www.eazyhomebrunei.com/");
            sb.append(this.url_text);
            sendIntent.putExtra("android.intent.extra.TEXT", sb.toString());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
