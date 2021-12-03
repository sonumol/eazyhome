package app.eazyhomebrunei.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.eazyhomebrunei.com.Config.BaseURL;

import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.model.all_products;

import com.squareup.picasso.Picasso;

import java.util.List;

public class offerproducts_details_adapter extends RecyclerView.Adapter<offerproducts_details_adapter.viewHolder> {
    private List<all_products> prolistItems;
    private OnItemClickListener mListener;

    private Context mcontext;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }

    public offerproducts_details_adapter(Context mcontext,List<all_products> listitem) {
        this.prolistItems = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public offerproducts_details_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.offerproducts_details_adapter,parent,false);
        mcontext=parent.getContext();

        return  new offerproducts_details_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final offerproducts_details_adapter.viewHolder holder, int position) {
        final all_products itemlist=prolistItems.get(position);

      //  String short_disc=itemlist.getShort_description();
        String price=itemlist.getPrice();

        String offerprice=itemlist.getOffer_price();
        String image=itemlist.getImage();

        String pname=itemlist.getPname();
        holder.catagory.setText(pname);
//        holder.price.setText(price);
//        holder.offer_price.setText(offerprice);
        if(offerprice.equals("0.00"))
        {
            holder.price.setText("BND."+price);
            holder.price.setTextColor(Color.BLACK);
        }
        else
        {
            holder.price.setText("BND."+price);
            holder.offer_price.setText("BND."+offerprice);
            holder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        Picasso with = Picasso.with(mcontext);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.PRODUCT_IMG_PATH);
        sb.append(itemlist.getImage());
        with.load(sb.toString()).placeholder(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).error(mcontext.getResources().getDrawable(R.drawable.ic_error_place_holder)).into(holder.img);


//
    }

    @Override
    public int getItemCount() {
        if(prolistItems==null)

            return 0;

        return prolistItems.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {
        TextView price,offer_price,catagory;
        TextView tv1;
        LinearLayout lnr1;
        //       myDialog = new Dialog();
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.image_view);
            catagory=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
//            price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            offer_price=itemView.findViewById(R.id.offer_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }

                    }

                }
            });
        }

    }
}
