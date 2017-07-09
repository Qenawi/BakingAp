package com.example.qenawi.bakingap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.example.qenawi.bakingap.fragments.SelectARecipe;
import com.example.qenawi.bakingap.idlelingResource.SimpleIdlingResource;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.widget.BakingAppWidgetProvider;

public class MainActivity extends AppCompatActivity implements SelectARecipe.OnFragmentInteractionSelectRecipe
{
    @Nullable
    private SimpleIdlingResource simpleIdlingResource;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onActionSelectRecipe(Object uri)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.bundleK1), (RecipeItem)uri);
        Intent i=new Intent(this,DetailActivity.class);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public void onAc()
    {
        if(simpleIdlingResource!=null)
            simpleIdlingResource.setIdleState(true);
    }
    @Override
    protected void onDestroy()
    {
        UpdateWedgie();
        super.onDestroy();
    }

    void UpdateWedgie( )
    {

        Intent i = new Intent(this , BakingAppWidgetProvider.class);
        i.setAction(getString(R.string.Action0));
       sendBroadcast(i);
    }
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource()
    {
        if (simpleIdlingResource == null)
        {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;
    }
}
