package com.example.qenawi.bakingap.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.adapters.AdapterMultiView;
import com.example.qenawi.bakingap.items.RecipeItem;
public class SelectRecipeDetail extends Fragment implements AdapterMultiView.onClickListner
{

    private AdapterMultiView adapter;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private RecipeItem recipeItem;
    private selectRecipeListener mListener;
    private int lastFirstVisiblePosition=0;
    public SelectRecipeDetail()
    {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null)
        {
            recipeItem=new RecipeItem();
            recipeItem=(RecipeItem)  getArguments().getSerializable(getActivity().getString(R.string.bundleK1));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_select_recipe_detail, container, false);
        if (savedInstanceState!= null)
        {
            lastFirstVisiblePosition=savedInstanceState.getInt(getString(R.string.bundleK4));
        }
        recyclerView =(RecyclerView) root.findViewById(R.id.recipe_list_detail);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterMultiView(recipeItem,this,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
        return  root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri,uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof selectRecipeListener) {
            mListener = (selectRecipeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + getActivity().getString(R.string.RESPONSE0));
        }
    }
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onListItemClick2(Object Clickpos,Object Clickposint)
    {
    mListener.onFragmentInteraction(Clickpos,Clickposint);
    }
    @Override
    public void onPause()
    {
        lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        super.onPause();
    }
    public interface selectRecipeListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object uri,Object uri3);
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(getString(R.string.bundleK4),lastFirstVisiblePosition);
        //outState.putSerializable(getString(R.string.bundleK1),recipeItem);
        super.onSaveInstanceState(outState);
    }

}
