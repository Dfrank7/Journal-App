package com.example.dfrank.journalapp;


import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AddJournalTesting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void clickFabButtonAndTestAddJournalActivity() {
        //The fab should move to
        onView(withId( R.id.fab)).perform(ViewActions.click());

        onView(withId(R.id.journal_title)).check(matches(isDisplayed())); // if it's displayed, We are in the AddTaskActivity

        //if we reach here, test is successful
    }

}
