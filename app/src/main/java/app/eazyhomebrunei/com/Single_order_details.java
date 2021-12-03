package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Single_order_details extends AppCompatActivity {
    TextView order;
    String order_id2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order_details);
        Intent intent=getIntent();
        order_id2=intent.getStringExtra("order_id2");
        //Toast.makeText(getApplicationContext(),""+order_id2,Toast.LENGTH_LONG).show();

        single_details();
    }

    private void single_details() {
        {
            String VIEW_SINGLE="https://www.eazyhomebrunei.com/Android_Api/SingleOrderDetails?order_id="+order_id2;
            StringRequest stringRequest=new StringRequest(Request.Method.GET, VIEW_SINGLE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        JSONObject jk=jsonObject.getJSONObject("data");
                        JSONArray ar=jk.getJSONArray("order");
                        //Toast.makeText(getApplicationContext(),"res"+ar,Toast.LENGTH_LONG).show();

                        for (int i = 0; i <ar.length(); i++) {
                            JSONObject productobject = ar.getJSONObject(i);

                            String orderId = productobject.getString("orderId");
                            String Name = productobject.getString("Name");
                            String Location = productobject.getString("Location");

                            String Mobile = productobject.getString("Mobile");
                            String address = productobject.getString("address");
                            String OrderDate = productobject.getString("OrderDate");
                            String status_date = productobject.getString("status_date");
                            String ShippinReference = productobject.getString("ShippinReference");
                            String pincode = productobject.getString("pincode");
                            final String Total_Amount = productobject.getString("Total_Amount");
                            String Delivery_Charge = productobject.getString("Delivery_Charge");

                            String Grand_Total = productobject.getString("Grand_Total");
                            String Flag = productobject.getString("Flag");




                            TextView orderid=(TextView)findViewById(R.id.orderid);
                            TextView date=(TextView)findViewById(R.id.date);
                            TextView name=(TextView)findViewById(R.id.name);
                            TextView location=(TextView)findViewById(R.id.location);
                            TextView addres=(TextView)findViewById(R.id.address);
                            TextView cancel=(TextView)findViewById(R.id.cancel);

                            order=(TextView)findViewById(R.id.order);
                            if (Flag.equals("Pending"))
                            {
                                order.setText("Order Pending");
                                cancel.setVisibility(View.VISIBLE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textblue));
                            }
                            else if (Flag.equals("Processing"))
                            {
                                order.setText("Order Processing");
                                cancel.setVisibility(View.VISIBLE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textyellow));


                            }
                            else if(Flag.equals("Completed")) {
                                order.setText("Order Completed");
                                cancel.setVisibility(View.GONE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textgreen));

                            }
                            else if(Flag.equals("Cancelled")) {
                                order.setText("Order Cancelled");
                                cancel.setVisibility(View.GONE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textred));

                            }
                            else if(Flag.equals("Refunded")) {
                                order.setText("Refunded");
                                cancel.setVisibility(View.GONE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textrose));

                            }
                            else if(Flag.equals("Failed")) {
                                order.setText("Order Failed");
                                cancel.setVisibility(View.GONE);
                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textblack));

                            }
                            else
                            {
                                order.setText("Order Shipped");
                                cancel.setVisibility(View.VISIBLE);

                                order.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.textorange));



                            }

                            orderid.setText("Order Id :"+orderId);
                            date.setText("Date :"+OrderDate);
                            name.setText(Name);
                            location.setText(Location+","+Mobile);
                            addres.setText(address);

                            /////////////////////////////////////////////////cancel order////////////////////////
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String URL_DELETE_ADDRESS=PATH+"CancelOrder?order_id="+order_id2;

                                    StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try{
                                                JSONObject jsonObject=new JSONObject(response);
                                                Intent intent = new Intent(getApplicationContext(), my_profile.class);
//                // map.put("product_id", modelList.get
                                                getApplicationContext().startActivity(intent);


                                            }
                                            catch (JSONException e)
                                            { e.printStackTrace();
                                            }
                                        }
                                    },  new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    })   {

                                    };
                                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(stringRequest);


                                }

                            });
                            /////////////////////////////////////////////////////////////////////////////////////////////////




//}

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"add address error"+e.toString(),Toast.LENGTH_LONG).show();


                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        }
                    })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params=new HashMap<String, String>();
                    params.put("order_id", String.valueOf(order_id2));


                    return params;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

}
