
package com.example.gauthupload;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class copyTest {

    @Rule
    public ActivityTestRule<copy> copyActivityTestRule = new ActivityTestRule<>(copy.class);
    private copy Copy = null;

    @Before
    public void setUp() throws Exception {
        Copy = copyActivityTestRule.getActivity();
    }

    @Test
    public void testInput() {
        View view = Copy.findViewById(R.id.textEt);
        assertNotNull(view);
    }

    @Test
    public void testSubmit() {
        View view = Copy.findViewById(R.id.saveBtn);
        assertNotNull(view);
    }

    @Test
    public void testMessages() {
        View view = Copy.findViewById(R.id.messages);
        assertNotNull(view);
    }

    @Test
    public void testMail() {
        View view = Copy.findViewById(R.id.Gmail);
        assertNotNull(view);
    }

    @Test
    public void testWhatsapp() {
        View view = Copy.findViewById(R.id.whatsapp);
        assertNotNull(view);
    }
    @Test
    public void testOutput() {
        View view = Copy.findViewById(R.id.fetch);
        assertNotNull(view);
    }
    @Test
    public void testSpeedBar() {
        View view = Copy.findViewById(R.id.seek_bar_speedb);
        assertNotNull(view);
    }
    @Test
    public void testPitchBar() {
        View view = Copy.findViewById(R.id.seek_bar_pitchb);
        assertNotNull(view);
    }
    @Test
    public void testSpeak() {
        View view = Copy.findViewById(R.id.button_speakb);
        assertNotNull(view);
    }
    @Test
    public void testStop() {
        View view = Copy.findViewById(R.id.button_stopb);
        assertNotNull(view);
    }
    @Test
    public void testDownload() {
        View view = Copy.findViewById(R.id.downloadfile1);
        assertNotNull(view);
    }
    @Test
    public void testShare() {
        View view = Copy.findViewById(R.id.sharesumchat);
        assertNotNull(view);
    }
    @Test
    public void CheckSubmitButtonWorking() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.saveBtn), withText("Submit"),
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
    public void checkMessageButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.messages),
                        childAtPosition(
                                childAtPosition2(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                0)));
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
    public void checkMailButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.Gmail),
                        childAtPosition3(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1)));
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
    public void checkWhatsappButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.whatsapp),
                        childAtPosition4(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                2)));
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
    public void checkSpeakButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_speakb),
                        childAtPosition5(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                4)));
        appCompatImageView.perform(scrollTo(), click());
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
    public void checkStopButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.button_stopb),
                        childAtPosition6(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        7),
                                5)));
        appCompatImageView.perform(scrollTo(), click());
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
    public void checkShareButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.sharesumchat),
                        childAtPosition8(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                9)));
        appCompatImageView.perform(scrollTo(), click());
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
    @Test
    public void checkDownloadButtonWorking() {
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.downloadfile1),
                        childAtPosition7(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatImageView.perform(scrollTo(), click());
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
}