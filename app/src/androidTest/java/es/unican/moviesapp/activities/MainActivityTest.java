package es.unican.moviesapp.activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import android.widget.EditText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

import static org.hamcrest.Matchers.anything;

import es.unican.moviesapp.R;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testClickHomeInDrawer() {
        // Open the navigation drawer
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .perform(DrawerActions.open());

        // Click on the "Settings" item in the navigation drawer
        Espresso.onView(ViewMatchers.withId(R.id.navigation_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_settings));

    }

    // TEST 2: Hacer clic en la primera película y verificar que abre el diálogo
    @Test
    public void testOpenMovieDetailsDialog() {
        // En ListView se usa onData() para pulsar la primera posición (0)
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.lvMovies))
                .atPosition(0)
                .perform(click());

        // Verificar que el botón "Close" del AlertDialog se muestra en pantalla
        Espresso.onView(withText("Close"))
                .check(matches(isDisplayed()));
    }

    // TEST 3: Probar la filtración con el buscador
    @Test
    public void testSearchMovie() {
        // Pulsar el icono de búsqueda en la barra superior
        Espresso.onView(withId(R.id.action_search)).perform(click());

        // Escribir "Dune" en el SearchView
        Espresso.onView(isAssignableFrom(EditText.class))
                .perform(typeText("Dune"));

        // Comprobar que la primera película resultante contiene "Dune"
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.lvMovies))
                .atPosition(0)
                .onChildView(withId(R.id.tvTitle))
                .check(matches(withText("Dune")));
    }

}
