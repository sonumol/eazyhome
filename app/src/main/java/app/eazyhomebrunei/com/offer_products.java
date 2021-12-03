package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.adapter.offerproducts_details_adapter;
import app.eazyhomebrunei.com.model.all_products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class offer_products extends AppCompatActivity{
    RecyclerView recyclerView,recyclerView1,recyclerView2;
    private offerproducts_details_adapter prdadapter;
    private List<all_products> prolistItems;
    private static final String URL_OFFER_PRODUCT=PATH +"AllProducts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recy1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        prolistItems=new ArrayList<>();
        all_product();


    }

    private void all_product() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_OFFER_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                      //Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("all_products");
                    //Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();

                    prdadapter = new offerproducts_details_adapter(getApplicationContext(), prolistItems);
                    recyclerView.setAdapter(prdadapter);
                 //   prdadapter.setOnItemClickListener(offer_products.this);
                    // Toast.makeText(getApplicationContext(),"jjj"+ar,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        int id=productobject.getInt("pid");
                        String pname=productobject.getString("pname");
                        String short_descrption=productobject.getString("short_description");
                        String detailed_description=productobject.getString("detailed_description");
                        String image=productobject.getString("Image");
                        int status=productobject.getInt("Status");
                        int flag=productobject.getInt("Flag");
                        String price=productobject.getString("Price");
                        String offer_price=productobject.getString("OfferPrice");
                        int wishId=productobject.getInt("wishId");



                        all_products products=new all_products(id,pname,image,price,offer_price,status,flag,wishId);
                        prolistItems.add(products);




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

    //@Override
//    public void onItemClick(int position) {
//        all_products clickeditem = prolistItems.get(position);
//        int id = prolistItems.get(position).getId();
//        String pname = prolistItems.get(position).getPname();
//        String short_disc = prolistItems.get(position).getShort_description();
//
//        String detailed_description= prolistItems.get(position).getShort_description();
//        String image= prolistItems.get(position).getShort_description();
//        int status= prolistItems.get(position).getStatus();
//        int flag= prolistItems.get(position).getFlag();
//        String price= prolistItems.get(position).getPrice();
//        String offer_price= prolistItems.get(position).getOffer_price();
//
//            Intent i = new Intent(getApplicationContext(), product_details.class);
//
//
//            i.putExtra("id",id);
//            i.putExtra("pname",clickeditem.getPname());
//            i.putExtra("short_disc", short_disc);
//            i.putExtra("detailed_description", detailed_description);
//            i.putExtra("image", clickeditem.getImage());
//            i.putExtra("status", status);
//            i.putExtra("flag", flag);
//           i.putExtra("price", price);
//           i.putExtra("offer_price", offer_price);
//
//
//        startActivity(i);
//
//    }

}
