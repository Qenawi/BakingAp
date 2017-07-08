package com.example.qenawi.bakingap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.qenawi.bakingap.fragments.RStepDetail;
import com.example.qenawi.bakingap.fragments.SelectRecipeDetail;
import com.example.qenawi.bakingap.items.RecipeItem;

public class DetailActivity extends AppCompatActivity implements SelectRecipeDetail.selectRecipeListener,RStepDetail.OnFragmentInteractionListener
{
   private RecipeItem recipeItem;
    private Boolean tView =false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState== null)
        {
        recipeItem =(RecipeItem)getIntent().getExtras().getSerializable(getString(R.string.bundleK1));
        try
        {this.setTitle(recipeItem.getName());
        }catch (Exception e){e.printStackTrace();}

        if (findViewById(R.id.frame2) != null)// Tablet or No-t
        {
            tView = true;
        }
        Call_DEtail_FRAG(recipeItem);
        if(tView)
        {
           Call_DEtail_FRAG2(recipeItem,0);
        }
        }
        else
        {
            recipeItem =(RecipeItem) savedInstanceState.getSerializable(getString(R.string.bundleK3));
            tView =savedInstanceState.getBoolean(getString(R.string.bundleK2));
        }

    }
    void Call_DEtail_FRAG(RecipeItem packge)
    {
        //
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.bundleK1), packge);
        //
        SelectRecipeDetail fragment = new SelectRecipeDetail();
        fragment.setArguments(bundle);//set data
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame1, fragment, SelectRecipeDetail.class.getSimpleName()); //Container -> R.id.contentFragment
        transaction.commit();
    }
    void Call_DEtail_FRAG2(RecipeItem packge,int pos)
    {
        //
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.bundleK1), packge);
        bundle.putInt(getString(R.string.bundleK2), pos);
        bundle.putBoolean(getString(R.string.bundleK4), tView);
        RStepDetail fragment = new RStepDetail();
        fragment.setArguments(bundle);//set data
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.frame2, fragment, RStepDetail.class.getSimpleName()); //Container -> R.id.contentFragment
        transaction.commit();
    }
    @Override
    public void onFragmentInteraction(Object uri,Object uri3)
    {
        if (!tView)  // not tap mode
        {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.bundleK1), recipeItem);
            bundle.putInt(getString(R.string.bundleK2), (Integer) uri3);
            bundle.putBoolean(getString(R.string.bundleK4), tView);
            Intent i = new Intent(this, StepDetailActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        else  // tab mode
        {
            Call_DEtail_FRAG2(recipeItem,(int)uri3);
        }
    }

    @Override
    public void onFragmentInteraction(Object uri)
    {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
    outState.putBoolean(getString(R.string.bundleK2), tView);
        outState.putSerializable(getString(R.string.bundleK3), recipeItem);
        super.onSaveInstanceState(outState);
    }
}
