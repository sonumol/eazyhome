package app.eazyhomebrunei.com.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.eazyhomebrunei.com.Config.BaseURL;
import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.Single_product;
import app.eazyhomebrunei.com.catgeory1;
import app.eazyhomebrunei.com.view_my_cart;

public class categoryproductsadapter extends RecyclerView.Adapter<categoryproductsadapter.viewHolder> {
    private List<catgeory1> latest_productlist;

    private Context mcontext;
    String Token;
    int pid;
    int wishId;
    private categoryproductsadapter.CartItemClickListener mListener;

    public interface CartItemClickListener{
        void CartItemClick(int position);
    }
    public void setOnItemClickListener(categoryproductsadapter.CartItemClickListener listener){
        mListener =listener;
    }

    public categoryproductsadapter( Context mcontext,List<catgeory1> listitem) {
        this.latest_productlist = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public categoryproductsadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_latest_products_adapter,parent,false);
        mcontext=parent.getContext();

        return  new categoryproductsadapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final categoryproductsadapter.viewHolder holder, final int position) {
        final catgeory1 product=latest_productlist.get(position);
        //imageurl=product.getImage();
        SharedPreferences pref = mcontext.getSharedPreferences("userid", Context.MODE_PRIVATE);
        Token=pref.getString("Token", null);

        holder.tb_wishlist.setOnCheckedChangeListener(null);
        // String short_disc = product.getShort_description();
        String price=product.getPrice();

        String offerprice=product.getOfferPrice();
        pid=product.getPid();
        wishId=product.getWishId();

        String pname=product.getPname();
        holder.catagory.setText(pname);
        if(offerprice.equals("0.00"))
        {
            holder.price.setText("BND."+price);
            holder.price.setTextColor(Color.BLACK);
        }
        else
        {
            holder.price.setText("BND."+price);
            holder.offer_price.setText("BND."+offerprice);
            holder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (Integer.parseInt(String.valueOf(product.getFlag())) == 1) {
            holder.tb_wishlist.setChecked(true);
            holder.tb_wishlist.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.ic_favorite_black));
        } else {
            holder.tb_wishlist.setChecked(false);
            holder.tb_wishlist.setBackgroundDrawable(mcontext.getResources().getDrawable(R.drawable.ic_favorite_border_black));
        }
        holder.tb_wishlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //   Toast.makeText(mcontext,"flag"+product.getFlag(),Toast.LENGTH_LONG).show();

                if (b) {
                    holder.tb_wishlist.setChecked(true);
                    holder.tb_wishlist.setBackgroundDrawable(categoryproductsadapter.this.mcontext.getResources().getDrawable(R.drawable.ic_favorite_black));
                    categoryproductsadapter.this.addToWishList(product.getPid(), position);
                    return;
                }
                holder.tb_wishlist.setChecked(false);
                holder.tb_wishlist.setBackgroundDrawable(categoryproductsadapter.this.mcontext.getResources().getDrawable(R.drawable.ic_favorite_border_black));
                categoryproductsadapter.this.remove_wishlist(product.getWishId(), position);
            }
        });
        if (product.getStatus() == 0) {
            holder.add_cart.setText("ADD TO CART");
            // holder.ll_add_to_cart.setTag(allProducts);
            holder.add_cart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    //Toast.makeText(mcontext,"ADD TO CART",Toast.LENGTH_LONG).show();
                    if (holder.add_cart.getText().toString().matches("IN CART")) {
                        categoryproductsadapter.this.showDialog();
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(mcontext);
                        progressDialog.setMessage("Loading..."); // Setting Message
                        progressDialog.setTitle("ADD TO CART"); // Setting Title
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                        progressDialog.show(); // Display Progress Dialog
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    categoryproductsadapter.this.addToCart(1, product.getPid(), position);
                                    holder.add_cart.setText("IN CART");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                    }
                }
            });
        } else if (product.getStatus() == 1) {
            holder.add_cart.setText("IN CART");
            holder.add_cart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    categoryproductsadapter.this.showDialog();
                }
            });
        }



        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.img.getContext(), Single_product.class);
//                // map.put("product_id", modelList.get(position).getProduct_id());
                intent.putExtra("image",latest_productlist.get(position).getImage());

                intent.putExtra("id",latest_productlist.get(position).getPid());
                intent.putExtra("pname",latest_productlist.get(position).getPname());
//                intent.putExtra("short_disc",latest_productlist.get(position).getShort_description());
//                intent.putExtra("detailed_description",latest_productlist.get(position).getDetailed_description());
                intent.putExtra("status",latest_productlist.get(position).getFlag());
                intent.putExtra("flag",latest_productlist.get(position).getFlag());
                intent.putExtra("price",latest_productlist.get(position).getPrice());
                intent.putExtra("offer_price",latest_productlist.get(position).getOfferPrice());
                intent.putExtra("wishid",latest_productlist.get(position).getWishId());



                holder.img.getContext().startActivity(intent);

            }
        });


        Picasso with = Picasso.with(mcontext);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.PRODUCT_IMG_PATH);
        sb.append(product.getImage());
        with.load(sb.toString()).placeholder(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).error(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).into(holder.img);


    }
    private void addToCart(int quantity, final int pid,  final int position) {
        String URL_ADD_TO_CART="https://www.eazyhomebrunei.com/Android_Api/addTocart?pid="+pid+"&quantity="+1;
        // Toast.makeText(mcontext,"res"+lid,Toast.LENGTH_LONG).show();


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_ADD_TO_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext,"res"+response,Toast.LENGTH_LONG).show();

                if(position!=RecyclerView.NO_POSITION){
                    mListener.CartItemClick(position);

                }


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
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }
    public void showDialog() {
        String title = "Note!";
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(this.mcontext.getResources().getColor(R.color.colorPrimary));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
        builder1.setTitle(ssBuilder);
        builder1.setMessage("Product is already in cart.");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder1.setNegativeButton("GO TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(mcontext, view_my_cart.class);
                mcontext.startActivity(intent);
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.setCanceledOnTouchOutside(false);
        alert11.show();
    }
    private void addToWishList(final int pid, final int position) {



        String URL_ADD_TO_WISHLIST="https://www.eazyhomebrunei.com/Android_Api/AddToWishlist?pid="+pid;
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
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }
    private void remove_wishlist(final int wishId, int position) {



        String URL_DELETE_ADDRESS="https://www.eazyhomebrunei.com/Android_Api/RemoveWishlistProduct?wishId="+wishId;


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(mcontext(),"res"+response,Toast.LENGTH_LONG).show();
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
//                    int position = fdproductlist.indexOf(wishId);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("wishId", String.valueOf(wishId));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }
    @Override
    public int getItemCount() {
        return latest_productlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView price,offer_price,catagory,add_cart;
        TextView tv1;
        LinearLayout lnr1;
        //       myDialog = new Dialog();
        ImageView img;
        ToggleButton tb_wishlist;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.image_view);
            catagory=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
//            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            offer_price=itemView.findViewById(R.id.offer_price);
            add_cart=itemView.findViewById(R.id.add_cart);
            tb_wishlist = (ToggleButton) itemView.findViewById(R.id.tb_wishlist);


        }

    }
}
