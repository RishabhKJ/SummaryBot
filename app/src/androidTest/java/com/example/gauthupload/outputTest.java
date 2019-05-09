package com.example.gauthupload;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

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
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class outputTest {

    @Rule
    public ActivityTestRule<output> outputActivityTestRule = new ActivityTestRule<>(output.class);
    private output Output = null;

    @Before
    public void setUp() throws Exception {
        Output = outputActivityTestRule.getActivity();
    }

    @Test
    public void testoutput() {
        View view = Output.findViewById(R.id.opbox);
        assertNotNull(view);
    }
    @Test
    public void testpitchbar() {
        View view = Output.findViewById(R.id.seek_bar_pitch);
        assertNotNull(view);
    }
    @Test
    public void testspeedbar() {
        View view = Output.findViewById(R.id.seek_bar_speed);
        assertNotNull(view);
    }
    @Test
    public void testspeak() {
        View view = Output.findViewById(R.id.button_speak);
        assertNotNull(view);
    }
    @Test
    public void teststop() {
        View view = Output.findViewById(R.id.button_stop);
        assertNotNull(view);
    }
    @Test
    public void testdownload() {
        View view = Output.findViewById(R.id.conpdf);
        assertNotNull(view);
    }
    @Test
    public void testshare() {
        View view = Output.findViewById(R.id.share);
        assertNotNull(view);
    }
    @Test
    public void checkingSpeakButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_speak),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                4)));
        appCompatImageView.perform(scrollTo(), click());
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
    @Test
    public void checkingStopButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_stop),
                        childAtPosition2(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                5)));
        appCompatImageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition2(
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

    @Test
    public void checkingShareButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.share),
                        childAtPosition4(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        appCompatImageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition4(
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
    @Test
    public void checkingDownloadButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.conpdf),
                        childAtPosition3(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatImageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition3(
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
}

