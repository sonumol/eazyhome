package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.adapter.check_out_adapter;
import app.eazyhomebrunei.com.model.checkout_pro;
import app.eazyhomebrunei.com.model.customertype;
import app.eazyhomebrunei.com.model.customertype_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class checkout extends AppCompatActivity  implements  Spinner.OnItemSelectedListener {
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    private List<checkout_pro> listItems;
    private RecyclerView.Adapter adapter;
    Button checkout;
    Spinner sp;
    TextView t;
    String[] country;
    int sp_position;
    String selected;
    String Token;
    private check_out_adapter fdadapter;
    TextView total1,delivery,grand,addnew;
//    List<String> address = new ArrayList<String>();//add ids in this list
//    List<String> id = new ArrayList<String>();//add names in this list

    public AppCompatSpinner sp_typecustomer;
    /* access modifiers changed from: private */
    public ArrayAdapter<String> typecustomer_adapter;

    //JSON Array
    private JSONArray result;
    String adr;
    LinearLayout cashon,card;
    int mode;
    LinearLayout placeorder;

    int total_price;
    String dr="";
    int adid;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;
    AppCompatSpinner appCompatSpinner3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  address = new ArrayList<String>();

//        sp = (Spinner) findViewById(R.id.addr);
//        sp.setOnItemSelectedListener(this);



        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        left_arrow=(ImageView)findViewById(R.id.arrow);



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

        cashon=(LinearLayout) findViewById(R.id.cashon);
        card=(LinearLayout) findViewById(R.id.card);
        placeorder=(LinearLayout)findViewById(R.id.placeorder);
        addnew=(TextView)findViewById(R.id.addnew);
        final LinearLayout csh=(LinearLayout)findViewById(R.id.cshid);
        final LinearLayout crd=(LinearLayout)findViewById(R.id.crdid);


        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),add_address.class);
                startActivity(i);
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeorderr();
            }
        });

        cashon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode=2;
                csh.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
                crd.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_background));


                // Toast.makeText(getApplicationContext(),"Cash on Delivery"+mode,Toast.LENGTH_LONG).show();
            }
        });
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode=1;
                crd.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
                csh.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_background));



                //Toast.makeText(getApplicationContext(),"Debit/Credit Netbanking",Toast.LENGTH_LONG).show();
            }
        });



        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        total1 = (TextView) findViewById(R.id.total);
        delivery = (TextView) findViewById(R.id.delivery);
        grand = (TextView) findViewById(R.id.grand_total);
        String str = "Select Address";
//        final String URL_FEATURED="UserAddressList?user_id="+lid;
//
//        ArrayList<String>items=getadrres(URL_FEATURED);
//        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.activity_checkout,R.id.textView,items);
//sp.setAdapter(adapter);


        /////////////////////Latest Products//////////////////////////////////

        recyclerView2 = findViewById(R.id.recy1);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager1);

        listItems = new ArrayList<>();
        checkout1();
        view_all_address();
        intUI();
    }
    private void intUI() {


        appCompatSpinner3 = (AppCompatSpinner) findViewById(R.id.addr);
        this.sp_typecustomer = appCompatSpinner3;
        appCompatSpinner3.setOnItemSelectedListener(this);

        initListAndAdapters();

    }

    public void initListAndAdapters() {

        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter(this, R.layout.layout_text, R.id.tv_fucking_text, customertype_list.getInstance().getList());
        this.typecustomer_adapter = arrayAdapter5;
        this.sp_typecustomer.setAdapter((SpinnerAdapter) arrayAdapter5);
        this.typecustomer_adapter.notifyDataSetChanged();



        view_all_address();
    }
    private void placeorderr() {
//        if (sp_typecustomer.getSelectedItem().toString().trim().equals("Select Address")) {
//
//            Toast.makeText(getApplicationContext(), "Select Address", Toast.LENGTH_SHORT).show();
//        }
          if(mode == 0)
        {
            Toast.makeText(getApplicationContext(),"plese select payment Method",Toast.LENGTH_LONG).show();
        }
        else {


            final String PLACE_ORDER = PATH +"placeOrder?mode=" + mode + "&id=" + adid + "&total=" + total_price + "&delivery_charge=" + 0;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, PLACE_ORDER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
                        Intent i = new Intent(checkout.this, Home.class);
                        Toast.makeText(checkout.this, "Successfully added", Toast.LENGTH_LONG).show();


                        startActivity(i);
                        finish();


                    } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(checkout.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                              Toast.makeText(checkout.this,"please add address / Select Address", Toast.LENGTH_SHORT).show();


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

    private void view_all_address() {
            final String URL_FEATURED=PATH +"CheckoutAddressList?";

            StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FEATURED, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

//                   //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                        JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
                        JSONArray ar=jsonObject.getJSONArray("data");
//

                        // Toast.makeText(getApplicationContext(),"jjj"+adresadapter,Toast.LENGTH_LONG).show();
                        customertype_list.getInstance().clearList();

                        for (int i = 0; i <ar.length(); i++)
                        {
                            JSONObject productobject = ar.getJSONObject(i);


                            customertype_list.getInstance().add(new customertype(productobject.getString("address"), productobject.getInt("id")));


//                            addres_list fd_product=new addres_list(id,customer_id,name,phone,email,address,state,city,postal_code,status,date);





                        }
                        checkout.this.typecustomer_adapter = new ArrayAdapter(checkout.this, R.layout.layout_text, R.id.tv_fucking_text,customertype_list.getInstance().getcustomertype());
                        checkout.this.sp_typecustomer.setAdapter((SpinnerAdapter) checkout.this.typecustomer_adapter);
                        checkout.this.typecustomer_adapter.notifyDataSetChanged();

//                        ArrayAdapter<String> ad =(new ArrayAdapter<String>(checkout.this, android.R.layout.simple_spinner_dropdown_item, address));
//                        sp.setAdapter(ad);
//                        sp.setPrompt("Select your favorite Planet!");

                        // sp_position = ad.getPosition(myString);
                    } catch (JSONException e) {
                        appCompatSpinner3.setVisibility(View.GONE);
//                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
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



    private void checkout1() {
            final String URL_CART=PATH+"CheckOut?";

            StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CART, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {


                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject jk=jsonObject.getJSONObject("data");

                        JSONArray ar=jk.getJSONArray("cart");
                       // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                        fdadapter = new check_out_adapter(getApplicationContext(), listItems);
                        recyclerView2.setAdapter(fdadapter);
                        //fdadapter.setOnItemClicklistener(checkout.this);

                        for (int i = 0; i <ar.length(); i++)
                        {
                            JSONObject productobject = ar.getJSONObject(i);
                            int cartid=productobject.getInt("cartId");

                            int id=productobject.getInt("pid");
                            String pname=productobject.getString("pname");
                            String image=productobject.getString("image");
                            int quantity=productobject.getInt("quantity");
                            int price=productobject.getInt("price");
                            total_price=productobject.getInt("total_price");


                            checkout_pro fd_product=new checkout_pro(cartid,id,pname,image,quantity,price,total_price);
                            listItems.add(fd_product);




                        }
                    String    delivery_charge = jk.getString("delivery_charge");


                        Double v=Double.parseDouble(delivery_charge);

                        delivery.setText("BND."+String.format("%.2f",v));
                        String total = jk.getString("total");
                        Double c=Double.parseDouble(total);
                        total1.setText("BND."+String.format("%.2f",c));

                    String    grandTotal = jk.getString("grand_total");
                        Double b=Double.parseDouble(grandTotal);
                        grand.setText("BND."+String.format("%.2f",b));
//

                        // Toast.makeText(getApplicationContext(),"jjj"+gd_total,Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//                        e.printStackTrace();
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
            // updateData();

        }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long i) {
        //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        if (this.sp_typecustomer.getSelectedItemPosition() > 0) {
            adid= customertype_list.getInstance().getcustomertypeID(this.sp_typecustomer.getSelectedItem().toString());
//            StringBuilder sb = new StringBuilder();
//            sb.append("onItemSelected: sp_district id : ");
//            sb.append(districtID);
//            Log.d(str, sb.toString());
//            call_panchayat_api(districtID);
            // Toast.makeText(getApplicationContext(),"d"+adid,Toast.LENGTH_LONG).show();

            return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

         Toast.makeText(getApplicationContext(),"please add address",Toast.LENGTH_LONG).show();

    }


}
