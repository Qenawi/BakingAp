package com.example.qenawi.bakingap;


import android.content.Intent;
import android.os.Bundle;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.qenawi.bakingap.items.IngredientItem;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityTestRule = new ActivityTestRule<>(DetailActivity.class,true,false);
    @Test
    public void mainActivityTest()
    {

        ArrayList<StepItem>stepItems=new ArrayList<>();
        ArrayList<IngredientItem>ingredientItems=new ArrayList<>();
        stepItems.add(new StepItem("shrt","lng","link","thumbal"));
        ingredientItems.add(new IngredientItem(8,"xx","bal7"));
        Bundle bundle = new Bundle();
        bundle.putSerializable("AE7ECO0", new RecipeItem("bA",7,"ede",ingredientItems,stepItems));
        Intent i=new Intent();
        i.putExtras(bundle);
        mActivityTestRule.launchActivity(i);
        //---------------------------send data to intent----------------------------------------------------------------
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.item2text), isDisplayed()));
       ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recipe_list_detail), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
    }

}
