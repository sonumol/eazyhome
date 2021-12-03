package app.eazyhomebrunei.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import app.eazyhomebrunei.com.adapter.catagory_adapter;
import app.eazyhomebrunei.com.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Catagories extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView1, recyclerView2;
    private RecyclerView.Adapter adapter;
    private List<Product> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recy1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        listItems = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            Product listitem1 = new Product(
//                    "heading",
//                    "hcggcvcggc",
//                    "");
//
//
//            listItems.add(listitem1);
//        }
        adapter = new catagory_adapter(this, listItems);
        recyclerView.setAdapter(adapter);
//        recyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),product_details.class);
//                startActivity(i);
//            }
//        });
    }


}
