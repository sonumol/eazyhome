package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import app.eazyhomebrunei.com.Config.BaseURL;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class Editprofilenew extends AppCompatActivity {
    ImageView GetImageFromGalleryButton;
       Button     UploadImageOnServerButton;
    ProgressBar progressBar;
//    ImageView ShowSelectedImage;
    Bitmap FixBitmap;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private int GALLERY = 1, CAMERA = 2;
    String currentDateandTime;
    String Token;
    String name1,email1,image,phone1,address1;
    TextView name,phn;
    TextView nam;
    LinearLayout home,cart,wishlist,profile,search;
    ImageView left_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofilenew);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("userid", MODE_PRIVATE);
        Token=pref.getString("Token", null);
        nam=findViewById(R.id.name);
cartcount();
        GetImageFromGalleryButton = (CircleImageView)findViewById(R.id.img);

        UploadImageOnServerButton = (Button)findViewById(R.id.up);

//        ShowSelectedImage = (ImageView)findViewById(R.id.imageView);
        name=(TextView) findViewById(R.id.name);
        phn=(TextView)findViewById(R.id.phn);

//        imageName=(EditText)findViewById(R.id.imageName);

        byteArrayOutputStream = new ByteArrayOutputStream();

//        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                showPictureDialog();
//
//
//            }
//        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        currentDateandTime = sdf.format(new Date());
        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                name1 = nam.getText().toString();
//
//
//                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
//
//                byteArray = byteArrayOutputStream.toByteArray();
//
//                ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                uploadImageUsingRetrofit();
                showPictureDialog();
            }
        });
        view_details();

        if (ContextCompat.checkSelfPermission(Editprofilenew.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }




        home=(LinearLayout)findViewById(R.id.home_layout);
        cart=(LinearLayout)findViewById(R.id.cart_layout);
        wishlist=(LinearLayout)findViewById(R.id.wish_layout);
        profile=(LinearLayout)findViewById(R.id.profile_layout);
        search=(LinearLayout)findViewById(R.id.search1);
        left_arrow=(ImageView)findViewById(R.id.arrow);
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

                        count.setText(String. valueOf ( count1 )) ;



                    //Toast.makeText(getApplicationContext(),"jjj"+count1,Toast.LENGTH_LONG).show();


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
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    GetImageFromGalleryButton.setImageBitmap(FixBitmap);
                FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);

                byteArray = byteArrayOutputStream.toByteArray();

                ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                uploadImageUsingRetrofit();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Editprofilenew.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            GetImageFromGalleryButton.setImageBitmap(FixBitmap);
            //UploadImageOnServerButton.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


//    public void UploadImageToServer(){
//
//        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream);
//
//        byteArray = byteArrayOutputStream.toByteArray();
//
//        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
//
//            @Override
//            protected void onPreExecute() {
//
//                super.onPreExecute();
//
//                progressDialog = ProgressDialog.show(Editprofilenew.this,"Image is Uploading","Please Wait",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String string1) {
//
//                super.onPostExecute(string1);
//
//                progressDialog.dismiss();
//
//                Toast.makeText(Editprofilenew.this,string1,Toast.LENGTH_LONG).show();
//                Intent i=new Intent(getApplicationContext(),my_profile.class);
//                startActivity(i);
//
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//           //  postData(params[0]);
//
//
//                ImageProcessClass imageProcessClass = new ImageProcessClass();
//
//                HashMap<String,String> HashMapParams = new HashMap<String,String>();
//
//                HashMapParams.put(ImageTag, currentDateandTime);
//
//                HashMapParams.put(ImageName, ConvertImage);
//                HashMapParams.put("Token", Token);
//                HashMapParams.put("name", name1);
//                HashMapParams.put("email", email1);
//                HashMapParams.put("address", address1);
//
//
//                //UpdateProfile?user_id=" + lid + "&name=" + name1 + "&email=" + email1 + "&address=" + address1 + "&image=" + imagetoString(bitmap)
//                String FinalData = imageProcessClass.ImageHttpRequest("https://www.eazyhomebrunei.com/Android_Api/UpdateProfile?", HashMapParams);
//              //  Toast.makeText(getApplicationContext(),""+FinalData,Toast.LENGTH_LONG).show();
//
//                return FinalData;
//               // return  null;
//
//                //String URL_Edit_ACCOUNT="https://www.eazyhomebrunei.com/Android_Api/""
//            }
//
//            private void postData(Void param) {
////                HttpClient httpclient = new DefaultHttpClient();
////                HttpPost httppost = new HttpPost(PATH+"UpdateProfile?");
////                httppost.addHeader( "Token" , Token );
////
////
////                try {
////                    // Add your data
////                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
////                    nameValuePairs.add(new BasicNameValuePair(ImageName, ConvertImage));
////                    nameValuePairs.add(new BasicNameValuePair(ImageTag, currentDateandTime));
////                    nameValuePairs.add(new BasicNameValuePair("name", name1));
////                    nameValuePairs.add(new BasicNameValuePair("email", email1));
////                    nameValuePairs.add(new BasicNameValuePair("address", address1));
////
//////
//////                HashMapParams.put(ImageName, ConvertImage);
//////                HashMapParams.put("Token", Token);
//////                HashMapParams.put("name", name1);
//////                HashMapParams.put("email", email1);
//////                HashMapParams.put("address", address1);
////                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
////
////                    // Execute HTTP Post Request
////                    HttpResponse response = httpclient.execute(httppost);
////
////                } catch (ClientProtocolException e) {
////                    // TODO Auto-generated catch block
////                } catch (IOException e) {
////                    // TODO Auto-generated catch block
////
////                }
////                HttpPost post = new HttpPost( "http://wwww.testserver.com/userAddMoney" );
////                post.addHeader( "Token" , T);
//            }
//
//
//
//
//
//        }
//        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
//        AsyncTaskUploadClassOBJ.execute();
//    }
private void uploadImageUsingRetrofit(){


    progressBar=findViewById(R.id.progress);
    progressBar.setVisibility(View.VISIBLE);


    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String   currentDateandTime = sdf.format(new Date());
    final String name = currentDateandTime;
    Map<String,String> headers=new HashMap<>();
    headers.put("Token",Token);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MyImageInterface.IMAGEURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    MyImageInterface myImageInterface = retrofit.create(MyImageInterface.class);
    Call<String> call = myImageInterface.getImageData(Token,ConvertImage,currentDateandTime,name1,email1,address1);
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, retrofit2.Response<String> response) {


            if (response.isSuccessful()) {
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    Intent i=new Intent(getApplicationContext(),my_profile.class);
                    startActivity(i);
                    Toast.makeText(Editprofilenew.this, "Image Uploaded Successfully!!", Toast.LENGTH_SHORT).show();

                } else {

                }
            }else{

                Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
            }
        }



        @Override
        public void onFailure(Call<String> call, Throwable t) {

            Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();

        }
    });
}
    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera

            }
            else {

              //  Toast.makeText(Editprofilenew.this, "Unable to use Camera..Please Allow us to use Camera", Toast.LENGTH_LONG).show();

            }
        }
    }
    private void view_details() {
        final String URL_MY_WISHLIST=PATH +"MyProfile?";

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

                    with.load(sb.toString()).placeholder(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).error(getApplication().getResources().getDrawable(R.drawable.ic_error_place_holder)).into(GetImageFromGalleryButton);
                   // Toast.makeText(getApplicationContext(),"jjj"+image,Toast.LENGTH_LONG).show();

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
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Token", Token);

                return params;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}
