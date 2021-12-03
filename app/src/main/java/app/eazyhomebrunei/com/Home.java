package app.eazyhomebrunei.com;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import app.eazyhomebrunei.com.Config.BaseURL;
import app.eazyhomebrunei.com.adapter.Custome_swipe_adapter;
import app.eazyhomebrunei.com.adapter.Latest_products_adapter;
import app.eazyhomebrunei.com.adapter.ViewPagerAdapter;
import app.eazyhomebrunei.com.adapter.catagory_adapter;
import app.eazyhomebrunei.com.adapter.featured_products_adapter;
import app.eazyhomebrunei.com.adapter.offer_products_adapter;
import app.eazyhomebrunei.com.model.Product;
import app.eazyhomebrunei.com.model.featured_pro;
import app.eazyhomebrunei.com.model.featured_products;

import app.eazyhomebrunei.com.model.latest_products;
import app.eazyhomebrunei.com.model.offer_product;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Home extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, featured_products_adapter.CartItemClickListener,
        offer_products_adapter.CartItemClickListener,Latest_products_adapter.CartItemClickListener {

    Custome_swipe_adapter adapter1;
    private AppBarConfiguration mAppBarConfiguration;
    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    private RecyclerView.Adapter adapter;
    private List<featured_products> listItems;
    private List<Slide>slideList=new ArrayList<>();
     private Timer timer;
     private  int current_position=0;
     private LinearLayout dotslayout;
     private  int custom_postion=0;
     TextView view_categories,view_featured,view_offer,view_latest,name,phone;
     LinearLayout home,cart,wishlist,profile,search;
     private catagory_adapter ctadapter;
     List<Product>productList;
     List<featured_pro>fdproductlist;
     private featured_products_adapter fdadapter;
      List<offer_product>offproductlist;
      private offer_products_adapter ofadapter;
      List<latest_products>latest_productlist;
     private Latest_products_adapter latest_adapter;
     String phnumber;
     String Token;
     private ProgressBar progress,progress1;




    ViewPager viewPager;
    LinearLayout sliderdots;
    private  int dotcount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderimg;
    ViewPagerAdapter viewPagerAdapter;

    SwipeRefreshLayout refreshLayout;
    CircleImageView image;
    private static final String URL_CATAGORY=PATH +"ListAllCategory";
//    private static final String URL_FEATURED="AllFeaturedProducts";
//    private static final String URL_OFFER="OfferProducts";


    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    static int notificationCount = 0 ;
    ImageView cartcount;
    TextView count;
    String count1;
    private FrameLayout fl_container;
    private ShimmerFrameLayout shimmer;
    private ConstraintLayout ll_main_layout;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            Toolbar toolbar = findViewById(R.id.toolbar);
            cartcount=findViewById(R.id.iv_whatsapp);
           // count=findViewById(R.id.cou);
            fl_container = findViewById(R.id.fl_container);
            shimmer = findViewById(R.id.shimmer);
          ll_main_layout= findViewById(R.id.ln);

            setSupportActionBar(toolbar);
            SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
            Token=pref.getString("Token", null);
          // Toast.makeText(getApplicationContext(),"idsss"+lid,Toast.LENGTH_LONG).show();

            SharedPreferences pref2 = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref2.edit();

            editor.putBoolean("islogged",true);
          // Save the changes in SharedPreferences
            editor.apply();
            //createNotification();
            cartcount();

            SharedPreferences pref1 = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
            phnumber=pref1.getString("number", null);
//            SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//            lid=pref.getString("lid", null);
//            Toast.makeText(getApplicationContext(),"idsss"+lid,Toast.LENGTH_LONG).show();


            SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refres);
            this.refreshLayout = swipeRefreshLayout;
            swipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) this);
            this.refreshLayout.setColorSchemeResources(R.color.swprefresh1, R.color.swprefresh2, R.color.swprefresh3);


            /////////////////////////// side bar///////////////////////
            final DrawerLayout drawer = findViewById(R.id.drawer_layout);


            final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);

            toggle.syncState();
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.getCheckedItem().setChecked(false);

            Menu menu = navigationView.getMenu();

            MenuItem tools= menu.findItem(R.id.communicate);
            SpannableString s = new SpannableString(tools.getTitle());
            s.setSpan(new TextAppearanceSpan(this, R.style.TextAppearance44), 0, s.length(), 0);
            tools.setTitle(s);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   item.setChecked(true);
                    String str = "android.intent.action.VIEW";
                   int menu_id=item.getItemId();
                   switch (menu_id){

                       case R.id.nav_home:
                           Intent i=new Intent(getApplicationContext(),Home.class);
                           startActivity(i);
                           break;
                       case R.id.nav_offer:
                           Intent i1=new Intent(getApplicationContext(),view_all_offerproduct.class);
                           startActivity(i1);
                           break;

                       case R.id.nav_wishlist:
                           Intent im=new Intent(getApplicationContext(),Vew_my_wishlist.class);
                           startActivity(im);
                           break;
                       case R.id.nav_cart:
                           Intent i2=new Intent(getApplicationContext(),view_my_cart.class);
                           startActivity(i2);
                           break;
                       case R.id.nav_delivery:
                           Intent i3=new Intent(getApplicationContext(),delivery_availability.class);
                           startActivity(i3);
                           break;
                       case R.id.nav_adress:
                           Intent i4=new Intent(getApplicationContext(),Address.class);
                           startActivity(i4);
                           break;
                       case R.id.nav_coupons:
                           Intent i5=new Intent(getApplicationContext(),my_coupons.class);
                           startActivity(i5);
                           break;
                       case R.id.nav_order:
                           Intent i6=new Intent(getApplicationContext(),my_order.class);
                           startActivity(i6);
                           break;
                       case R.id.nav_profile:
                           Intent i7=new Intent(getApplicationContext(),my_profile.class);
                           startActivity(i7);
                           break;
                       case R.id.nav_contact:
                           Intent i8=new Intent(getApplicationContext(),Contact.class);
                           startActivity(i8);
                           break;
                       case R.id.nav_about:
                           Intent i9=new Intent(getApplicationContext(),Aboutus.class);
                           startActivity(i9);
                           break;
                       case R.id.nav_share:
                           shareApp();
                           break;
                       case R.id.nav_Rate:
                           String appPackageName = getPackageName();
                           try {
                               StringBuilder sb = new StringBuilder();
                               sb.append("market://details?id=");
                               sb.append(appPackageName);
                               startActivity(new Intent(str, Uri.parse(sb.toString())));
                               break;
                           } catch (ActivityNotFoundException e) {
                               StringBuilder sb2 = new StringBuilder();
                               sb2.append("https://play.google.com/store/apps/details?id=");
                               sb2.append(appPackageName);
                               startActivity(new Intent(str, Uri.parse(sb2.toString())));
                               break;
                           }

                       case R.id.nav_logout:
                           showDialog();
                           break;

                   }


                   drawer.closeDrawers();
                    return true;
                }
            });
            View header = navigationView.getHeaderView(0);
            name = (TextView) header.findViewById(R.id.gname);
            phone = (TextView) header.findViewById(R.id.gphone);
             image=header.findViewById(R.id.img);
            //progresss();
            view_details();




//            navigationView.setNavigationItemSelectedListener(this);
///////////////////////////////////////////////////////////////////////////////////////////////////////////
            view_categories=(TextView) findViewById(R.id.view_category);
            view_featured=(TextView) findViewById(R.id.view_featured_product);
            view_offer=(TextView) findViewById(R.id.offer_products);
            view_latest=(TextView) findViewById(R.id.latest_products);
            home=(LinearLayout)findViewById(R.id.home_layout);
            cart=(LinearLayout)findViewById(R.id.cart_layout);
            wishlist=(LinearLayout)findViewById(R.id.wish_layout);
            profile=(LinearLayout)findViewById(R.id.profile_layout);
            search=(LinearLayout)findViewById(R.id.search1);


            ////////////////footer icons///////////////



/////////////////////////////////////////////////////////////
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i=new Intent(getApplicationContext(),Home.class);
                    startActivity(i);
                }
            });
            view_categories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i=new Intent(getApplicationContext(),view_allcategoriesproduct.class);
                    startActivity(i);
                }
            });
            view_featured.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),view_all_featuredproduct.class);
                    startActivity(i);
                }
            });
            view_offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),view_all_offerproduct.class);
                    startActivity(i);
                }
            });
            view_latest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getApplicationContext(),view_all_latestproduct.class);
                    startActivity(i);
                }
            });
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView chngcolr=(ImageView)findViewById(R.id.iv_home);
                    int light= Color.parseColor("#f7bbc7");
                    chngcolr.setColorFilter(light);

                    Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                    startActivity(i);
                }
            });
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView chngcolr=(ImageView)findViewById(R.id.iv_home);
                    int light= Color.parseColor("#f7bbc7");
                    chngcolr.setColorFilter(light);
                    Intent i=new Intent(getApplicationContext(),search_product.class);
                    startActivity(i);
                }
            });
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView chngcolr=(ImageView)findViewById(R.id.iv_home);
                    int light= Color.parseColor("#f7bbc7");
                    chngcolr.setColorFilter(light);
                    Intent i=new Intent(getApplicationContext(),my_profile.class);
                    startActivity(i);

                }
            });
            wishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView chngcolr=(ImageView)findViewById(R.id.iv_home);
                    int light= Color.parseColor("#f7bbc7");
                    chngcolr.setColorFilter(light);

                    Intent i=new Intent(getApplicationContext(),Vew_my_wishlist.class);
                    startActivity(i);
                }
            });

         ////////////////////////////slide image///////////////////////
            rq=Volley.newRequestQueue(this);
            sliderimg=new ArrayList<>();
            viewPager=(ViewPager)findViewById(R.id.viewpager);
            sliderdots=(LinearLayout)findViewById(R.id.sliderdots);
            Send_req();

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
            timer.scheduleAtFixedRate(new Home.myTimerTask(),2000,4000);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////

            ////////////////////////////////Catagoreis/////////////////////////

            recyclerView3 = findViewById(R.id.recy4);
            recyclerView3.setHasFixedSize(true);
            recyclerView3.setLayoutManager(new LinearLayoutManager(this));
            LinearLayoutManager layoutManager3= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView3.setLayoutManager(layoutManager3);
            productList=new ArrayList<>();
//            ctadapter=new catagory_adapter(this,productList);
//            recyclerView3.setAdapter(ctadapter);


            loadProducts();


            ///////////////////////////////////////////////////////////////////////
            ///////////////////////featured products/////////////////////////
            recyclerView = findViewById(R.id.recy1);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager2);
            fdproductlist=new ArrayList<>();

             featured_product();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////offfer products/////////////////////////

            recyclerView1 = findViewById(R.id.recy2);
            recyclerView1.setHasFixedSize(true);
            recyclerView1.setLayoutManager(new LinearLayoutManager(this));
            LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView1.setLayoutManager(layoutManager);
            offproductlist=new ArrayList<>();
            offfer_product();


            ///////////////////////////////////////////////////////////////////////
            /////////////////////Latest Products//////////////////////////////////

            recyclerView2 = findViewById(R.id.recy3);
            recyclerView2.setHasFixedSize(true);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));
            LinearLayoutManager layoutManager1= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView2.setLayoutManager(layoutManager1);

            latest_productlist=new ArrayList<>();
            Latest_product();
//            adapter=new Latest_products_adapter(listItems,this);
//            recyclerView2.setAdapter(adapter);
            //////////////////////////////////////////////////////////////////////
        }
//    public void createNotification () {
//        Intent notificationIntent = new Intent(getApplicationContext() , MainActivity. class ) ;
//        notificationIntent.putExtra( "fromNotification" , true ) ;
//        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
//        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , notificationIntent , 0 ) ;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Home. this, default_notification_channel_id ) ;
//        mBuilder.setContentTitle( "My Notification" ) ;
//        mBuilder.setContentIntent(pendingIntent) ;
//        mBuilder.setContentText( "Notification Listener Service Example" ) ;
//        mBuilder.setTicker( "Notification Listener Service Example" ) ;
//        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
//        mBuilder.setAutoCancel( true ) ;
//        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//            int importance = NotificationManager. IMPORTANCE_HIGH ;
//            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//            assert mNotificationManager != null;
//            mNotificationManager.createNotificationChannel(notificationChannel) ;
//        }
//        assert mNotificationManager != null;
//        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
//       // notificationCount ++ ;
//
//    }
//    private void progresss() {
//        this.progress=findViewById(R.id.progress);
//        progress.setVisibility(View.VISIBLE);
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                progress.setVisibility(View.GONE);
////            }
////        },5000);
//    }

    private void shareApp() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction("android.intent.action.SEND");
            sendIntent.putExtra("android.intent.extra.TEXT", "Send surprise gifts to your beloved ones online. Install Eazy Home app from www.eazyhomebrunei.com");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    public void onResume() {
        super.onResume();
        this.refreshLayout.post(new Runnable() {
            public void run() {
                Home.this.refreshLayout.setRefreshing(true);
               Home.this.cartcount();
//                Home.this.offfer_product();
//                Home.this.featured_product();
//                Home.this.loadProducts();
            }
        });
    }
    @Override
    public void onRefresh() {
            Latest_product();

     cartcount();

    }

    @Override
    public void CartItemClick(int position) {
        cartcount();

    }


    public  class  myTimerTask extends TimerTask{

        @Override
        public void run() {
            Home.this.runOnUiThread(new Runnable() {
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
                    else  if(viewPager.getCurrentItem()==2)
                    {
                        viewPager.setCurrentItem(3)
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
        final String URL_SUBCATAGORY="https://www.eazyhomebrunei.com/Android_Api/allBanners";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
//                progress.setVisibility(View.GONE);
                Home.this.refreshLayout.setRefreshing(false);
                try {


//
                    JSONObject jsonObject = new JSONObject(response);
                 //   JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jsonObject.getJSONArray("data");



                    for (int i = 0; i <ar.length(); i++)
                    {
                        SliderUtils sliderUtils=new SliderUtils();

                        JSONObject productobject = ar.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(productobject.getString("image"));
                     //   Toast.makeText(getApplicationContext(),"jjj"+sliderUtils,Toast.LENGTH_LONG).show();

                        sliderimg.add(sliderUtils);


                    }
                    viewPagerAdapter=new ViewPagerAdapter(sliderimg,Home.this);
                    viewPager.setAdapter(viewPagerAdapter);
                    dotcount=viewPagerAdapter.getCount();
                    dots=new ImageView[dotcount];
                    for (int i=0;i<dotcount;i++)
                    {
                        dots[i]=new ImageView(Home.this);
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
        })    {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile_no", phnumber);
                params.put("Token", Token);
                //Toast.makeText(getApplicationContext(),""+params,Toast.LENGTH_LONG).show();
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

                    Home.this.refreshLayout.setRefreshing(false);
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
    private void Latest_product() {
   final String URL_LATEST=PATH +"home?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Home.this.refreshLayout.setRefreshing(false);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("latest_list");
                    latest_adapter = new Latest_products_adapter(getApplicationContext(),latest_productlist);
                    recyclerView2.setAdapter(latest_adapter);
                    latest_adapter.setOnItemClickListener(Home.this);
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int id=productobject.getInt("id");
                        String pname=productobject.getString("product");
                        String image=productobject.getString("thumbnail_image");
                        String offer_price=productobject.getString("offer_price");
                        String price=productobject.getString("price");
                        String flag=productobject.getString("Flag");
                        int wishid=productobject.getInt("wishId");
                        int status=productobject.getInt("Status");
                        latest_products latest_product=new latest_products(id,pname,offer_price,price,flag,image,wishid,status);
                        latest_productlist.add(latest_product);

                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                        ll_main_layout.setVisibility(View.VISIBLE);


                    }
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

                params.put("mobile_no", phnumber);
                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void offfer_product() {
        final String URL_OFFER=PATH +"home?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_OFFER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Home.this.refreshLayout.setRefreshing(false);


                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("offer_list");
                   ofadapter = new offer_products_adapter(getApplicationContext(),offproductlist);
                    recyclerView1.setAdapter(ofadapter);
                    ofadapter.setOnItemClickListener(Home.this);
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int id=productobject.getInt("id");
                        String pname=productobject.getString("product");
                        String image=productobject.getString("thumbnail_image");
                        String offer_price=productobject.getString("offer_price");
                        String price=productobject.getString("price");
                        String flag=productobject.getString("Flag");
                        int wishid=productobject.getInt("wishId");
                        int status=productobject.getInt("Status");
                        offer_product offer_product=new offer_product(id,pname,offer_price,price,flag,image,wishid,status);
                        offproductlist.add(offer_product);
                    }
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

                params.put("mobile_no", phnumber);
                params.put("Token", Token);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void featured_product() {
         final String URL_FEature=PATH +"home?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FEature, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("FEa",response);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("featured_list");
                    fdadapter = new featured_products_adapter(getApplicationContext(), fdproductlist);
                    recyclerView.setAdapter(fdadapter);
                    fdadapter.setOnItemClickListener(Home.this);
                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        int id=productobject.getInt("id");
                        String pname=productobject.getString("product");
                        String image=productobject.getString("thumbnail_image");
                        String offer_price=productobject.getString("offer_price");
                        String price=productobject.getString("price");
                        int flag=productobject.getInt("Flag");
                        int wishid=productobject.getInt("wishId");
                        int status=productobject.getInt("Status");
                        featured_pro fd_product=new featured_pro(id,pname,offer_price,price,flag,image,wishid,status);
                        fdproductlist.add(fd_product);
                    }
                } catch (JSONException e) {
//
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("mobile_no", phnumber);
                params.put("Token", Token);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadProducts() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   // progress.setVisibility(View.GONE);

                    Home.this.refreshLayout.setRefreshing(false);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("cat");
                    ctadapter = new catagory_adapter(getApplicationContext(), productList);
                    recyclerView3.setAdapter(ctadapter);



                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int CategoryId=productobject.getInt("CategoryId");
                        String catagory=productobject.getString("Category");
                        String image=productobject.getString("Image");
                        int product_count=productobject.getInt("pr_count");
                        int sub_count=productobject.getInt("sub_count");
                        Product product=new Product(CategoryId,sub_count,product_count,catagory,image);
                         productList.add(product);




                    }
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
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ald=new AlertDialog.Builder(Home.this);
        ald.setTitle("Do you want to Exit")
                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in=new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al=ald.create();
        al.show();

    }

    public void showDialog() {
        String title = "Logout!";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(ssBuilder);
        builder1.setMessage("Are you sure you really want to logout?");
        builder1.setCancelable(true);
        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               Intent i=new Intent(getApplicationContext(),Login.class);
               SharedPreferences.Editor editor=getSharedPreferences("MyPref",MODE_PRIVATE).edit();
               editor.clear();
               editor.apply();
               startActivity(i);
                Home.this.finish();
            }
        });
        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder1.create().show();
    }


    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"MyProfile?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

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
                    String image1=productobject.getString("image");
                    //int status=productobject.getInt("Status");
//                        String price=productobject.getString("Price");

                    name.setText(name1);
                    phone.setText(phone1);
                    Picasso with = Picasso.with(getApplicationContext());
                    StringBuilder sb = new StringBuilder();
                    sb.append(BaseURL.PROFILE_IMAGE);
                    sb.append(image1);
                    with.load(sb.toString()).placeholder(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).error(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).into(image);

                    // phn.setText(phone1);

//
//
//
//                    }
//


                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
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
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }


//    @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            MenuInflater inflater=getMenuInflater();
//            // Inflate the menu; this adds items to the action bar if it is present.
//            inflater.inflate(R.menu.home, menu);
//            return true;
//        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.item1:
                    Toast.makeText(this,"Item 1 is selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.item2:
                    Toast.makeText(this,"Item 2 is selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.item3:
                    Toast.makeText(this,"Item 3 is selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.subitem1:
                    Toast.makeText(this,"Subitem 1 is selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.subitem2:
                    Toast.makeText(this,"Subitem 2 is selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.subitem:
                    Toast.makeText(this,"Subitem  is selected",Toast.LENGTH_LONG).show();
                    return true;
                    default:return super.onOptionsItemSelected(item);
            }

        }

            private  void createSlideShow()
        {
            final Handler handler=new Handler();
           final Runnable runnable=new Runnable() {
               @Override
               public void run() {
                   if(current_position==listItems.size());
                   current_position=0;
                   viewPager.setCurrentItem(current_position++,true);

               }
           };
           timer=new Timer();
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   handler.post(runnable);

               }
           },250,2500);
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
