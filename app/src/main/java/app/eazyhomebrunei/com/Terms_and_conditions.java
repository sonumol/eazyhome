package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

//import retrofit2.http.PATCH;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Terms_and_conditions extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        textView=findViewById(R.id.t1);
        viewtermsandconditions();

    }
    private void viewtermsandconditions() {


        final String URL_FEATURED=PATH +"termsCondition";


        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_FEATURED, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
//                    Home.this.refreshLayout.setRefreshing(false);

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk1=jsonObject.getJSONObject("data");

                    JSONObject jk=jk1.getJSONObject("user_details");
                    String title=jk.getString("title");
                    String description=jk.getString("description");

                    textView.setText(Html.fromHtml(description));



//
//                    name.setText("I'm "+name1);
//                    position.setText(position1);
//                    desc.setText(about);
//
//





                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();

//                    TextView nodata=(TextView)findViewById(R.id.nodata);
//                    nodata.setVisibility(View.VISIBLE);
//                    recyclerView3.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }}
