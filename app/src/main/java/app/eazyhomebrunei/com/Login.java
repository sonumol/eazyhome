package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Login extends AppCompatActivity {
    private  long backpressed;
    private  Toast backtoast;

    LinearLayout lnr1;
    EditText phn;

    String fullnumber;
    SharedPreferences sp;
    ProgressBar progressBar;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lnr1 =(LinearLayout)findViewById(R.id.otp1);

        phn=(EditText)findViewById(R.id.phn) ;
        progressBar=findViewById(R.id.progress);

        lnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = phn.getText().toString();
                String MobilePattern = "[0-9]{10}";

//                String phonenumber = "+" + cpp.getFullNumber() + number;
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("phn", number);
                editor.putString("number",number);

                editor.apply();

                otp_login();


            }


        });
//        cpp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getnumber();//get country code
//
//            }
//        });
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    ///////////////get country code//////////////////////////
//    private void getnumber() {
//        fullnumber=cpp.getFullNumber()+phn.getText().toString();
//        phn.setText(fullnumber);
//    }



    private void otp_login() {



        final    String phone=phn.getText().toString().trim();
        if(phone.equals(""))
        {
            phn.setError(" Error");
        }
        else {
            progressBar.setVisibility(View.VISIBLE);


            String URL_CREATE_ACCOUNT = PATH +"login?mobile_no=" + phone;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                  //  Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                         JSONObject jk=jsonObject.getJSONObject("data");
                        String Token = jk.getString("Token");
                        boolean status = jsonObject.getBoolean("status");
                        message=jsonObject.getString("message");

                        if (!status && Token.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
                        }
                        else {
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("Token", Token);
                            editor.apply();
                            Intent i = new Intent(Login.this, Verify_otp.class);


                            startActivity(i);
                            finish();

                        }






                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                     //   Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile_no", phone);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }


}

