package com.example.qenawi.bakingap;


import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.qenawi.bakingap.items.IngredientItem;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {
    @Rule
    public ActivityTestRule<StepDetailActivity> mActivityTestRule = new ActivityTestRule<>(StepDetailActivity.class,true,false);
    private IdlingResource idlingResource;
    @Before
    public void Reg()
    {
        ArrayList<StepItem>stepItems=new ArrayList<>();
        ArrayList<IngredientItem>ingredientItems=new ArrayList<>();
        stepItems.add(new StepItem("shrt","lng","https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4","thumbal"));
        ingredientItems.add(new IngredientItem(8,"xx","bal7"));
        Bundle bundle = new Bundle();
        bundle.putSerializable("AE7ECO0", new RecipeItem("bA",7,"ede",ingredientItems,stepItems));
        bundle.putInt("AE7ECO1", 0);
        bundle.putBoolean("AE7ECO3",false);
        Intent i=new Intent();
        i.putExtras(bundle);
        mActivityTestRule.launchActivity(i);
        idlingResource=mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void mainActivityTest()
    {

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.exo_pause), withContentDescription("Pause"), isDisplayed()));
        appCompatImageButton.perform(click());
        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.exo_play), withContentDescription("Play"), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button2), withText("next"), isDisplayed()));
        appCompatButton.perform(click());
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("prev"), isDisplayed()));
        appCompatButton2.perform(click());

    }
    @After
   public void UnReg()
    {
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
