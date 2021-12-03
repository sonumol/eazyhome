package app.eazyhomebrunei.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.model.cart_pro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VIEWMYCART extends RecyclerView.Adapter<VIEWMYCART.viewHolder> {
    private List<cart_pro> fdproductlist;

    private Context mcontext;
    String imageurl;
    int wish_id;
    String lid;
    public VIEWMYCART(Context mcontext,List<cart_pro> listitem) {
        this.fdproductlist = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public VIEWMYCART.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_wishlist_adapter,parent,false);
        mcontext=parent.getContext();
        // Toast.makeText(v.getContext(), "imagee"+imageurl, Toast.LENGTH_SHORT).show();

        return  new VIEWMYCART.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VIEWMYCART.viewHolder holder, final int position) {
        final cart_pro product=fdproductlist.get(position);

        final String name=product.getPname();
       final int cartid=product.getCart_id();
        String reference=product.getReference();
        final int price= Integer.parseInt(String.valueOf(product.getPrice()));
//        int total_price=product.getTotal_price();
//        int count1=product.getQuantity();




        holder.name.setText(name);
        holder.reference.setText(reference);
        holder.prie.setText("BND."+price);
//        holder.total.setText("₹"+total_price);
//        holder.count.setText(""+count1);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletecart(product);

            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //  Toast.makeText(v.getContext(), "imagee"+cartid, Toast.LENGTH_SHORT).show();





            }
        });





    }

    private  void deletecart(final cart_pro product )  {



        String URL_DELETE_ADDRESS="https://www.eazyhomebrunei.com/Android_Api/DeleteCart?cartId="+product.getCart_id();
        Toast.makeText(mcontext,"res"+product.getCart_id(),Toast.LENGTH_LONG).show();


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
                    int position = fdproductlist.indexOf(product);
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
                params.put("cartId", String.valueOf(product.getCart_id()));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }


    @Override
    public int getItemCount() {
        if(fdproductlist==null)

            return 0;

        return fdproductlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,desc,reference,prie,total,count;
        TextView tv1;
        LinearLayout lnr1;
        ImageView delete;
        LinearLayout iv_minus,iv_plus;

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


        }

    }
}
