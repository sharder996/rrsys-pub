package comp3350.rrsys.acceptance;

import android.app.Activity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.GregorianCalendar;

import comp3350.rrsys.presentation.HomeActivity;
import comp3350.rrsys.R;
import comp3350.rrsys.objects.Item;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.contrib.PickerActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import static org.hamcrest.Matchers.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AcceptanceTests
{
    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testHomeScreen()
    {
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCreate)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonUpdate)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonReview)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonMenu)).check(matches(isDisplayed())).check(matches(isEnabled()));
    }

    @Test
    public void testCreateReservation()
    {
        // activity_home
        onView(withId(R.id.buttonCreate)).check(matches(isDisplayed())).perform(click());

        // activity_create_reservation
        onView(withId(R.id.buttonCheckAvailability)).check(matches(isDisplayed())).check(matches(not(isEnabled())));
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(not(isEnabled())));

        // fill in the inputs for a reservation
        GregorianCalendar currDate = new GregorianCalendar();
        onView(withId(R.id.editTextDate)).check(matches(isDisplayed())).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(currDate.get(GregorianCalendar.YEAR), currDate.get(GregorianCalendar.MONTH) + 1, currDate.get(GregorianCalendar.DATE) + 1));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.editTextTime)).check(matches(isDisplayed())).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(9, 0));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.editLengthOfStay)).check(matches(isDisplayed())).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(10, 0));
        onView(withId(android.R.id.button1)).perform(click());

        Activity currentActivity = getCurrentActivity();
        onView(withId(R.id.editNumberOfPeople)).check(matches(isDisplayed()));
        NumberPicker numberPicker = currentActivity.findViewById(R.id.editNumberOfPeople);
        int numberOfPeople = numberPicker.getValue();

        // check the enabled status of the buttons
        onView(withId(R.id.buttonCheckAvailability)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(not(isEnabled())));

        // make the reservation
        onView(withId(R.id.buttonCheckAvailability)).perform(click());
        onData(allOf()).inAdapterView(withId(R.id.availabilityList)).atPosition(0).perform(click());
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_create_confirm_reservation
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonConfirm)).check(matches(isDisplayed())).check(matches(not(isEnabled())));
        onView(withId(R.id.editFirstName)).check(matches(isDisplayed())).perform(clearText(), typeText("Kermit"));
        onView(withId(R.id.editLastName)).check(matches(isDisplayed())).perform(clearText(), typeText("theFrog"));
        onView(withId(R.id.editPhoneNumber)).check(matches(isDisplayed())).perform(clearText(), typeText("666-420-6969"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.buttonConfirm)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_reservation_receipt
        onView(withId(R.id.textReservationCode)).check(matches(isDisplayed())).check(matches(not(withText("123456"))));
        onView(withId(R.id.textDateInfo)).check(matches(isDisplayed()))
                .check(matches(isDisplayed())).check(matches(withText((currDate.get(GregorianCalendar.MONTH) + 1) + "/" + (currDate.get(GregorianCalendar.DATE) + 1) + "/" + currDate.get(GregorianCalendar.YEAR))));
        onView(withId(R.id.textTimeInfo)).check(matches(isDisplayed())).check(matches(isDisplayed())).check(matches(withText("9:00 - 10:00")));
        onView(withId(R.id.textNumberOfPeopleInfo)).check(matches(isDisplayed())).check(matches(withText("" + numberOfPeople)));
        onView(withId(R.id.buttonPreOrder)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_order
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed()));

        // add an item
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(0).perform(click());
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(1).perform(click());

        onView(ViewMatchers.withContentDescription("popup_order_window")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.editTextInstructions)).check(matches(isDisplayed())).perform(clearText(), typeText("No bun"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());

        // confirm that the item got added
        onView(withId(R.id.buttonViewOrder)).check(matches(isDisplayed())).perform(click());
        onView(ViewMatchers.withContentDescription("popup_view_order")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(1))));
        onData(instanceOf(Item.class)).atPosition(0).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();

        // add a second item
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(2).perform(click());
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());

        // remove a item
        onView(withId(R.id.buttonViewOrder)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(2))));
        onData(instanceOf(Item.class)).atPosition(1).perform(click());
        onView(ViewMatchers.withContentDescription("popup_remove_item")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(1))));
        Espresso.pressBack();

        // confirm the order
        onView(withId(R.id.buttonConfirmOrder)).check(matches(isDisplayed())).perform(click());

        // activity_home
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testUpdateReservation()
    {
        // activity_home
        onView(withId(R.id.buttonUpdate)).check(matches(isDisplayed())).perform(click());

        // activity_get_reservation_update
        onView(withId(R.id.editTextReservationCode)).check(matches(isDisplayed())).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonEnter)).check(matches(isDisplayed())).perform(click());

        // activity_get_choice_update_reservation
        onView(withId(R.id.buttonUpdate)).check(matches(isDisplayed())).perform(click());

        // activity_update_reservation
        onView(withId(R.id.buttonCheckAvailability)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(not(isEnabled())));

        // fill in the inputs for a reservation
        GregorianCalendar currDate = new GregorianCalendar();
        onView(withId(R.id.editTextDate)).check(matches(isDisplayed())).check(matches(not(withText("")))).perform(click());
        onView(isAssignableFrom(DatePicker.class)).perform(setDate(currDate.get(GregorianCalendar.YEAR), currDate.get(GregorianCalendar.MONTH) + 1, currDate.get(GregorianCalendar.DATE) + 1));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.editTextTime)).check(matches(isDisplayed())).check(matches(not(withText("")))).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(9, 0));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.editLengthOfStay)).check(matches(isDisplayed())).check(matches(not(withText("")))).perform(click());
        onView(isAssignableFrom(TimePicker.class)).perform(setTime(10, 0));
        onView(withId(android.R.id.button1)).perform(click());

        Activity currentActivity = getCurrentActivity();
        onView(withId(R.id.editNumberOfPeople)).check(matches(isDisplayed()));
        NumberPicker numberPicker = currentActivity.findViewById(R.id.editNumberOfPeople);
        int numberOfPeople = numberPicker.getValue();

        // check the enabled status of the buttons
        onView(withId(R.id.buttonCheckAvailability)).check(matches(isDisplayed())).check(matches(isEnabled()));
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(not(isEnabled())));

        // make the reservation
        onView(withId(R.id.buttonCheckAvailability)).perform(click());
        onData(allOf()).inAdapterView(withId(R.id.availabilityList)).atPosition(0).perform(click());
        onView(withId(R.id.buttonMakeReservation)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_confirm_updates
        onView(withId(R.id.textReservationCode)).check(matches(isDisplayed())).check(matches(not(withText("123456"))));
        onView(withId(R.id.textDateInfo)).check(matches(isDisplayed()))
                .check(matches(isDisplayed())).check(matches(withText((currDate.get(GregorianCalendar.MONTH) + 1) + "/" + (currDate.get(GregorianCalendar.DATE) + 1) + "/" + currDate.get(GregorianCalendar.YEAR))));
        onView(withId(R.id.textTimeInfo)).check(matches(isDisplayed())).check(matches(isDisplayed())).check(matches(withText("9:00 - 10:00")));
        onView(withId(R.id.textNumberOfPeopleInfo)).check(matches(isDisplayed())).check(matches(withText("" + numberOfPeople)));
        onView(withId(R.id.buttonDone)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_home
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testReviewReservation()
    {
        // activity_home
        onView(withId(R.id.buttonReview)).check(matches(isDisplayed())).perform(click());

        // activity_get_reservation_update
        onView(withId(R.id.editTextReservationCode)).check(matches(isDisplayed())).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonEnter)).check(matches(isDisplayed())).perform(click());

        // activity_review_reservation
        onView(withId(R.id.textDateInfo)).check(matches(isDisplayed())).check(matches(isDisplayed())).check(matches(not(withText("*date info*"))));
        onView(withId(R.id.textTimeInfo)).check(matches(isDisplayed())).check(matches(isDisplayed())).check(matches(not(withText("*time info*"))));
        onView(withId(R.id.textNumberOfPeopleInfo)).check(matches(isDisplayed())).check(matches(not(withText("*# people*"))));
        onView(withId(R.id.buttonDone)).check(matches(isDisplayed())).check(matches(isEnabled())).perform(click());

        // activity_home
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testViewMenu()
    {
        // activity_home
        onView(withId(R.id.buttonMenu)).check(matches(isDisplayed())).perform(click());

        // activity_menu
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(0).perform(click());
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(1).perform(click());
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(0).perform(click());
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed())).perform(click());

        // activity_home
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testPreOrderUpdateReservation()
    {
        // activity_home
        onView(withId(R.id.buttonUpdate)).check(matches(isDisplayed())).perform(click());

        // activity_get_reservation_update
        onView(withId(R.id.editTextReservationCode)).check(matches(isDisplayed())).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonEnter)).check(matches(isDisplayed())).perform(click());

        // activity_get_choice_update_reservation
        onView(withId(R.id.buttonPreorder)).check(matches(isDisplayed())).perform(click());

        // activity_order
        onView(withId(R.id.menuList)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonBack)).check(matches(isDisplayed()));

        // add an item
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(0).perform(click());
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(1).perform(click());

        onView(ViewMatchers.withContentDescription("popup_order_window")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.editTextInstructions)).check(matches(isDisplayed())).perform(clearText(), typeText("No bun"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());

        // confirm that the item got added
        onView(withId(R.id.buttonViewOrder)).check(matches(isDisplayed())).perform(click());
        onView(ViewMatchers.withContentDescription("popup_view_order")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(1))));
        onData(instanceOf(Item.class)).atPosition(0).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();

        // add a second item
        onData(allOf()).inAdapterView(withId(R.id.menuList)).atPosition(2).perform(click());
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());

        // remove a item
        onView(withId(R.id.buttonViewOrder)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(2))));
        onData(instanceOf(Item.class)).atPosition(1).perform(click());
        onView(ViewMatchers.withContentDescription("popup_remove_item")).inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
        onView(withId(R.id.buttonPopupConfirm)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.orderList)).check(ViewAssertions.matches((Matchers.withListSize(1))));
        Espresso.pressBack();

        // confirm the order
        onView(withId(R.id.buttonConfirmOrder)).check(matches(isDisplayed())).perform(click());

        // activity_home
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()));
    }

    private Activity getCurrentActivity()
    {
        final Activity[] currentActivity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable()
        {
            @Override
            public void run()
            {
                Collection<Activity> allActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if(!allActivities.isEmpty())
                    currentActivity[0] = allActivities.iterator().next();
            }
        });
        return currentActivity[0];
    }
}

class Matchers
{
    public static Matcher<View> withListSize(final int size)
    {
        return new TypeSafeMatcher<View>()
        {
            @Override public boolean matchesSafely(final View view) { return ((ListView)view).getCount() == size; }

            @Override public void describeTo(final Description description) { description.appendText("ListView should have " + size + " items"); }
        };
    }
}
