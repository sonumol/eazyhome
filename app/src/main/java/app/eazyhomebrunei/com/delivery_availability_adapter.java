package app.eazyhomebrunei.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import app.eazyhomebrunei.com.model.delivery;

public class delivery_availability_adapter extends RecyclerView.Adapter<delivery_availability_adapter.viewHolder> {
    private List<delivery> fdproductlist;

    private Context mcontext;
    String imageurl;
    String lid;
    int wishId;

    public delivery_availability_adapter(Context mcontext,List<delivery> listitem) {
        this.fdproductlist = listitem;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public delivery_availability_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_availability_adapter,parent,false);
        mcontext=parent.getContext();
        // Toast.makeText(v.getContext(), "imagee"+imageurl, Toast.LENGTH_SHORT).show();

        return  new delivery_availability_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final delivery_availability_adapter.viewHolder holder, final int position) {

        final delivery product=fdproductlist.get(position);
     String   name=product.getName();

        holder.pname.setText(name);


    }

    @Override
    public int getItemCount() {
        if(fdproductlist==null)

            return 0;

        return fdproductlist.size();
    }

    public void filterList(ArrayList<delivery> filteredList){
        fdproductlist=filteredList;
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView pname,catagory,price,offer_price;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            pname=itemView.findViewById(R.id.pname);

        }

    }
}
