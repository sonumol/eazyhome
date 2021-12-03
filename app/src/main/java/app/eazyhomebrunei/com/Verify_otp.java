package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.auth.FirebaseAuth;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Verify_otp extends AppCompatActivity {
    LinearLayout verify;
    //EditText otp;
    //  Button resend;

    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    String phonenumber;
    // PinView otp;
    TextView phn,Resend,change_num;

    PinEntryEditText pinEntry;
    SharedPreferences sp;
    String phnumber,phone;
 //  private SmsVerifyCatcher smsVerifyCatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        phn=(TextView)findViewById(R.id.phnumber) ;
        Resend=(TextView)findViewById(R.id.resend) ;
        change_num=(TextView)findViewById(R.id.change) ;
        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        verify =(LinearLayout)findViewById(R.id.otp1);
        progressBar=findViewById(R.id.progress);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
        phnumber=pref.getString("number", null);
        phone=pref.getString("phn",null);
         // Toast.makeText(getApplicationContext(),"p"+phnumber,Toast.LENGTH_LONG).show();
        change_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),otp.class);
                startActivity(i);
            }
        });
        //otp = (PinView) findViewById(R.id.pinView);
      //  progressBar =(ProgressBar) findViewById(R.id.progressbar);
        phn.setText(phone);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = pinEntry.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 4)){

                    pinEntry.setError("Enter code...");
                    pinEntry.requestFocus();
                    return;
                }
                verifyCode();

            }
        });
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend();
                progresss();
                Toast.makeText(getApplicationContext(),"OTP is Resent",Toast.LENGTH_LONG).show();
            }
        });

        change_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
            }
        });
//        this.smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
//            public void onSmsCatch(String message) {
//                //Verify_otp.this.pinEntry.setText(Verify_otp.this.parseCode(message));
//                String code = parseCode(message);//Parse verification code
//                pinEntry.setText(code);
//            }
//        });
    }
    private void progresss() {
        this.progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.GONE);
//            }
//        },2000);
    }
    /* access modifiers changed from: private */
    public String parseCode(String message) {
        Matcher m = Pattern.compile("\\b\\d{4}\\b").matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }
//    public void onStart() {
//        super.onStart();
//        this.smsVerifyCatcher.onStart();
//    }

    /* access modifiers changed from: protected */
//    public void onStop() {
//        super.onStop();
//        this.smsVerifyCatcher.onStop();
//    }

//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        this.smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    private void resend() {

//        loading.setVisibility(View.VISIBLE);
//        lnr1.setVisibility(View.GONE);




        String URL_CREATE_ACCOUNT=PATH +"resendOtp?mobile_no="+phnumber;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                try{
                    JSONObject jsonObject=new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
//                    Intent i = new Intent(Verify_otp.this, Verify_otp.class);
//                    Toast.makeText(Verify_otp.this,"Successfully added",Toast.LENGTH_LONG).show();


//                    startActivity(i);
//                    finish();




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Verify_otp.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Verify_otp.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile_no",phone);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void verifyCode() {

        final    String otp=pinEntry.getText().toString().trim();


         progressBar.setVisibility(View.VISIBLE);

        String URL_CREATE_ACCOUNT=PATH +"check_otp?mobile_no="+phnumber+"&otp="+otp;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
              // Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String message=jsonObject.getString("message");
                    if(message.isEmpty() && !status)
                    {
                        Toast.makeText(getApplicationContext(),"Invalid OTP",Toast.LENGTH_LONG).show();
                    }
                    else if (!status)
                    {
                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
                    }

                    else {
                        check_accout();
                    }



//                     if(!status && message=="OTP TIME EXPIRED")
//                    {
//                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
//                    }
//                    else  if(!status && message=="OTP LIMIT EXCEED")
//                    {
//                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_LONG).show();
//                    }
//                    else if (!status) {
//                        Toast.makeText(getApplicationContext(),""+message, Toast.LENGTH_LONG).show();
//                    }





                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Verify_otp.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Verify_otp.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile_no",phone);
                params.put("otp",otp);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void check_accout() {

        final String phonenumber=phnumber;
        // Toast.makeText(getApplicationContext(),"p"+phonenumber,Toast.LENGTH_LONG).show();
        String URL_CHECK_ACCOUNT=PATH +"CheckCustomer?mobile_no="+phonenumber;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CHECK_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    // Toast.makeText(getApplicationContext(),"Already have an Account",Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    String success=jk.getString("cus_flag");
//                    String lid=jk.getString("user_id");
                    String block=jk.getString("block");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();










                    if(success.equals("1"))
                    {
                      //  Toast.makeText(getApplicationContext(),"Already have an Account",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                    }
                    else if(block.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Sorry, you are blocked from using this app",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                    }
                    else {
                        Intent intent = new Intent(Verify_otp.this, create_account.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }






                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Verify_otp.this,"Regsister error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("mobile_no",phonenumber);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


}
