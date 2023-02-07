package com.example.navigation_bar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class example_adapter extends RecyclerView.Adapter<example_adapter.ExampleViewHolder> {
    public ArrayList<example_item>mexamplelist;
    private OnItemClickListener mListener;
    public interface OnItemClickListener
    {
        void OnItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }
    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mtextview1;
        public TextView mtextview2;
        public TableLayout mtablelayout;
        public ImageView mdeleteimage;
        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mtextview1 = itemView.findViewById(R.id.textView);
            mtextview2 = itemView.findViewById(R.id.textView2);
            mtablelayout = itemView.findViewById(R.id.table_in_cardview);
            mdeleteimage = itemView.findViewById(R.id.image_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            mdeleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public example_adapter(ArrayList<example_item> examplelist)
    {
        mexamplelist = examplelist;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_cardview,parent,false); //cardview athva R.id.first_table athva R.id.sctt
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position)
    {
        example_item currentItem = mexamplelist.get(position);
        holder.mtextview1.setText(currentItem.getMtext1());
        holder.mtextview2.setText(currentItem.getMtext2());
 //       holder.mtablelayout = currentItem.getMtable();
    }

    @Override
    public int getItemCount() {
        return mexamplelist.size();
    }
}
