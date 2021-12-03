package app.eazyhomebrunei.com.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.Login;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.edittprofileinterface;
import app.eazyhomebrunei.com.my_profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static app.eazyhomebrunei.com.Config.BaseURL.PATH;


public class settingfargment extends Fragment {

String Token;
SharedPreferences prefs;

ImageView edit,edit1,edit2;
Button update;
EditText addr1,email1,name1;
String name;
ImageView logout;
String phone1,gmail,address1,image;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_settingfargment, container, false);
        prefs = this.getActivity().getSharedPreferences("userid",  MODE_PRIVATE);
        Token = prefs.getString("Token", null);


        logout=(ImageView)v.findViewById(R.id.logout);

        edit=(ImageView)v.findViewById(R.id.edit);
//        edit1=(ImageView)v.findViewById(R.id.edit1);
//        edit2=(ImageView)v.findViewById(R.id.edit3);

        addr1=(EditText) v.findViewById(R.id.adr1);
        email1=(EditText) v.findViewById(R.id.email1);

        name1=v.findViewById(R.id.name);


//Toast.makeText(getContext(),"a"+Token,Toast.LENGTH_LONG).show();

        update=(Button) v.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateprofilesettings();
            }
        });

//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                update.setVisibility(View.VISIBLE);
//                edit.setVisibility(View.INVISIBLE);
//
//                addr1.setVisibility(View.VISIBLE);
//                edit1.setVisibility(View.VISIBLE);
//               edit2.setVisibility(View.VISIBLE);
////                email1.setVisibility(View.INVISIBLE);
//
//
//
//            }
//        });
//        edit1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                update.setVisibility(View.VISIBLE);
//                edit1.setVisibility(View.INVISIBLE);
//                email1.setVisibility(View.VISIBLE);
//
//                edit.setVisibility(View.VISIBLE);
//                edit2.setVisibility(View.VISIBLE);
////                addr1.setVisibility(View.INVISIBLE);
//
//
//
//            }
//        });
//        edit2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                name1.setVisibility(View.VISIBLE);
//                update.setVisibility(View.VISIBLE);
//                edit2.setVisibility(View.GONE);
//                edit.setVisibility(View.VISIBLE);
//                edit1.setVisibility(View.VISIBLE);
//            }
//        });
logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            String title = "Logout!";
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
            SpannableStringBuilder ssBuilder = new SpannableStringBuilder(title);
            ssBuilder.setSpan(foregroundColorSpan, 0, title.length(), 33);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setTitle(ssBuilder);
            builder1.setMessage("Are you sure you really want to logout?");
            builder1.setCancelable(true);
            builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent i=new Intent(getActivity(),Login.class);
                    SharedPreferences.Editor editor=getActivity().getSharedPreferences("MyPref",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    startActivity(i);
                  getActivity().finish();
                   // settingfargment.this.();
                }
            });
            builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder1.create().show();


    }
});


        // Inflate the layout for this fragment
                view_details();
return v;
    }

    private void updateprofilesettings() {

        final    String address=addr1.getText().toString();
        final  String email=email1.getText().toString();
        final   String name3=name1.getText().toString();
        if(name3.equals(""))
        {
            name1.setError("Enter name");
        }
        else if(!name3.matches("^[A-Za-z ]+$")){
            name1.setError("Enter name");
        }
        else
        {
            Map<String,String> headers=new HashMap<>();
            headers.put("Token",Token);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(edittprofileinterface.IMAGEURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            edittprofileinterface ed = retrofit.create(edittprofileinterface.class);
            Call<String> call = ed.getImageData(Token,address,email,name3);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {


                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            Intent i=new Intent(getContext(),my_profile.class);
                            startActivity(i);
                            Toast.makeText(getContext(), "Updated Successfully!!", Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    }else{

                        Toast.makeText(getContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }



                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(getContext(), "Error occurred!", Toast.LENGTH_SHORT).show();

                }
            });
        }
       // Toast.makeText(getActivity(),""+email,Toast.LENGTH_LONG).show();

//        String URL_Edit_ACCOUNT=PATH +"UpdateProfile?name="+name+"&email="+email+"&address="+address;
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Edit_ACCOUNT, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getContext(),"res"+response,Toast.LENGTH_LONG).show();
//                try{
//                    JSONObject jsonObject=new JSONObject(response);
////                    JSONObject jk=jsonObject.getJSONObject("data");
////                    String lid=jk.getString("user_id");
////                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
////                    SharedPreferences.Editor editor = pref.edit();
////                    editor.putString("lid",lid);
////                    editor.apply();
////                    Intent i = new Intent(getActivity(), my_profile.class);
////                    Toast.makeText(getActivity(),"Edited Successfully",Toast.LENGTH_LONG).show();
////
////
////                    startActivity(i);
//                    //finish();
//
//
//
//
//                }
//                catch (JSONException e)
//                {
////                    e.printStackTrace();
////                    Toast.makeText(getActivity(),"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//////                    loading.setVisibility(View.GONE);
////                    lnr1.setVisibility(View.VISIBLE);
//
//                }
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                       // Toast.makeText(settingfargment.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//                })
//
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("Token", Token);
//
//                return params;
//            }
//        };
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        requestQueue.add(stringRequest);
//







    }




    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"MyProfile?";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //Toast.makeText(getActivity(),"jjj"+response,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONObject productobject=jk.getJSONObject("profile");
                    //Toast.makeText(getActivity(),"jjj"+productobject,Toast.LENGTH_LONG).show();
//
//
//
////
//                    for (int i = 0; i <ar.length(); i++)
//                    {
                      name=productobject.getString("name");

                      phone1=productobject.getString("phone");
                         gmail=productobject.getString("email");
                         address1=productobject.getString("address");
                         image=productobject.getString("image");
//                        int status=productobject.getInt("Status");
//                        String price=productobject.getString("Price");
//                        String offer_price=productobject.getString("OfferPrice");



                    addr1.setText(address1);
                    email1.setText(gmail);
                    name1.setText(name);

//
//
//
//                    }
//


                } catch (JSONException e) {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
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
