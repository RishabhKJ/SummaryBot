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

public class SelectTest {

    @Rule
    public ActivityTestRule<Profile> profileActivityTestRule = new ActivityTestRule<>(Profile.class);
    private Profile profile = null;

    @Before
    public void setUp() throws Exception {
        profile = profileActivityTestRule.getActivity();
    }

    @Test
    public void testselectfile() {
        View view = profile.findViewById(R.id.selectFile);
        assertNotNull(view);
    }

    @Test
    public void testtextbox1() {
        View view = profile.findViewById(R.id.notification);
        assertNotNull(view);
    }

    @Test
    public void testtextbox2() {
        View view = profile.findViewById(R.id.notification1);
        assertNotNull(view);
    }

    @Test
    public void testupload() {
        View view = profile.findViewById(R.id.upload);
        assertNotNull(view);
    }

    @Test
    public void testoutput() {
        View view = profile.findViewById(R.id.fileresult);
        assertNotNull(view);
    }
    @Test
    public void testpitchbar() {
        View view = profile.findViewById(R.id.seek_bar_pitcha);
        assertNotNull(view);
    }
    @Test
    public void testspeedbar() {
        View view = profile.findViewById(R.id.seek_bar_speeda);
        assertNotNull(view);
    }
    @Test
    public void testdownload() {
        View view = profile.findViewById(R.id.downloadfile);
        assertNotNull(view);
    }
    @Test
    public void testshare() {
        View view = profile.findViewById(R.id.sharesumpdf);
        assertNotNull(view);
    }

    @Test
    public void checkingGetSummaryButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.upload), withText(" Get   Summary "),
                        childAtPosition1(
                                childAtPosition1(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatButton.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition1(
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
    public void checkingSpeakButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_speaka),
                        childAtPosition2(
                                childAtPosition2(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                4)));
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
    public void checkingStopButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_stopa),
                        childAtPosition3(
                                childAtPosition3(
                                        withClassName(is("android.widget.LinearLayout")),
                                        5),
                                5)));
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

    @Test
    public void checkingDownloadButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.downloadfile),
                        childAtPosition4(
                                childAtPosition4(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
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
    public void checkingShareButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.sharesumpdf),
                        childAtPosition5(
                                childAtPosition5(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        appCompatImageView.perform(scrollTo(), click());
    }
    @Test
    public void checkingSelectFileButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.selectFile), withText("Select  FILE"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
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

    private static Matcher<View> childAtPosition5(
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
        profile = null;
    }
}