package com.adityarajput.bustrackwar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class my_passenger_adapter  extends RecyclerView.Adapter<my_passenger_adapter.MyViewHolder> {

    Context context;
    ArrayList<passenger_bus_details> list;

    public my_passenger_adapter(Context context, ArrayList<passenger_bus_details> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.bus_details_recycle,parent,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        passenger_bus_details passenger_bus_details = list.get(position);
        String id = passenger_bus_details.getId();
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.v.getContext();
                Intent intent = new Intent(context,map_show_bus.class);
                intent.putExtra("id",id);
                context.startActivity(intent);



            }
        });
        holder.busno.setText(passenger_bus_details.getBusNumber());
        holder.vehicleno.setText((passenger_bus_details.getUniqueNumber()));
        holder.noofseat.setText(passenger_bus_details.getNoonSeat());
        holder.drivername.setText(passenger_bus_details.getDrivername());
        holder.alertmessage.setText(passenger_bus_details.getMessage());
        holder.remainingseat.setText(passenger_bus_details.getRemainseat());
        holder.startloc.setText(passenger_bus_details.getStartinglocation());
        holder.endloc.setText(passenger_bus_details.getEndinglocation());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView busno, vehicleno, noofseat, drivername, alertmessage, remainingseat,startloc,endloc;
        View v = itemView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            busno = itemView.findViewById(R.id.textView27);
            vehicleno = itemView.findViewById(R.id.textView29);
            noofseat = itemView.findViewById(R.id.textView31);
            drivername = itemView.findViewById(R.id.textView33);
            alertmessage = itemView.findViewById(R.id.textView35);
            remainingseat = itemView.findViewById(R.id.textView37);
            startloc = itemView.findViewById(R.id.textView39);
            endloc = itemView.findViewById(R.id.textView42);


        }
    }


}
