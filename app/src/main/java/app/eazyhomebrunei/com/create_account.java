package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;
import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class create_account extends AppCompatActivity {


    LinearLayout lnr1;
   private EditText ed_name,ed_email,ed_pswd;
    private ProgressBar loading;
    String phnumber;
    AwesomeValidation awesomeValidation;
    TextView t1,t3;
    CheckBox terms;
    TextView agree;
   // private static  String URL_CREATE_ACCOUNT="CreateAccount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        awesomeValidation =new AwesomeValidation(BASIC);

        lnr1 =(LinearLayout)findViewById(R.id.create);
        ed_name =(EditText) findViewById(R.id.name);
        ed_email =(EditText) findViewById(R.id.email);
        ed_pswd =(EditText) findViewById(R.id.pswd);
        loading=(ProgressBar)findViewById(R.id.loading);
        terms=(CheckBox)findViewById(R.id.checkBox);
        agree=findViewById(R.id.agrr);

        t1=(TextView) findViewById(R.id.t1);

        t3=(TextView) findViewById(R.id.t3);




        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref1", MODE_PRIVATE);
        phnumber=pref.getString("number", null);
       //Toast.makeText(getApplicationContext(),"p"+phnumber,Toast.LENGTH_LONG).show();

        lnr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Terms_and_conditions.class);
                startActivity(i);
            }
        });
    }

    public void showDialog() {
        Enter_popup exapleDialog=new Enter_popup();
        exapleDialog.show(getSupportFragmentManager(),"exampledialog");
    }
    public void showDialog1() {
        Enter_password exapleDialog=new Enter_password();
        exapleDialog.show(getSupportFragmentManager(),"exampledialog");
    }
    public void showDialog2() {
        Terms_conditions exapleDialog=new Terms_conditions();
        exapleDialog.show(getSupportFragmentManager(),"exampledialog");
    }
    private void Register() {

//        loading.setVisibility(View.VISIBLE);
//        lnr1.setVisibility(View.GONE);

        final String name = ed_name.getText().toString().trim();
        final String email = ed_email.getText().toString().trim();
        final String pswd = ed_pswd.getText().toString().trim();
        if (name.equals("")) {
          ed_name.setError("Enter name");

        } else if (email.equals("")) {
            ed_email.setError("Enter Email");
        }
            else if (pswd.equals("")) {
            ed_pswd.setError("Enter Password");

        } else if(terms.isChecked()==false){
            showDialog2();

        }
            else {
            String URL_CREATE_ACCOUNT = PATH +"CreateAccount?mobile_no=" + phnumber + "&name=" + name + "&email=" + name + "&password=" + pswd;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
//                        JSONObject jk=jsonObject.getJSONObject("data");
//                        String lid=jk.getString("user_id");
//                        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString("lid",lid);
//                        editor.apply();
                        Intent i = new Intent(create_account.this, Home.class);
                        Toast.makeText(create_account.this, "Regsister success", Toast.LENGTH_LONG).show();


                        startActivity(i);
                        finish();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(create_account.this, "Regsister error" + e.toString(), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                        lnr1.setVisibility(View.VISIBLE);

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(create_account.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobile_no", phnumber);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", pswd);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }

}
