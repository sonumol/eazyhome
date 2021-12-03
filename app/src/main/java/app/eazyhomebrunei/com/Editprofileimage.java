package app.eazyhomebrunei.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import app.eazyhomebrunei.com.Config.BaseURL;

import com.squareup.picasso.Picasso;

//import net.gotev.uploadservice.MultipartUploadRequest;
//import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Editprofileimage extends AppCompatActivity {
    ImageView img;
    Button up;
    EditText name;
    TextView phn;

    String lid;
    String phone1,email1,address1,image,name1;
    String path;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;


    ImageView imageView;
    EditText editTextName, editTextEmail;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;

    public static final String UPLOAD_URL = "https://www.eazyhomebrunei.com/Android_Api/UpdateProfile?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofileimage);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        lid=pref.getString("lid", null);
        name=(EditText)findViewById(R.id.name);
        phn=(TextView)findViewById(R.id.phn);
        img=(ImageView)findViewById(R.id.img);
        up=(Button)findViewById(R.id.up);
        left_arrow=(ImageView)findViewById(R.id.arrow);

        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search);
        requestStoragePermission();

//        startServiceOreoCondition();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Home.class);
                startActivity(i);
            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Vew_my_wishlist.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),view_my_cart.class);
                startActivity(i);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),search_product.class);
                startActivity(i);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),my_profile.class);
                startActivity(i);
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),my_profile.class);
                startActivity(i);
            }
        });
        view_details();
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();



            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFileChooser();


            }
        });


    }
    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"MyProfile?user_id="+lid;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_MY_WISHLIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                 //   Toast.makeText(getApplicationContext(),"jjj"+lid,Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONObject productobject=jk.getJSONObject("profile");
//
//
//email1,address1,image
////
//                    for (int i = 0; i <ar.length(); i++)
//                    {
                 name1=productobject.getString("name");

                    phone1=productobject.getString("phone");
                    email1=productobject.getString("email");
                     address1=productobject.getString("address");
                     image=productobject.getString("image");
//                        int status=productobject.getInt("Status");
//                        String price=productobject.getString("Price");
//                        String offer_price=productobject.getString("OfferPrice");

                    Picasso with = Picasso.with(getApplicationContext());
                    StringBuilder sb = new StringBuilder();
                    sb.append(BaseURL.PROFILE_IMAGE);
                    sb.append(image);

                    with.load(sb.toString()).placeholder(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).error(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).into(img);
                    //Toast.makeText(getApplicationContext(),"jjj"+image,Toast.LENGTH_LONG).show();

                    name.setText(name1);
                    phn.setText(phone1);
                    // phn.setText(phone1);

//
//
//
//                    }
//


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
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                img.setImageBitmap(bitmap);
            } catch (Exception ex) {

            }
        }
    }

//    public void selectImage(View view) {
//
//    }


    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }
    //https://www.eazyhomebrunei.com/Android_Api/UpdateProfile?user_id=" + "16" + "&name=" + "name1" + "&email=" + "email1" + "&address=" + "address1" + "&image=" + filename

//    private void uploadImage() {
////        String name = editTextName.getText().toString().trim();
////        String email = editTextEmail.getText().toString().trim();
//        String path = getPath(filepath);
//        try {
//            String uploadId = UUID.randomUUID().toString();
//            new MultipartUploadRequest(this, uploadId, UPLOAD_URL).addFileToUpload(path, "image").addParameter("name", name1).addParameter("email", email1).addParameter("user_id",lid).addParameter("address",address1)
//                    .setNotificationConfig(new UploadNotificationConfig())
//                    .setMaxRetries(3)
//                    .startUpload();
//
//        } catch (Exception ex) {
//
//
//        }
//
//    }

//
//    public void saveData(View view)
//    {
//        uploadImage();
//    }

    private  void uploadImage()
    {
        final String path = getPath(filepath);

        String URL_Edit_ACCOUNT=PATH+"UpdateProfile?user_id=" + lid + "&name=" + name1 + "&email=" + email1 + "&address=" + address1 + "&image=" + path;

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Edit_ACCOUNT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  //Toast.makeText(getApplicationContext(),"res"+response,Toast.LENGTH_LONG).show();
                try{
                    JSONObject jsonObject=new JSONObject(response);
//                    JSONObject jk=jsonObject.getJSONObject("data");
//                    String lid=jk.getString("user_id");
//                    SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("lid",lid);
//                    editor.apply();
                    Intent i = new Intent(Editprofileimage.this, my_profile.class);
                 //   Toast.makeText(Editprofileimage.this,"Edited Successfully",Toast.LENGTH_LONG).show();


                    startActivity(i);
                    finish();




                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Editprofileimage.this,"add address error"+e.toString(),Toast.LENGTH_LONG).show();
//                    loading.setVisibility(View.GONE);
//                    lnr1.setVisibility(View.VISIBLE);

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Editprofileimage.this,error.getMessage().toString(), Toast.LENGTH_SHORT).show();


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
              //  String URL_Edit_ACCOUNT=PATH+"user_id=" + lid + "&name=" + name1 + "&email=" + email1 + "&address=" + address1 + "&image=" + imagetoString(bitmap);

                params.put("user_id", String.valueOf(lid));
                params.put("name",name1);
                params.put("email",email1);
                params.put("address",address1);
              params.put("image",path);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private  String imagetoString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
}
