package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Contact extends AppCompatActivity {
    Dialog myDialog;
    LinearLayout send_message;
    LinearLayout home, cart, wishlist, profile, search;
    ImageView left_arrow;
    String Token;
    String SEND_FEEDBACK = "";
    TextView name, address;
    ImageView ph, email, whatsapp, fb, inta, twitter, pinterest, linkedin;
    String phone, ema, wha, fac, insta, twi, pint, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        //Toast.makeText(getApplicationContext(),"jjj"+Token,Toast.LENGTH_LONG).show();
        cartcount();
        send_message = (LinearLayout) findViewById(R.id.lnr1);
        myDialog = new Dialog(this);
        ph = (ImageView) findViewById(R.id.phone);
        email = (ImageView) findViewById(R.id.email);
        whatsapp = (ImageView) findViewById(R.id.whatsapp);
        inta = (ImageView) findViewById(R.id.insta);
        twitter = (ImageView) findViewById(R.id.twittwe);
        pinterest = (ImageView) findViewById(R.id.pinterest);
        linkedin = (ImageView) findViewById(R.id.link);
        fb = (ImageView) findViewById(R.id.facebook);


        left_arrow = (ImageView) findViewById(R.id.arrow);

        home = (LinearLayout) findViewById(R.id.home_layout);
        cart = (LinearLayout) findViewById(R.id.cart_layout);
        wishlist = (LinearLayout) findViewById(R.id.wish_layout);
        profile = (LinearLayout) findViewById(R.id.profile_layout);
        search = (LinearLayout) findViewById(R.id.search1);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Vew_my_wishlist.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), view_my_cart.class);
                startActivity(i);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), search_product.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), my_profile.class);
                startActivity(i);
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
            }
        });


        View_contact_us();

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup();
            }
        });

    }
    private void cartcount() {
        final String URL_LATEST=PATH +"cartCount?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_LATEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {


                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject jk=jsonObject.getJSONObject("data");
                    //  Toast.makeText(getApplicationContext(),"jjj"+jk,Toast.LENGTH_LONG).show();
                    int    count1=jk.getInt("count");
                    TextView count=findViewById(R.id.cou);
                    // Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();
                    ImageView imageView4=findViewById(R.id.imageView4);
                    // Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();
                    imageView4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                            startActivity(i);
                        }
                    });
                    count.setText(String. valueOf ( count1 )) ;



                    //Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                // params.put("mobile_no", phnumber);
                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void View_contact_us() {

        final String CONTACT=PATH +"contactUs";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, CONTACT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

//                   //  Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jsonObject.getJSONArray("data");
//

                    for (int i = 0; i <ar.length(); i++)
                    {
                        JSONObject productobject = ar.getJSONObject(i);
                        String name1=productobject.getString("name");
                        String address1=productobject.getString("address");
                       phone=productobject.getString("phone");
                       ema=productobject.getString("email");
                         fac=productobject.getString("facebook");
                       twi=productobject.getString("twitter");
                        String google=productobject.getString("google");
                        insta=productobject.getString("instagram");
                      pint=productobject.getString("pinterest");
                        String youtube=productobject.getString("youtube");
                        link=productobject.getString("linkedin");
                        String flickr=productobject.getString("flickr");
                        String logo=productobject.getString("logo");
                        String favicon=productobject.getString("favicon");
                        name.setText(name1);
                        address.setText(address1+","+phone);



                        if(phone.isEmpty())
                        {
                            ph.setVisibility(View.GONE);
                        }
                        else
                        {
                            ph.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String phon = phone;
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phon, null));
                                    startActivity(intent);
                                }
                            });
                        }
                        if(ema.isEmpty())
                        {
                            email.setVisibility(View.GONE);
                        }
                        else {
                            email.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("plain/text");
                                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { ema });

                                    startActivity(Intent.createChooser(intent, ""));
                                }
                            });
                        }
                        if(phone.isEmpty())
                        {
                            whatsapp.setVisibility(View.GONE);
                        }
                        else {
                            whatsapp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String w1 = phone;
                                    String w = w1.replace("+", "");
                                    String smsNumber = w;
                                    Intent sendIntent = new Intent("android.intent.action.MAIN");
                                    sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                                    sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
                                    startActivity(sendIntent);
                                }
                            });
                        }
                        if(fac.isEmpty())
                        {
                            fb.setVisibility(View.GONE);
                        }
                        else {
                            fb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = fac;
                                    Uri uriUrl = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);

                                }
                            });
                        }
                        if(insta.isEmpty())
                        {
                            inta.setVisibility(View.GONE);
                        }
                        else {
                            inta.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = insta;
                                    Uri uriUrl = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);
                                }
                            });
                        }
                        if(twi.isEmpty())
                        {
                            twitter.setVisibility(View.GONE);
                        }
                        else {
                            twitter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = twi;
                                    Uri uriUrl = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);
                                }
                            });
                        }
                        if(pint.isEmpty())
                        {
                            pinterest.setVisibility(View.GONE);
                        }
                        else {
                            pinterest.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = pint;
                                    Uri uriUrl = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);
                                }
                            });
                        }
                        if(link.isEmpty())
                        {
                            linkedin.setVisibility(View.GONE);
                        }
                        else {
                            linkedin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String url = link;
                                    Uri uriUrl = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                                    startActivity(launchBrowser);
                                }
                            });
                        }




                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }





    private void ShowPopup() {
        final TextView txtclose,Feedback;
        LinearLayout send;

        myDialog.setContentView(R.layout.send_message_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        send=(LinearLayout)myDialog.findViewById(R.id.send);
        Feedback=(TextView)myDialog.findViewById(R.id.feedback);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final    String feedback=Feedback.getText().toString().trim();


                String URL_CREATE_ACCOUNT=PATH +"feddback?feedback="+feedback;

                StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_CREATE_ACCOUNT, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
//                        Intent i = new Intent(add_address.this, Address.class);
                        Toast.makeText(Contact.this,"Successfully Sended",Toast.LENGTH_LONG).show();
//
//
//                        startActivity(i);
//                        finish();
myDialog.dismiss();



                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Contact.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Contact.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


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
                RequestQueue requestQueue= Volley.newRequestQueue(Contact.this);
                requestQueue.add(stringRequest);

            }
        });

        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }







}
