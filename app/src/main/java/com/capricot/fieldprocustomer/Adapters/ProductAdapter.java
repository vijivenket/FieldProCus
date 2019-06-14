package com.capricot.fieldprocustomer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capricot.fieldprocustomer.Bean.Product;
import com.capricot.fieldprocustomer.R;

import java.util.List;

/**
 * Created by BalajiPrabhu on 9/14/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {


    private final List<Product> productList;
    private final Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView product;
        final TextView subCategory;
        final TextView serialNumber;
        final TextView modelNo;
        final TextView contractType;

        MyViewHolder(View itemView) {

            super(itemView);
            product = (TextView)itemView.findViewById(R.id.product_id);
            subCategory = (TextView)itemView.findViewById(R.id.sub_category_id);
            serialNumber = (TextView)itemView.findViewById(R.id.serial_number_id);
            modelNo = (TextView)itemView.findViewById(R.id.model_no_id);
            contractType = (TextView)itemView.findViewById(R.id.contract_type_id);
        }
    }


    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context= context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
        return new ProductAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Product product = productList.get(position);
        Log.e("test","test"+product.getProduct());
        holder.product.setText(product.getProduct());
        holder.subCategory.setText(product.getSub_category());
        holder.serialNumber.setText(product.getSerial_number());
        holder.modelNo.setText(product.getModel_no());
        holder.contractType.setText(product.getContract_type());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
