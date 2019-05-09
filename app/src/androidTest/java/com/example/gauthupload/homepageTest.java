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

public class homepageTest {

    @Rule
    public ActivityTestRule<homepage> homepageActivityTestRule = new ActivityTestRule<>(homepage.class);
    private homepage Homepage = null;

    @Before
    public void setUp() throws Exception {
        Homepage = homepageActivityTestRule.getActivity();
    }

    @Test
    public void testfile() {
        View view = Homepage.findViewById(R.id.file);
        assertNotNull(view);
    }

    @Test
    public void testcopy() {
        View view = Homepage.findViewById(R.id.copy);
        assertNotNull(view);
    }

    @Test
    public void testhistory() {
        View view = Homepage.findViewById(R.id.historya);
        assertNotNull(view);
    }

    @Test
    public void testvoice() {
        View view = Homepage.findViewById(R.id.voice);
        assertNotNull(view);
    }

    @Test
    public void testsignout() {
        View view = Homepage.findViewById(R.id.signout);
        assertNotNull(view);
    }
    @Test
    public void CheckUploadButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.file), withText("UPLOAD"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linear),
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
    @Test
    public void CheckChatButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.copy), withText("Chat"),
                        childAtPosition(
                                childAtPosition1(
                                        withId(R.id.linear),
                                        0),
                                1)));
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
    public void CheckVoiceButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.voice), withText("VOICE"),
                        childAtPosition(
                                childAtPosition2(
                                        withId(R.id.linear),
                                        0),
                                2)));
        appCompatButton.perform(scrollTo(), click());
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
    public void CheckHistoryButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.historya), withText("History"),
                        childAtPosition(
                                childAtPosition3(
                                        withId(R.id.linear),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());
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
    public void CheckSignOutButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signout), withText("SignOut"),
                        childAtPosition(
                                childAtPosition4(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton.perform(click());
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



    @After
    public void tearDown() throws Exception {
        Homepage = null;
    }
}