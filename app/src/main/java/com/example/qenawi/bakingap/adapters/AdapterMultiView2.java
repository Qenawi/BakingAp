package com.example.qenawi.bakingap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.genericviewholders.GenericViewHolder;
import com.example.qenawi.bakingap.items.StepItem;

/**
 * Created by QEnawi on 3/18/2017.
 */

public class AdapterMultiView2 extends RecyclerView.Adapter<GenericViewHolder>
{

   private StepItem data2;
    private  Context C;
    private   Click_listnerx mCallback;
    public AdapterMultiView2(StepItem data3, Context c,Click_listnerx click_listnerx)
    {
        this.data2 = data3;
        C = c;
        this.mCallback=click_listnerx;
    }
    public void changeDataSet(StepItem data2)
    {
        this.data2=data2;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position)
    {
       return position;
    }
    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_txt_sp, parent, false);
        return new Viewholder2(view);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position)
    {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (1);
    }
    private class  Viewholder2 extends GenericViewHolder implements View.OnClickListener
    {
        TextView textView;
        Button nxt,prev;
        public Viewholder2(View itemView)
        {
            super(itemView);

            textView=(TextView)itemView.findViewById(R.id.item2text3);
            nxt=(Button) itemView.findViewById(R.id.button2);
            prev=(Button) itemView.findViewById(R.id.button);
            nxt.setOnClickListener(this);
            prev.setOnClickListener(this);

        }

        @Override
        public void bind(int position)
        {
          textView.setText(data2.getDescription());
        }

        @Override
        public void onClick(View view)
        {
          if (view.getId()==nxt.getId())
              mCallback.OnClickList(0);
            else
                mCallback.OnClickList(1);
        }
    }
public  interface  Click_listnerx
{
    void OnClickList(int c);
}
}
