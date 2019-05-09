package com.example.gauthupload;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class recoveryTest {

    @Rule
    public ActivityTestRule<recovery> recoveryActivityTestRule = new ActivityTestRule<>(recovery.class);
    private recovery Recovery =null;

    @Before
    public void setUp() throws Exception {
        Recovery = recoveryActivityTestRule.getActivity();
    }
    @Test
    public void testEmail()
    {
        View view = Recovery.findViewById(R.id.email1);
        assertNotNull(view);
    }
    @Test
    public void testRecovery()
    {
        View view = Recovery.findViewById(R.id.reset1);
        assertNotNull(view);
    }

    @Test
    public void CheckResetButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.reset1), withText("Reset Password"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatButton.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
    @After
    public void tearDown() throws Exception {
        Recovery = null;
    }
}