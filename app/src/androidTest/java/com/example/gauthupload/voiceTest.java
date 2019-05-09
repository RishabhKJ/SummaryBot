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

public class voiceTest {

    @Rule
    public ActivityTestRule<voice> voiceActivityTestRule = new ActivityTestRule<>(voice.class);
    private voice Voice = null;

    @Before
    public void setUp() throws Exception {
        Voice = voiceActivityTestRule.getActivity();
    }

    @Test
    public void testinput() {
        View view = Voice.findViewById(R.id.textView1);
        assertNotNull(view);
    }

    @Test
    public void testtoggle() {
        View view = Voice.findViewById(R.id.toggleButton1);
        assertNotNull(view);
    }

    @Test
    public void testsubmit() {
        View view = Voice.findViewById(R.id.submitvoice);
        assertNotNull(view);
    }

    @Test
    public void testclear() {
        View view = Voice.findViewById(R.id.clearvoice);
        assertNotNull(view);
    }

    @Test
    public void testoutput() {
        View view = Voice.findViewById(R.id.fetch);
        assertNotNull(view);
    }
    @Test
    public void testpitchbar() {
        View view = Voice.findViewById(R.id.seek_bar_pitchc);
        assertNotNull(view);
    }
    @Test
    public void testspeedbar() {
        View view = Voice.findViewById(R.id.seek_bar_speedc);
        assertNotNull(view);
    }
    @Test
    public void testspeak() {
        View view = Voice.findViewById(R.id.button_speakc);
        assertNotNull(view);
    }
    @Test
    public void teststop() {
        View view = Voice.findViewById(R.id.button_stopc);
        assertNotNull(view);
    }
    @Test
    public void testdownload() {
        View view = Voice.findViewById(R.id.downloadvoice);
        assertNotNull(view);
    }
    @Test
    public void testshare() {
        View view = Voice.findViewById(R.id.sharesumvoice);
        assertNotNull(view);
    }
    @Test
    public void checkingStartRecordingButtonWorking() {
        ViewInteraction toggleButton = onView(
                allOf(withId(R.id.toggleButton1), withText("Start Recording"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        toggleButton.perform(scrollTo(), click());
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
    public void checkingSubmitButtonWorking() {
        ViewInteraction button = onView(
                allOf(withId(R.id.submitvoice), withText("Submit"),
                        childAtPosition2(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                4)));
        button.perform(scrollTo(), click());
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
    public void checkingClearButtonWorking() {
        ViewInteraction button = onView(
                allOf(withId(R.id.clearvoice), withText("Clear"),
                        childAtPosition4(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                5)));
        button.perform(scrollTo(), click());
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
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    @Test
    public void checkingSpeakButtonWorking() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.button_speakc),
                        childAtPosition5(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                4)));
        imageView.perform(scrollTo(), click());
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
    public void checkingStopButtonWorking() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.button_stopc),
                        childAtPosition6(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8),
                                5)));
        imageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition6(
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
        ViewInteraction imageView = onView(
                allOf(withId(R.id.downloadvoice),
                        childAtPosition7(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        imageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition7(
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
        ViewInteraction imageView = onView(
                allOf(withId(R.id.sharesumvoice),
                        childAtPosition8(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                10)));
        imageView.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition8(
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