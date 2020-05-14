package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.meals.TrNewPetMeal;
import org.pesmypetcare.mypetcare.features.pets.MealAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMealManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class TestTrNewPetMeal {
    private static final int YEAR = 2020;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    private User user;
    private Pet linux;
    private TrNewPetMeal trNewPetMeal;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        trNewPetMeal = new TrNewPetMeal(new StubMealManagerService(), new StubGoogleCalendarService());
    }

    @Test
    public void shoudlAddOneMeal() throws MealAlreadyExistingException, InterruptedException, ExecutionException,
        InvalidFormatException {
        Meals meal = getTestMeal();
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(meal);
        trNewPetMeal.execute();
        boolean result = trNewPetMeal.isResult();
        assertTrue("should communicate with service to add a meal", result);
    }

    @Test(expected = MealAlreadyExistingException.class)
    public void shouldNotAddIfExisting() throws MealAlreadyExistingException, InterruptedException,
        ExecutionException, InvalidFormatException {
        Meals meal = getTestMeal();
        trNewPetMeal.setUser(user);
        trNewPetMeal.setPet(linux);
        trNewPetMeal.setMeal(meal);
        trNewPetMeal.execute();
        trNewPetMeal.execute();
    }

    private Meals getTestMeal() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(YEAR, 2, DAY, HOUR, MINUTES, SECONDS);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Meals(date, (double) SECONDS, "Linux meal");
    }
}
