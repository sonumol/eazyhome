package app.eazyhomebrunei.com;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class otp_generate extends AppCompatActivity {
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate);
        phn=(TextView)findViewById(R.id.phnumber) ;
        Resend=(TextView)findViewById(R.id.resend) ;
        change_num=(TextView)findViewById(R.id.change) ;
        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        verify =(LinearLayout)findViewById(R.id.otp1);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        phnumber=pref.getString("number", null);
        phone=pref.getString("phn",null);
     //  Toast.makeText(getApplicationContext(),"p"+phnumber,Toast.LENGTH_LONG).show();
        change_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),otp.class);
                startActivity(i);
            }
        });
        //otp = (PinView) findViewById(R.id.pinView);
        progressBar =(ProgressBar) findViewById(R.id.progressbar);


        mAuth = FirebaseAuth.getInstance();

        phn.setText(phone);
        sendVerificationCode(phone);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = pinEntry.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    pinEntry.setError("Enter code...");
                    pinEntry.requestFocus();
                    return;
                }
                verifyCode(code);

            }
        });
        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), otp_generate.class);
                intent.putExtra("phonenumber", phone);
               // Toast.makeText(getApplicationContext(),"Your number is"+phone,Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {



                            check_accout();
//

                        } else {
                            Toast.makeText(otp_generate.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
               progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(otp_generate.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
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
                    String lid=jk.getString("user_id");
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("lid",lid);
                    editor.apply();
                    if(success.equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"Already have an Account",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),Home.class);
                        startActivity(i);
                    }
                    else {
                        Intent intent = new Intent(otp_generate.this, create_account.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                    }

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(otp_generate.this,"Regsister error"+e.toString(),Toast.LENGTH_LONG).show();
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
