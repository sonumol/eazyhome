package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Detailactivity extends AppCompatActivity {
    Button add_address;
    EditText ed_name, ed_email, ed_phone, ed_address, ed_pincode, ed_city;
    String lid;
    int id;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailactivity);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        Toast.makeText(getApplicationContext(), "id" + id, Toast.LENGTH_LONG).show();
        //viewsingle_product();

    }
}
//    private void viewsingle_product() {
//
//
//
//        String URL_CREATE_ACCOUNT="SingleOrderDetails?order_id="+1;
//
//        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
//                try{
//                    JSONObject jsonObject=new JSONObject(response);
//                    //JSONObject jk=jsonObject.getJSONObject("data");
//                    JSONArray ar=jsonObject.getJSONArray("data");
//                    //Toast.makeText(getApplicationContext(),"res"+ar,Toast.LENGTH_LONG).show();
//
//                    for (int i = 0; i <ar.length(); i++) {
//                        JSONObject productobject = ar.getJSONObject(i);
//
//                        String name = productobject.getString("name");
//                        String phone = productobject.getString("phone");
//                        String email = productobject.getString("email");
//
//                        String address = productobject.getString("address");
//                        String state = productobject.getString("state");
//                        String city = productobject.getString("city");
//                        String postal_code = productobject.getString("postal_code");
//                        ed_name.setText(name);
//                        ed_phone.setText(phone);
//                        ed_email.setText(email);
//                        ed_address.setText(address);
//                        //ed_s.setText(state);
//                        ed_city.setText(city);
//                        ed_pincode.setText(postal_code);
//
//
////}
//
//                    }
////
//////
////
////
//
//
//
//                }
//                catch (JSONException e)
//                {
//                    e.printStackTrace();
//                    Toast.makeText(edit_address.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
////                    loading.setVisibility(ed.GONE);
////                    lnr1.setVisibility(View.VISIBLE);
//
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(edit_address.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//                })
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("id", String.valueOf(id));
//
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }
//}
