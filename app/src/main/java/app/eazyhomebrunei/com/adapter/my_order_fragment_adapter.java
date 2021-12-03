package app.eazyhomebrunei.com.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.Single_order_details;
import app.eazyhomebrunei.com.model.my_order;

import java.util.List;

public class my_order_fragment_adapter extends RecyclerView.Adapter<my_order_fragment_adapter.viewHolder> {
    private List<my_order> cpn_productlist;
    private int order_id,order_id1;

    private Context mcontext;
    String Flag;
    Dialog mydialog;
    TextView order;

    public my_order_fragment_adapter(Context mcontext,List<my_order> listitem) {
        this.cpn_productlist = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public my_order_fragment_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_fragment_adapter,parent,false);
        mcontext=parent.getContext();


        return  new my_order_fragment_adapter.viewHolder(v);
    }




    @Override
    public void onBindViewHolder(@NonNull final my_order_fragment_adapter.viewHolder holder, final int position) {
        final my_order itemlist=cpn_productlist.get(position);
        final String name = itemlist.getName();
       order_id= Integer.parseInt(itemlist.getOrderId());
        order_id1 = Integer.parseInt(cpn_productlist.get(position).getOrder_id());
        final String order_id2=itemlist.getOrder_id();
        final String order_date=itemlist.getOrderDate();
         Flag=itemlist.getFlag();
      //  holder.btn.setOnCheckedChangeListener(null);


        holder.name.setText("To:"+name);
        holder.orderid.setText(""+order_id);

      //  holder.delivery.setText(order_date);
        String  f1="Placed";

        if (itemlist.getFlag().equals("Pending")) {
            holder.btn.setChecked(true);
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.blue));
        }
        else if(itemlist.getFlag().equals("Processing"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.yellow));
        }
        else if(itemlist.getFlag().equals("Completed"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.gree));

        }
        else if(itemlist.getFlag().equals("Cancelled"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.red));

        }
        else if(itemlist.getFlag().equals("Refunded"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.rose));

        }
        else if(itemlist.getFlag().equals("Failed"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.black));

        }
        else if(itemlist.getFlag().equals("Shipped"))
        {
            holder.delivery.setText(Flag);
            holder.btn.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.orange));

        }

        holder.lnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.lnr1.getContext(), Single_order_details.class);
//                // map.put("product_id", modelList.get(position).getProduct_id());
                intent.putExtra("order_id2",cpn_productlist.get(position).getOrder_id());



                // intent.putExtra("wishId",fdproductlist.get(position).getWishId());


                holder.lnr1.getContext().startActivity(intent);
            }
        });
//        mydialog=new Dialog(mcontext);
//        mydialog.setContentView(R.layout.activity_detailactivity);
//        holder.lnr1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String VIEW_SINGLE="SingleOrderDetails?order_id="+order_id2;
//                StringRequest stringRequest=new StringRequest(Request.Method.GET, VIEW_SINGLE, new Response.Listener<String>() {
//                        @Override
//                public void onResponse(String response) {
//                    // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();
//                    try{
//                        JSONObject jsonObject=new JSONObject(response);
//                        JSONObject jk=jsonObject.getJSONObject("data");
//                        JSONArray ar=jk.getJSONArray("order");
//                        //Toast.makeText(getApplicationContext(),"res"+ar,Toast.LENGTH_LONG).show();
//
//                        for (int i = 0; i <ar.length(); i++) {
//                            JSONObject productobject = ar.getJSONObject(i);
//
//                            String orderId = productobject.getString("orderId");
//                            String Name = productobject.getString("Name");
//                            String Location = productobject.getString("Location");
//
//                            String Mobile = productobject.getString("Mobile");
//                            String address = productobject.getString("address");
//                            String OrderDate = productobject.getString("OrderDate");
//                            String status_date = productobject.getString("status_date");
//                            String ShippinReference = productobject.getString("ShippinReference");
//                            String pincode = productobject.getString("pincode");
//                            final String Total_Amount = productobject.getString("Total_Amount");
//                            String Delivery_Charge = productobject.getString("Delivery_Charge");
//
//                            String Grand_Total = productobject.getString("Grand_Total");
//                            String Flag = productobject.getString("Flag");
//
//
//
//
//                            TextView orderid=(TextView)mydialog.findViewById(R.id.orderid);
//                            TextView date=(TextView)mydialog.findViewById(R.id.date);
//                            TextView name=(TextView)mydialog.findViewById(R.id.name);
//                            TextView location=(TextView)mydialog.findViewById(R.id.location);
//                            TextView addres=(TextView)mydialog.findViewById(R.id.address);
//                            TextView cancel=(TextView)mydialog.findViewById(R.id.cancel);
//
//                             order=(TextView)mydialog.findViewById(R.id.order);
//                                if (Flag.equals("Cancelled"))
//                                {
//                                    order.setText("Order Cancelled");
//                                    cancel.setVisibility(View.INVISIBLE);
//                                   order.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.textred));
//                                }
//                                else if (Flag.equals("Placed"))
//                                {
//                                    order.setText("Order Placed");
//                                    cancel.setVisibility(View.VISIBLE);
//                                    order.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.textblue));
//
//
//                                }
//                                else if(Flag.equals("Delivered")) {
//                                    order.setText("Order Delivered");
//                                    cancel.setVisibility(View.INVISIBLE);
//                                   order.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.textgreen));
//
//                                }
//                                else
//                                {
//                                    order.setText("Order Shipped");
//                                   order.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.textyellow));
//
//
//
//                                }
//
//                            orderid.setText("Order Id :"+orderId);
//                            date.setText("Date :"+OrderDate);
//                            name.setText(Name);
//                            location.setText(Location+","+Mobile);
//                            addres.setText(address);
//                            TextView txtclose;
//                            txtclose =(TextView) mydialog.findViewById(R.id.txtclose);
//                            txtclose.setText("X");
//                            txtclose.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    mydialog.dismiss();
//                                }
//                            });
//                            /////////////////////////////////////////////////cancel order////////////////////////
//                                    cancel.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//
//                                                String URL_DELETE_ADDRESS="CancelOrder?order_id="+order_id2;
//
//                                                StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
//                                                    @Override
//                                                    public void onResponse(String response) {
//                                                        try{
//                                                            JSONObject jsonObject=new JSONObject(response);
//                                                            Intent intent = new Intent(mcontext, my_profile.class);
////                // map.put("product_id", modelList.get
//                                                           mcontext.startActivity(intent);
//
//
//                                                        }
//                                                        catch (JSONException e)
//                                                        { e.printStackTrace();
//                                                        }
//                                                    }
//                                                },  new Response.ErrorListener() {
//                                                            @Override
//                                                            public void onErrorResponse(VolleyError error) {
//                                                               Toast.makeText(mcontext,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                                            }
//                                                        })   {
//
//                                                };
//                                                RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
//                                                requestQueue.add(stringRequest);
//
//
//                                            }
//
//                                    });
//                            /////////////////////////////////////////////////////////////////////////////////////////////////
//                            mydialog.show();
//
//
//
////}
//
//                        }
//                    }
//                    catch (JSONException e)
//                    {
//                        e.printStackTrace();
//                        Toast.makeText(mcontext,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//
//
//                    }
//                }
//            },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(mcontext,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    })
//
//            {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String,String> params=new HashMap<String, String>();
//                    params.put("order_id", String.valueOf(order_id1));
//
//
//                    return params;
//                }
//            };
//            RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
//            requestQueue.add(stringRequest);
//
//        }




//});
//
}

//    public void cancel_order(final String product )


    @Override
    public int getItemCount() {
        return cpn_productlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,orderid,delivery;
        ToggleButton btn;
        LinearLayout lnr1;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            orderid=itemView.findViewById(R.id.orderid);
            delivery=itemView.findViewById(R.id.delivery);
            btn=itemView.findViewById(R.id.wish);
            lnr1=itemView.findViewById(R.id.lnr1);
//            String text="10.30am | 1.30pm | 4.30pm | 9.00pm 10.30am | 1.30pm | 4.30pm | 9.00pm ";
//            tv1.setText(text);
//            tv1.setMovementMethod(new ScrollingMovementMethod());
        }

    }
}
