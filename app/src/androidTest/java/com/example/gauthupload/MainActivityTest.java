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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity =null;

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }
    @Test
    public void testEmail()
    {
        View view = mainActivity.findViewById(R.id.email);
        assertNotNull(view);
    }
    @Test
    public void testPassword()
    {
        View view = mainActivity.findViewById(R.id.password);
        assertNotNull(view);
    }
    @Test
    public void testCustomSignin()
    {
        View view = mainActivity.findViewById(R.id.ah_login);
        assertNotNull(view);
    }
    @Test
    public void testCreateAccount()
    {
        View view = mainActivity.findViewById(R.id.sign_in_button);
        assertNotNull(view);
    }
    @Test
    public void testGoogleSignin()
    {
        View view = mainActivity.findViewById(R.id.sign_in_google);
        assertNotNull(view);
    }
    @Test
    public void testNeedHelp()
    {
        View view = mainActivity.findViewById(R.id.needhelp);
        assertNotNull(view);
    }
    @Test
    public void testOtheritem()
    {
        View view = mainActivity.findViewById(R.id.email);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
    @Test
    public void CheckCustomLoginButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ah_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
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
    public void CheckNewAccountButtonWorking() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.sign_in_button), withText("New Account"),
                        childAtPosition(
                                childAtPosition2(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatTextView.perform(scrollTo(), click());
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
    public void CheckCutomLoginButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ah_login), withText("LOGIN"),
                        childAtPosition(
                                childAtPosition3(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
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
    public void CheckNeedHelpWorking() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.needhelp), withText("Need Help?"),
                        childAtPosition(
                                childAtPosition5(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatTextView.perform(scrollTo(), click());
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
    @Test
    public void CheckGoogleButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.sign_in_google),
                        childAtPosition(
                                childAtPosition4(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
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


}