
package com.example.qenawi.bakingap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.example.qenawi.bakingap.fragments.RStepDetail;
import com.example.qenawi.bakingap.idlelingResource.SimpleIdlingResource;
//https://stackoverflow.com/questions/41848293/google-exoplayer-guide-for-beginners
public class StepDetailActivity extends AppCompatActivity implements RStepDetail.OnFragmentInteractionListener
{
@Nullable private SimpleIdlingResource simpleIdlingResource;
    @Override
    protected void onCreate( Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_play_activity);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Object uri)
    {
        // set ideling source state
        if(simpleIdlingResource!=null)
        simpleIdlingResource.setIdleState((boolean)uri);
    }
    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
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