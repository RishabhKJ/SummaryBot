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

public class signupTest {

    @Rule
    public ActivityTestRule<signup> signupActivityTestRule = new ActivityTestRule<>(signup.class);
    private signup Signup =null;

    @Before
    public void setUp() throws Exception {
        Signup = signupActivityTestRule.getActivity();
    }
    @Test
    public void testEmail()
    {
        View view = Signup.findViewById(R.id.input_email);
        assertNotNull(view);
    }
    @Test
    public void testPassword()
    {
        View view = Signup.findViewById(R.id.input_password);
        assertNotNull(view);
    }
    @Test
    public void testSignUp()
    {
        View view = Signup.findViewById(R.id.btn_signup);
        assertNotNull(view);
    }
    @Test
    public void testLoginScreen()
    {
        View view = Signup.findViewById(R.id.login_page);
        assertNotNull(view);
    }

    @Test
    public void CheckCreateAccountButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_signup), withText("Create Account"),
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
    public void signupTest3() {
        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.login_page), withText("Already a member? Login"),
                        childAtPosition(
                                childAtPosition1(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatTextView.perform(scrollTo(), click());
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


    @After
    public void tearDown() throws Exception {
        Signup = null;
    }
}