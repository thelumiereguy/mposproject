package com.example.welcome.a3;

import android.content.Context;
import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

//    ArrayList<String> items;
//
//
//    public MainAdapter(ArrayList<String> items) {
//        this.items = items;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkoutitem,viewGroup,false );
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, int i) {
//
//        viewHolder.tv1.setText(items.get(i));
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView tv1;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tv1=itemView.findViewById(R.id.name);
//
//
//        }
//    }

    private List<ListItems> listItems2;
    private Context context;
    int qty=1;


    public MainAdapter(List<ListItems> listItems, Context context) {
        this.listItems2 = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext())
               .inflate(R.layout.checkoutitem,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
         ListItems listItems1=listItems2.get(i);

         viewHolder.tv1.setText(listItems1.getsku());
         viewHolder.tv2.setText(listItems1.getname());
         viewHolder.tv3.setText(listItems1.getmrp());
         viewHolder.itemtotal.setText(listItems1.getmrp());
    }

    @Override
    public int getItemCount() {
        return listItems2.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView itemtotal;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.SKU);
            tv2=itemView.findViewById(R.id.name);
            tv3=itemView.findViewById(R.id.MRP);
            itemtotal=itemView.findViewById(R.id.itemtotal);
        }
    }

}
