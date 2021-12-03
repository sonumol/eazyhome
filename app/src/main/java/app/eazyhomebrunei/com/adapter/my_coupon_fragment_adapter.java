package app.eazyhomebrunei.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.model.coupon_model;

import java.util.List;

public class my_coupon_fragment_adapter extends RecyclerView.Adapter<my_coupon_fragment_adapter.viewHolder> {
    private List<coupon_model> listitem;

    private Context mcontext;

    public my_coupon_fragment_adapter( Context mcontext,List<coupon_model> listitem) {
        this.listitem = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public my_coupon_fragment_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_coupon_fragment_adapter,parent,false);
        mcontext=parent.getContext();

        return  new my_coupon_fragment_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final my_coupon_fragment_adapter.viewHolder holder, int position) {
        final coupon_model itemlist=listitem.get(position);
        String reference = itemlist.getReference();
        String descr=itemlist.getTitle();
        String enddate=itemlist.getEndDate();
        String endtime=itemlist.getEndTime();

        holder.reference.setText(reference);
        holder.desc.setText(descr);

        holder.enddate.setText(enddate);

        holder.endtime.setText(endtime);




//
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView reference,desc,enddate,endtime;
        TextView tv1;
        LinearLayout lnr1;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            reference=itemView.findViewById(R.id.reference);
            desc=itemView.findViewById(R.id.desc);
            enddate=itemView.findViewById(R.id.enddate);
            endtime=itemView.findViewById(R.id.endtime);
//            String text="10.30am | 1.30pm | 4.30pm | 9.00pm 10.30am | 1.30pm | 4.30pm | 9.00pm ";
//            tv1.setText(text);
//            tv1.setMovementMethod(new ScrollingMovementMethod());
        }

    }
}
