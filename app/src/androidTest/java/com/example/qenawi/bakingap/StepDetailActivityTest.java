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
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
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
        stepItems.add(new StepItem("shrt","lng","link","thumbal"));
        ingredientItems.add(new IngredientItem(8,"xx","bal7"));
        Bundle bundle = new Bundle();
        bundle.putSerializable("bit3", new RecipeItem("bA",7,"ede",ingredientItems,stepItems));
        bundle.putInt("bit4", 0);
        bundle.putBoolean("bit74",false);
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
        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.exo_pause), withContentDescription("Pause"), isDisplayed()));
        appCompatImageButton3.perform(click());
        ViewInteraction appCompatImageButton4 = onView(
                allOf(withId(R.id.exo_play), withContentDescription("Play"), isDisplayed()));
        appCompatImageButton4.perform(click());
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button2), withText("Next"),
                        withParent(withId(R.id.fragment2)),
                        isDisplayed()));
        appCompatButton.perform(click());
        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button), withText("Prev"),
                        withParent(withId(R.id.fragment2)),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }
    @After
   public void UnReg()
    {
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
