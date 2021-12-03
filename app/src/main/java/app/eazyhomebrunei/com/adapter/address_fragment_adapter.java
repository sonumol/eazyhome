package app.eazyhomebrunei.com.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import app.eazyhomebrunei.com.edit_address;
import app.eazyhomebrunei.com.model.addres_list;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class address_fragment_adapter extends RecyclerView.Adapter<address_fragment_adapter.viewHolder> {
    private List<addres_list> addresslist;

    private Context mcontext;
    private int id;

    public address_fragment_adapter(Context mcontext,List<addres_list> listitem) {
        this.addresslist = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public address_fragment_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_fragment_adapter,parent,false);
        mcontext = parent.getContext();

        return  new address_fragment_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final address_fragment_adapter.viewHolder holder, final int position) {
        final addres_list product = addresslist.get(position);
        String name = product.getName();
        String phone=product.getPhone();
        id=product.getId();
        int userid=product.getCustomer_id();

        String address=product.getAddress();

        String city=product.getCity();
        holder.name.setText(name+" - "+phone);
        holder.address.setText(address+" - "+city);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.edit.getContext(), edit_address.class);
               // map.put("product_id", modelList.get(position).getProduct_id());
                intent.putExtra("id",addresslist.get(position).getId());
                intent.putExtra("userid",addresslist.get(position).getCustomer_id());


                holder.edit.getContext().startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String title = "Warning!";
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mcontext.getResources().getColor(R.color.colorPrimary));

                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
                ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Do you want to delete this Address?");
                alertbox.setTitle(ssBuilder);

                alertbox.setNegativeButton("Yes",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                delete_address(product);
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

//
    }

    private void delete_address(final addres_list product) {



        String URL_DELETE_ADDRESS="https://www.eazyhomebrunei.com/Android_Api/DeleteAaddress?id="+id;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(mcontext(),"res"+response,Toast.LENGTH_LONG).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
//                    Intent intent = new Intent(mcontext, Address.class);
//                    mcontext.startActivity(intent);
                    int position = addresslist.indexOf(product);
                    addresslist.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, addresslist.size());

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
                params.put("id", String.valueOf(id));


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        requestQueue.add(stringRequest);


    }


    @Override
    public int getItemCount() {
        if (addresslist == null)

            return 0;

        return addresslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name,address;
        TextView tv1;
        LinearLayout lnr1;
        ImageView edit,delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            edit=itemView.findViewById(R.id.edite);
            delete=itemView.findViewById(R.id.delete);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);

        }

    }
}
