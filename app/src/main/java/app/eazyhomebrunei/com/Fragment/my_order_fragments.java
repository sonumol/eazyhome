package app.eazyhomebrunei.com.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.adapter.my_order_fragment_adapter;
import app.eazyhomebrunei.com.model.my_order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static app.eazyhomebrunei.com.Config.BaseURL.PATH;


public class my_order_fragments extends Fragment {
    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    List<my_order> cpn_productlist;
    private my_order_fragment_adapter latest_adapter;
    String Token;
    TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_order_fragments, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler);
        nodata=v.findViewById(R.id.nodata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cpn_productlist=new ArrayList<>();

        SharedPreferences pref = getActivity().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        Latest_product();

        return v;
    }
    private void Latest_product() {
        final String URL_LATEST=PATH +"MyOrderList?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getActivity(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {



                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("order");
                    // Toast.makeText(getApplicationContext(),"j"+ar,Toast.LENGTH_LONG).show();
//
                    latest_adapter = new my_order_fragment_adapter(getActivity(),cpn_productlist);
                    recyclerView.setAdapter(latest_adapter);
                    // Toast.makeText(getApplicationContext(),"jjj"+ofadapter,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        String order_id=productobject.getString("order_id");
                        String orderId=productobject.getString("orderId");

                        String Name=productobject.getString("Name");
                        String Location=productobject.getString("Location");
                        String TotalAmount=productobject.getString("TotalAmount");
                        String OrderDate=productobject.getString("OrderDate");
                        String status_date=productobject.getString("status_date");
                        String ShippinReference=productobject.getString("ShippinReference");
                        String Flag=productobject.getString("Flag");






                        my_order latest_product=new my_order(order_id,orderId,Name,Location,TotalAmount,OrderDate,status_date,ShippinReference,Flag);
                        cpn_productlist.add(latest_product);





                    }
                } catch (JSONException e) {
               nodata.setVisibility(View.VISIBLE);
               recyclerView.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }


}
