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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;

import app.eazyhomebrunei.com.Config.BaseURL;
import app.eazyhomebrunei.com.R;
import app.eazyhomebrunei.com.model.sub_cate;
import app.eazyhomebrunei.com.subincategoryproducts;

public class subincategoryadapter extends RecyclerView.Adapter<subincategoryadapter.viewHolder> {
    private List<sub_cate> productList;
    private subincategoryadapter.OnItemClickListener mListener;
    private Context mcontext;
    Dialog myDialog;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(subincategoryadapter.OnItemClickListener listener){
        mListener =listener;
    }


    public subincategoryadapter(Context mcontext,List<sub_cate> listitem) {
        this.productList = listitem;
        this.mcontext = mcontext;
        myDialog = new Dialog(mcontext);
    }

    @NonNull
    @Override
    public subincategoryadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcatagory_adapter,parent,false);
        mcontext=parent.getContext();
        return  new subincategoryadapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final subincategoryadapter.viewHolder holder, final int position) {
        final sub_cate product=productList.get(position);
        String imageurl=product.getImage();
        final int id=product.getSubcategoryid();
        String catagory=product.getCatagory();
        holder.catagory.setText(catagory);
//        final int subcount=product.getSubin_count();


        Picasso with = Picasso.with(mcontext);
        StringBuilder sb = new StringBuilder();
        sb.append(BaseURL.CATEGORY_PATH);
        sb.append(product.getImage());
        with.load(sb.toString()).placeholder(mcontext.getResources().getDrawable(R.drawable.dummy)).error(mcontext.getResources().getDrawable(R.drawable.dummy)).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//

                    Intent i = new Intent(mcontext, subincategoryproducts.class);
                    i.putExtra("id", product.getCatid());

                    i.putExtra("sub_cat", product.getSubcategoryid());
                    i.putExtra("subincat", product.getSubinCategoryId());
                i.putExtra("category", product.getCatagory());
                    mcontext. startActivity(i);
                }

//                    Intent i = new Intent(mcontext, subincategory.class);
//                    i.putExtra("id", id);
//                    i.putExtra("catagory", product.getCatagory());
//                    i.putExtra("image", product.getImage());
//                    i.putExtra("catid", product.getCatid());
//                    i.putExtra("pr_count", product.getSubin_count());
//                    mcontext.startActivity(i);
                    //i.putExtra("catagory", clickeditem.getCatagory());
//Toast.makeText(mcontext,""+product.getCatid(),Toast.LENGTH_LONG).show();


        });


//
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
            img=itemView.findViewById(R.id.image_view);
            catagory=itemView.findViewById(R.id.name);
//            lnr1=itemView.findViewById(R.id.list);
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