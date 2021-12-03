package app.eazyhomebrunei.com.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app.eazyhomebrunei.com.CategoryProducts;
import app.eazyhomebrunei.com.Config.BaseURL;


import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.Subcatagory;
import app.eazyhomebrunei.com.model.Product;

import com.squareup.picasso.Picasso;


import java.util.List;

public class catagory_adapter  extends RecyclerView.Adapter<catagory_adapter.viewHolder> {
    private List<Product> productList;
     private OnItemClickListener mListener;
    private Context mcontext;
    Dialog myDialog;
    int id;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }


    public catagory_adapter(Context mcontext,List<Product> listitem) {
        this.productList = listitem;
        this.mcontext = mcontext;
        myDialog = new Dialog(mcontext);
    }

    @NonNull
    @Override
    public catagory_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_adapter,parent,false);
      mcontext=parent.getContext();

        return  new catagory_adapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final catagory_adapter.viewHolder holder, int position) {
       final Product product=productList.get(position);
       String imageurl=product.getImage();
        id=product.getCategoryId();
        String catagory=product.getCatagory();
       holder.catagory.setText(catagory);
       final int sub_cata=product.getSub_count();

        Picasso with = Picasso.with(mcontext);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.CATEGORY_PATH);
        sb.append(product.getImage());
        with.load(sb.toString()).placeholder(mcontext.getResources().getDrawable(R.drawable.dummy)).error(mcontext.getResources().getDrawable(R.drawable.dummy)).into(holder.img);

         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
              //   Toast.makeText(mcontext,"s"+product.getSub_count(),Toast.LENGTH_LONG).show();
                 if (sub_cata <= 0) {
            Intent i = new Intent(mcontext, CategoryProducts.class);
            i.putExtra("id", product.getCategoryId());
            i.putExtra("catagory", product.getCatagory());
            i.putExtra("image", product.getImage());
            i.putExtra("sub_cat", sub_cata);
            i.putExtra("pr_count", product.getPr_count());
          mcontext. startActivity(i);
        //    i.putExtra("catagory", clickeditem.getCatagory());
Toast.makeText(mcontext,"No Catagory found",Toast.LENGTH_LONG).show();
        } else
        {

            Intent i = new Intent(mcontext, Subcatagory.class);


        i.putExtra("id", product.getCategoryId());
        i.putExtra("catagory", product.getCatagory());
        i.putExtra("image", product.getImage());
        i.putExtra("sub_cat", sub_cata);
        i.putExtra("pr_count", product.getPr_count());
        mcontext.startActivity(i);
    }

             }
         });



    }

    private void ShowPopup() {
        TextView txtclose;

        myDialog.setContentView(R.layout.product_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    public int getItemCount() {
        if(productList==null)

            return 0;

        return productList.size();

    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txttitle,catagory;
        TextView tv1;
        LinearLayout lnr1;
//       myDialog = new Dialog();
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.iv_product);
            catagory=itemView.findViewById(R.id.tv_name);
           // txttitle=itemView.findViewById(R.id.price);
//            tv1=itemView.findViewById(R.id.text1);
//            String text="10.30am | 1.30pm | 4.30pm | 9.00pm 10.30am | 1.30pm | 4.30pm | 9.00pm ";
//            tv1.setText(text);
//            tv1.setMovementMethod(new ScrollingMovementMethod());
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
