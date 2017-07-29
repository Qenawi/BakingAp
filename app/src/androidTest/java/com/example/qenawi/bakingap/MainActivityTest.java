package com.example.qenawi.bakingap;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;
    @Before
    public void Reg()
    {
        idlingResource=mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void mainActivityTest()
    {
        ViewInteraction recyclerView = onView
                (
                allOf(withId(R.id.recipe_list),
                        withParent(withId(R.id.fragment)),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(2, click()));
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.item2text), isDisplayed()));
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recipe_list_detail), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(10, click()));
    }
    public void UnReg()
    {
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
