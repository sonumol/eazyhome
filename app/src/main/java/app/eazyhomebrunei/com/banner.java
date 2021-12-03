package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import app.eazyhomebrunei.com.adapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static app.eazyhomebrunei.com.Config.BaseURL.PATH;

public class banner extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout sliderdots;
    private  int dotcount;
    private ImageView[] dots;
    RequestQueue rq;
    List<SliderUtils> sliderimg;
    ViewPagerAdapter viewPagerAdapter;
    String request_url="Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
      rq=Volley.newRequestQueue(this);
       sliderimg=new ArrayList<>();
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        sliderdots=(LinearLayout)findViewById(R.id.sliderdots);
        Send_req();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for(int i=0;i<dotcount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dots));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(),2000,4000);

    }
    public  class  myTimerTask extends TimerTask{

        @Override
        public void run() {
            banner.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()==0)
                    {
                        viewPager.setCurrentItem(1);
                    }
                    else  if(viewPager.getCurrentItem()==1)
                    {
                        viewPager.setCurrentItem(2)
                        ;
                    }
                    else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
    private void Send_req() {
        final String URL_SUBCATAGORY=PATH +"Home";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_SUBCATAGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),"jjj"+response,Toast.LENGTH_LONG).show();
                try {


//
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jk=jsonObject.getJSONObject("data");
                    JSONArray ar=jk.getJSONArray("banr");



                    for (int i = 0; i <ar.length(); i++)
                    {
                        SliderUtils sliderUtils=new SliderUtils();

                        JSONObject productobject = ar.getJSONObject(i);
                        sliderUtils.setSliderImageUrl(productobject.getString("image"));

                        sliderimg.add(sliderUtils);


                    }
                 viewPagerAdapter=new ViewPagerAdapter(sliderimg,banner.this);
                    viewPager.setAdapter(viewPagerAdapter);
                    dotcount=viewPagerAdapter.getCount();
                    dots=new ImageView[dotcount];
                    for (int i=0;i<dotcount;i++)
                    {
                        dots[i]=new ImageView(banner.this);
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.inactive_dots));
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(8,0,8,0);
                        sliderdots.addView(dots[i],params);
                    }
                    dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

//
//
//
////                    }
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
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();



                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


    }
}
