package app.eazyhomebrunei.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import app.eazyhomebrunei.com.R;


public class Custome_swipe_adapter extends PagerAdapter {
    private int[] image_res={R.drawable.hom,R.drawable.hom,R.drawable.hom};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public  Custome_swipe_adapter(Context ctx)
    {
        this.ctx=ctx;
    }

    @Override
    public int getCount() {
        return image_res.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view=layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView=(ImageView) item_view.findViewById(R.id.imageview);
        imageView.setImageResource(image_res[position]);
        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((LinearLayout)object);
    }
}
