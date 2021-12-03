package app.eazyhomebrunei.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import app.eazyhomebrunei.com.Config.BaseURL;
import app.eazyhomebrunei.com.CustomVolleyRequest;

import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.SliderUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> slideimg;
    private ImageLoader imageLoader;
    //private Integer [] images={R.drawable.dummy,R.drawable.b3,R.drawable.b2};
    private ProgressBar progress,progress1;

    public ViewPagerAdapter(List<SliderUtils> sliderimg,Context context) {
        this.slideimg=sliderimg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return slideimg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
         layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View view=layoutInflater.inflate(R.layout.custom_layout,null);
        SliderUtils utils= slideimg.get(position);




        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);

        imageLoader= CustomVolleyRequest.getInstance(context).getImageLoader();
     //   imageLoader.get(utils.getSliderImageUrl(),ImageLoader.getImageListener(imageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        Picasso with = Picasso.with(context);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.BANNER);
        sb.append(utils.getSliderImageUrl());
        with.load(sb.toString()).placeholder(context.getResources().getDrawable(R.drawable.ic_error_place_holder)).error(context.getResources()
                .getDrawable(R.drawable.ic_error_place_holder)).
                into(imageView);

        ViewPager vp=(ViewPager) container;
        vp.addView(view,0);
        return  view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager vp=(ViewPager)container;
        View view=(View)object;
        vp.removeView(view);
    }
}
