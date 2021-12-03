package app.eazyhomebrunei.com.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.adapter.my_coupon_fragment_adapter;
import app.eazyhomebrunei.com.model.coupon_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class my_coupon_fragment extends Fragment {
    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    List<coupon_model> cpn_productlist;
    private my_coupon_fragment_adapter latest_adapter;
    private static final String URL_LATEST="home";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_my_coupon_fragment, container, false);
        recyclerView=(RecyclerView)v.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cpn_productlist=new ArrayList<>();
        Latest_product();

        return v;
    }

    private void Latest_product() {
         final String URL_LATEST= PATH +"Mycoupon";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  //Toast.makeText(getActivity(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {



                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();

                    JSONArray ar=jk.getJSONArray("coupon");
                    // Toast.makeText(getApplicationContext(),"j"+ar,Toast.LENGTH_LONG).show();
//
                    latest_adapter = new my_coupon_fragment_adapter(getActivity(),cpn_productlist);
                    recyclerView.setAdapter(latest_adapter);
                    // Toast.makeText(getApplicationContext(),"jjj"+ofadapter,Toast.LENGTH_LONG).show();


                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);

                        String Reference=productobject.getString("Reference");
                        String StartDate=productobject.getString("StartDate");

                        String StartTime=productobject.getString("StartTime");
                        String EndDate=productobject.getString("EndDate");
                        String EndTime=productobject.getString("EndTime");
                        String title=productobject.getString("title");
                        String description=productobject.getString("description");
                        String image=productobject.getString("image");
                        String code=productobject.getString("code");
                        String value=productobject.getString("value");





                        coupon_model latest_product=new coupon_model(Reference,StartDate,StartTime,EndDate,EndTime,title,description,image,code,value);
                        cpn_productlist.add(latest_product);





                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }


}
