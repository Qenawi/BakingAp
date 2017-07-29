package com.example.qenawi.bakingap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.items.RecipeItem;

import java.util.ArrayList;

/**
 * Created by QEnawi on 3/11/2017.
 */

public class RecyclerViewAdapterMainActivity extends RecyclerView.Adapter<RecyclerViewAdapterMainActivity.MainVIewHOlder> {
   private Context context;
    private int rotate;
    private  onClickListner mOnClickListener;
    private  ArrayList<RecipeItem> recipeItemArrayList;
    public RecyclerViewAdapterMainActivity(Context C, onClickListner L, ArrayList<RecipeItem>D, int Rotate)
    {
        context=C;
        mOnClickListener=L;
        recipeItemArrayList =D;
        rotate=Rotate;
    }
    //basic Fn
    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context parent_C=parent.getContext();
       int Layoutidforitem= R.layout.rv_text_item;
        LayoutInflater inflater=LayoutInflater.from(parent_C);
        boolean shouldAttachToParentImmediately = false;
        View view=inflater.inflate(Layoutidforitem,parent,shouldAttachToParentImmediately);
       MainVIewHOlder hOlder=new MainVIewHOlder(view);
        return  hOlder;
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int position)
    {
        holder.bind(recipeItemArrayList.get(position),rotate);
    }

    @Override
    public int getItemCount()
    {
        if(recipeItemArrayList ==null){return  0;}
            return recipeItemArrayList.size();
    }

    @Override
    public long getItemId(int position)
    {
        return  position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    class MainVIewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView recipe_img;
        TextView recipe_name;
        public MainVIewHOlder(View itemView)
        {
            super(itemView);
            recipe_img = (ImageView)itemView.findViewById(R.id.imageView);
            recipe_name=(TextView)itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        void bind(  RecipeItem img_src,int rotate)
         {
////       TODO: rotate  glide
             recipe_name.setText(img_src.getName());
            if (!img_src.getImg_url().equals("")) Glide.with(context).load(img_src.getImg_url()).into(recipe_img);
        }

        @Override
        public void onClick(View view)
        {
            int Clickpos = getAdapterPosition();
            mOnClickListener.onListItemClick(Clickpos);
        }
    }
    public interface onClickListner
    {
        void onListItemClick(int Clickpos);
    }
}
