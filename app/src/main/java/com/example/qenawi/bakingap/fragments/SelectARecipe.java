package com.example.qenawi.bakingap.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.bakingap.provider.Rcontract;
import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.adapters.RecyclerViewAdapterMainActivity;
import com.example.qenawi.bakingap.items.IngredientItem;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;
import com.example.qenawi.bakingap.widget.BakingAppWidgetProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelectARecipe extends Fragment implements RecyclerViewAdapterMainActivity.onClickListner
{

    private RecyclerViewAdapterMainActivity adapter;
    private RecyclerView rv;
    private OnFragmentInteractionSelectRecipe mListener;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecipeItem>recipeItems;
    private String Json;
    public SelectARecipe()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Json=loadJSONFromAsset();
        recipeItems=new ArrayList<>();
        try
        {
            getData(Json);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
       //recipeItems  read it from bundle
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_select_arecipe, container, false);
        rv=(RecyclerView) root.findViewById(R.id.recipe_list);
        layoutManager=new GridLayoutManager(getContext(),2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterMainActivity(getContext(), this, recipeItems, 2);
        rv.setAdapter(adapter);
        return  root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onActioon(Object uri)
    {
        if (mListener != null)
        {
            mListener.onActionSelectRecipe(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionSelectRecipe) {
            mListener = (OnFragmentInteractionSelectRecipe) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionSelectRecipe");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int Clickpos)
    {
        clean_add(Clickpos);
        mListener.onActionSelectRecipe(recipeItems.get(Clickpos));
    }

    public interface OnFragmentInteractionSelectRecipe
    {
        void onActionSelectRecipe(Object uri);
    }
    private String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = getActivity().getAssets().open("jsonCock.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return  json;
    }
    private  void getData(String json) throws JSONException
    {
        ArrayList<IngredientItem>ingredientItems;
        ArrayList<StepItem>stepItems;

        JSONArray arr,ingred,steps;
        JSONObject o,oi,os;
        RecipeItem f;
        IngredientItem ingredientItem;
        StepItem stepItem;
        arr = new JSONArray(json);
        for(int i=0;i<arr.length();i++) //recipi
        {
            o=arr.getJSONObject(i);
            f=new RecipeItem();
            f.setName(o.getString("name"));
            ingred=o.getJSONArray("ingredients");
            steps=o.getJSONArray("steps");
            ingredientItems=new ArrayList<>();
            stepItems=new ArrayList<>();
            for(int ii=0;ii<ingred.length();ii++)
            {
                oi=ingred.getJSONObject(ii);
                ingredientItem= new IngredientItem();
                ingredientItem.setMeasure(oi.getString("measure"));
                ingredientItem.setQuantity(oi.getInt("quantity"));
                ingredientItem.setIngredient(oi.getString("ingredient"));
                ingredientItems.add(ingredientItem);
            }
            for (int iii=0;iii<steps.length();iii++)
            {
                os=steps.getJSONObject(iii);
                stepItem=new StepItem();
                stepItem.setShortDescription(os.getString("shortDescription"));
                stepItem.setDescription(os.getString("description"));
                stepItem.setVideoURL(os.getString("videoURL"));
                stepItem.setThumbnailURL(os.getString("thumbnailURL"));
                stepItems.add(stepItem);
            }
            f.setServings(o.getInt("servings"));
            f.setImg_url(o.getString("image"));
            f.setIngredientItems(ingredientItems);
            f.setStepItems(stepItems);
            recipeItems.add(f);
        }
    //    adapter.notifyDataSetChanged();
    }
    void UpdateWedgit( )
    {

        Intent i = new Intent(getContext(), BakingAppWidgetProvider.class);
        i.setAction("UPDATE_ACTION");
        getActivity().sendBroadcast(i);
    }
    void clean_add(int pos)
    {
    //    get();
        Log.v("Contra","clean_add");
        getActivity().getContentResolver().delete(Rcontract.CONTENT_URI, "ecoo", null);
     //   get();
        add_to_dp(pos);
        get();
    }
    void add_to_dp(int pos)
    {
        addH(recipeItems.get(pos).getName());
for(int i=0;i<recipeItems.get(pos).getIngredientItems().size();i++)
{
    add(recipeItems.get(pos).getIngredientItems().get(i),recipeItems.get(pos).getName());
}
    }
    public  void add(IngredientItem x,String Name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Rcontract.INGREDIENT_NAME,x.getIngredient()+" "+x.getQuantity()+" "+x.getMeasure());
        contentValues.put(Rcontract.RECIPE_NAME,Name);
        contentValues.put(Rcontract.RECIPE_TAG,"ecoo");
        getActivity().getContentResolver().insert(Rcontract.CONTENT_URI, contentValues);
    }
    public  void addH(String Name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Rcontract.INGREDIENT_NAME,Name+'\n'+"INGREDIENTS:");
        contentValues.put(Rcontract.RECIPE_NAME,Name);
        contentValues.put(Rcontract.RECIPE_TAG,"ecoo");
        getActivity().getContentResolver().insert(Rcontract.CONTENT_URI, contentValues);
    }

    public  void get()
    {
        Log.v("Contra","geT-><>");
        Cursor result=getActivity().getContentResolver().query(Rcontract.CONTENT_URI,null,null,null,Rcontract.B_id);
        if (result!=null)
        {
            result.moveToFirst();
        }
        while (!result.isAfterLast())
        {
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.INGREDIENT_NAME)));
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.RECIPE_NAME)));
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.RECIPE_TAG)));
            result.moveToNext();
        }

    }
}
