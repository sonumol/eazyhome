package app.eazyhomebrunei.com.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import app.eazyhomebrunei.com.Config.BaseURL;

import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.Single_product;
import app.eazyhomebrunei.com.model.cart_pro;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class view_my_cart_adapter extends RecyclerView.Adapter<view_my_cart_adapter.viewHolder> {
    private List<cart_pro> fdproductlist;
    private CartItemClickListener mListener;

    private Context mcontext;
    int qty;
    Dialog myDialog;
    ProgressDialog progressDialog;
    private static DecimalFormat df = new DecimalFormat("0.00");

    //TextView ;
    float grand_Total;
    public  CartItemClickListener cartItemClick;
    public interface CartItemClickListener{
        void cartItemClick(int position);
    }
    public void setcartOnItemClickListener(CartItemClickListener listener){
        mListener =listener;
    }


    public view_my_cart_adapter( Context mcontext,List<cart_pro> listitem) {
        this.fdproductlist = listitem;
        this.mcontext = mcontext;
        myDialog = new Dialog(mcontext);

    }

    @NonNull
    @Override
    public view_my_cart_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_cart_adapter,parent,false);
        mcontext=parent.getContext();

        return  new view_my_cart_adapter.viewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final view_my_cart_adapter.viewHolder holder, final int position) {
        final cart_pro itemlist=fdproductlist.get(position);
      //  Dialog dialog = new Dialog(mcontext);

        String name=itemlist.getPname();
        final String price=itemlist.getPrice();
        String total_price=itemlist.getTotal_price();
        String reference=itemlist.getReference();
        int cartid=fdproductlist.get(position).getCart_id();
        int pid=fdproductlist.get(position).getId();
        final String count1=itemlist.getQuantity();
        final String offer_price=itemlist.getOffer_price();
        // String grand_total=itemlist.ge
        // final String total=count*price;
        //  Toast.makeText(mcontext,"res"+price,Toast.LENGTH_LONG).show();
//        holder.price.setText(offer_price);

        holder.name.setText(name);
        holder.reference.setText(reference);
        final Float offpric= Float.parseFloat(offer_price);
        final Float pric= Float.parseFloat(price);
        final Float coun= Float.parseFloat(count1);
        final Float totalpric= Float.parseFloat(total_price);

        holder. count.setText(""+count1);

        if(offer_price.equals("0.00"))
        {
            holder.prie.setText("BND."+df.format(pric));
            holder.prie.setTextColor(Color.BLACK);
            holder.total.setText("BND."+df.format(totalpric));

        }
        else
        {
            holder.prie.setText("BND."+df.format(pric));
            holder.price.setText("BND."+df.format(offpric));
            holder.price.setTextColor(Color.BLACK);
            holder.prie.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            holder.total.setText("BND."+coun*offpric);
        }




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Warning!";
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mcontext.getResources().getColor(R.color.colorPrimary));

                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
                ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Do you want to delete this item from cart?");
                alertbox.setTitle(ssBuilder);

                alertbox.setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
//                                if(mListener!=null){
//                                    int position=getAdapterPosition();
//                                    if(position!=RecyclerView.NO_POSITION){
//                                        mListener.cartItemClick(position);
//                                    }
//
//                                }

                                deletecart(itemlist);
                                cart_pro h = fdproductlist.get(position);
                                if(position!=RecyclerView.NO_POSITION){
                                    mListener.cartItemClick(position);

                                }

                            }
                        });
                alertbox.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                            }
                        });
                alertbox.show();





            }
        });



       holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.img.getContext(), Single_product.class);
//                // map.put("product_id", modelList.get(position).getProduct_id());
                intent.putExtra("image",fdproductlist.get(position).getImage());

                intent.putExtra("id",fdproductlist.get(position).getId());
                intent.putExtra("pname",fdproductlist.get(position).getPname());

//                intent.putExtra("short_disc",offproductlist.get(position).getShort_description());
//                intent.putExtra("detailed_description",offproductlist.get(position).getDetailed_description());



                holder.img.getContext().startActivity(intent);

            }
        });

//        String str="";
//        float grand_Total = CartList.getInstance().get_grand_total();
//        CartItemClickListener grandTotal2 = this.cartItemClick;
//        StringBuilder sb6 = new StringBuilder();
//        sb6.append(grand_Total);
//        sb6.append(str);
//        grandTotal2.cartItemClick(Integer.parseInt(sb6.toString()));
        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                qty = 0;
                if (!holder.count.getText().toString().equalsIgnoreCase(""))
                    qty = Integer.valueOf(holder.count.getText().toString());

                if (qty > 1) {
                    qty = qty - 1;
                    holder.count.setText(String.valueOf(qty));
                    if(offer_price.equals("0.00"))
                    {
                        float f=qty*pric;
                        holder.total.setText("BND."+df.format(f));
                    }
                    else
                    {
                        float f=qty*offpric;
                        holder.total.setText("BND."+df.format(f));
                    }

                    updateQuantity(itemlist);
                    if(position!=RecyclerView.NO_POSITION){
                        mListener.cartItemClick(position);

                    }

                }

//                if (holder.count.getText().toString().equalsIgnoreCase("0")) {
//                    dbHandler.removeItemFromCart(map.get("product_id"));
//                    list.remove(position);
//                    notifyDataSetChanged();
//
//                    updateintent();
//                }
            }
        });

        holder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qty = Integer.valueOf(holder.count.getText().toString());
                qty = qty + 1;
                holder.count.setText(String.valueOf(qty));
                if (offer_price.equals("0.00"))
                {
                    float f=qty*pric;
                    holder.total.setText("BND."+df.format(f));
                }
                else
                {
                    float f=qty*offpric;
                    holder.total.setText("BND."+df.format(f));
                }

                updateQuantity(itemlist);
                if(position!=RecyclerView.NO_POSITION){
                    mListener.cartItemClick(position);

                }

            }
        });
        Picasso with = Picasso.with(mcontext);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.PRODUCT_IMG_PATH);
        sb.append(itemlist.getImage());
        with.load(sb.toString()).placeholder(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).error(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).into(holder.img);


//
    }



    private void updateQuantity(final cart_pro itemlist) {
        String URL_ADD_TO_CART="https://www.eazyhomebrunei.com/Android_Api/UpdateProductQuantity?cartId="+itemlist.getCart_id()+"&quantity="+qty;
       // Toast.makeText(mcontext,"res"+itemlist.getCart_id(),Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject(response);
//
                    // Toast.makeText(mcontext,"updated",Toast.LENGTH_LONG).show();


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
                params.put("cartId", String.valueOf(itemlist.getCart_id()));
                params.put("quantity", String.valueOf(qty));



                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }



    private  void deletecart( final cart_pro itemlist)  {



        String URL_DELETE_ADDRESS="https://www.eazyhomebrunei.com/Android_Api/DeleteCart?cartId="+itemlist.getCart_id();
        // Toast.makeText(mcontext,"res"+itemlist.getCart_id(),Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext(),"res"+response,Toast.LENGTH_LONG).show();
                try{

                    JSONObject jsonObject=new JSONObject(response);

                    int position = fdproductlist.indexOf(itemlist);
                    fdproductlist.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, fdproductlist.size());

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
                params.put("cartId", String.valueOf(itemlist.getCart_id()));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }

    @Override
    public int getItemCount() {
        return fdproductlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,desc,reference,prie,total,count;
        TextView tv1;
        LinearLayout lnr1;
        ImageView delete,img;
        LinearLayout iv_minus,iv_plus;
        ConstraintLayout link;
        TextView price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            desc=itemView.findViewById(R.id.desc);
            reference=itemView.findViewById(R.id.reference);
            prie=itemView.findViewById(R.id.salary);
            total=itemView.findViewById(R.id.total);
            count=itemView.findViewById(R.id.tv_subcat_contetiy);
            delete=itemView.findViewById(R.id.delete);
            iv_minus =itemView.findViewById(R.id.iv_minus);
            iv_plus =itemView.findViewById(R.id.iv_plus);
            img=itemView.findViewById(R.id.im1);
            link=itemView.findViewById(R.id.tot);
            price=itemView.findViewById(R.id.price);
//            prie.setPaintFlags(prie.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

    }
}
