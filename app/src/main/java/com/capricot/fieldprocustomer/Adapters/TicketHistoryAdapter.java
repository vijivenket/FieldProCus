package com.capricot.fieldprocustomer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capricot.fieldprocustomer.Bean.Ticket;
import com.capricot.fieldprocustomer.R;

import java.util.ArrayList;

/**
 * Created by BalajiPrabhu on 9/17/2017.
 */

public class TicketHistoryAdapter extends RecyclerView.Adapter<TicketHistoryAdapter.MyViewHolder> {



    private final ArrayList<Ticket> ticketList;
    private final Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {


        final TextView ticketID;
        final TextView techname;
        final TextView mobNum;
        final TextView productName;
        final TextView categoryName;


        MyViewHolder(View itemView) {

            super(itemView);

            ticketID = (TextView)itemView.findViewById(R.id.ticket_id);
            techname = (TextView)itemView.findViewById(R.id.tech_name_id);
            mobNum = (TextView)itemView.findViewById(R.id.mob_id);
            productName = (TextView)itemView.findViewById(R.id.product_name_id);
            categoryName = (TextView)itemView.findViewById(R.id.catName);

        }
    }

    public TicketHistoryAdapter(ArrayList<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context= context;
    }

    @Override
    public TicketHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_item, viewGroup, false);
        return new TicketHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TicketHistoryAdapter.MyViewHolder myViewHolder, int position) {

        final Ticket ticket = ticketList.get(position);
        Log.e("test","test"+ticket.getTicketId());
        myViewHolder.ticketID.setText(ticket.getTicketId());
        myViewHolder.techname.setText(ticket.getTechName());
        myViewHolder.mobNum.setText(ticket.getContactNo());
        myViewHolder.productName.setText(ticket.getProductName());
        myViewHolder.categoryName.setText(ticket.getCatName());

    }

    @Override
    public int getItemCount() {

        return ticketList.size();
    }

}
