package com.example.qenawi.bakingap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.genericviewholders.GenericViewHolder;
import com.example.qenawi.bakingap.items.IngredientItem;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;

import java.util.ArrayList;

/**
 * Created by QEnawi on 3/18/2017.
 */

public class AdapterMultiView extends RecyclerView.Adapter<GenericViewHolder>
{
    public AdapterMultiView()
    {
    }
  private   ArrayList<IngredientItem> data1;
    private  ArrayList<StepItem> data2;
    private  Context C;
    private String Na;
    private   onClickListner mOnClickListener;

    public AdapterMultiView( RecipeItem data3,onClickListner L, Context c)
    {
        this.data1 = data3.getIngredientItems();
        this.data2 = data3.getStepItems();
        mOnClickListener=L;
        C = c;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0)
        {
            return 0;
        }
            return 1;
    }
    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_txt, parent, false);
        if (viewType == 0)
        {return new Viewholder1(view);
        } else if (viewType == 1)
        {return new Viewholder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (data2.size()+1);
    }
    private class Viewholder1 extends GenericViewHolder
    {
        View x;
        TextView e;
        private Viewholder1(View itemView) {
            super(itemView);
            x = itemView;
            e = (TextView) x.findViewById(R.id.item2text);
        }

        @Override
        public void bind(int position)
        {
            e.setText("");
            e.append("ingrediant : ");
            e.append("\n");
            for(int i=0;i<data1.size();i++)
            {
                e.append(data1.get(i).getIngredient());
                e.append("\n");
                e.append(data1.get(i).getMeasure());
                e.append("\t\t\t");
                e.append(data1.get(i).getQuantity()+"\n");

            }

            e.append("\n\n Steps: ");
        }
    }
    private class Viewholder2 extends GenericViewHolder implements View.OnClickListener {
        View x;
        TextView e;

        private Viewholder2(View itemView)
        {
            super(itemView);
            x = itemView;
            e = (TextView) x.findViewById(R.id.item2text);
            x.setOnClickListener(this);
        }
        @Override
        public void bind(int position)
        {
            position-=1;
          e.setText(data2.get(position).getShortDescription());

        }
        @Override
        public void onClick(View view)
        {
            mOnClickListener.onListItemClick2(data2.get(getAdapterPosition()-1),getAdapterPosition()-1);
        }
    }

    public interface onClickListner
    {
        void onListItemClick2(Object Clickpos,Object pos);
    }
}
