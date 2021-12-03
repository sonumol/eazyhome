package app.eazyhomebrunei.com.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.model.checkout_pro;

import java.util.List;

public class check_out_adapter extends RecyclerView.Adapter<check_out_adapter.MyViewHolder> {
    private List<checkout_pro> listitem;

    private Context mcontext;
    Dialog myDialog;



    public check_out_adapter( Context mcontext,List<checkout_pro> listitem) {
        this.listitem = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.check_out_adapter,parent,false);
      MyViewHolder viewHolder=new MyViewHolder(v);




        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final checkout_pro itemlist=listitem.get(position);
        String name=itemlist.getPname();
        int price=itemlist.getPrice();
        int count=itemlist.getQuantity();
        int total=itemlist.getTotal_price();
        holder.total.setText("BND."+total+".00");
        holder.count.setText("BND."+count+"X"+price+".00");
        holder.name.setText(""+name);

//
    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,count,total;
        TextView tv1;
        LinearLayout lnr1;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.pname);
            count=itemView.findViewById(R.id.quantity);
            total=itemView.findViewById(R.id.total);
//            tv1=itemView.findViewById(R.id.text1);
//            String text="10.30am | 1.30pm | 4.30pm | 9.00pm 10.30am | 1.30pm | 4.30pm | 9.00pm ";
//            tv1.setText(text);
//            tv1.setMovementMethod(new ScrollingMovementMethod());


        }

    }
}
