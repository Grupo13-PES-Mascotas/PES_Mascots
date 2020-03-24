package org.pesmypetcare.mypetcare.features.users;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrChangePassword;
import org.pesmypetcare.mypetcare.controllers.TrDeleteUser;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewPet;
import org.pesmypetcare.mypetcare.controllers.TrRegisterNewUser;
import org.pesmypetcare.mypetcare.features.pets.Gender;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertTrue;

public class TestUser {
    private User user;
    private TrRegisterNewPet trRegisterNewPet;
    private TrRegisterNewUser trRegisterNewUser;
    private TrChangePassword trChangePassword;
    private TrDeleteUser trDeleteUser;
    private final String MIKE = "Mike";
    private final String PASSWORD = "12En)(";

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", PASSWORD);
        trRegisterNewPet = new TrRegisterNewPet(new StubPetManagerService());
        trRegisterNewUser = new TrRegisterNewUser(new StubUserManagerService());
        trChangePassword = new TrChangePassword(new StubUserManagerService());
        trDeleteUser = new TrDeleteUser(new StubUserManagerService(), new StubPetManagerService());
    }

    @Test
    public void shouldAddOnePet() throws PetRepeatException {
        Pet pet = getLinuxPet();
        user.addPet(pet);
        assertTrue("should add one pet", user.getPets().contains(pet));
    }

    @Test
    public void shouldCommunicateWithService() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        boolean addingResult = trRegisterNewPet.isResult();
        assertTrue("should communicate with service to add a pet", addingResult);
    }

    @Test(expected = PetAlreadyExistingException.class)
    public void shouldNotAddPetIfExisting() throws PetAlreadyExistingException, PetRepeatException {
        Pet pet = getLinuxPet();
        trRegisterNewPet.setUser(user);
        trRegisterNewPet.setPet(pet);
        trRegisterNewPet.execute();
        trRegisterNewPet.execute();
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(Gender.MALE);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }

    @Test
    public void shouldCreateUser() throws UserAlreadyExistingException {
        trRegisterNewUser.setUsername("Michael");
        trRegisterNewUser.setEmail("michael@gmail.com");
        trRegisterNewUser.setPassword("123abc!");
        trRegisterNewUser.execute();
        boolean addingResult = trRegisterNewUser.isResult();
        assertTrue("should communicate with service to add a user", addingResult);
    }

    @Test(expected = UserAlreadyExistingException.class)
    public void shouldNotAddUserIfExisting() throws UserAlreadyExistingException {
        trRegisterNewUser.setUsername(MIKE);
        trRegisterNewUser.setEmail("mike@gmail.com");
        trRegisterNewUser.setPassword("123abc?");
        trRegisterNewUser.execute();
        trRegisterNewUser.setUsername(MIKE);
        trRegisterNewUser.execute();

    }

    @Test
    public void shouldChangeUserPassword() throws SamePasswordException, NotValidPasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword("Ab12!@");
        trChangePassword.execute();
        boolean addingResult = trChangePassword.isResult();
        assertTrue("should communicate with service to change the password", addingResult);
    }

    @Test(expected = SamePasswordException.class)
    public void shouldNotChangePasswordIfIsTheCurrent() throws SamePasswordException, NotValidPasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword(PASSWORD);
    }

    @Test(expected = NotValidPasswordException.class)
    public void shouldNotChangePasswordIfNotValid() throws SamePasswordException, NotValidPasswordException {
        trChangePassword.setUser(this.user);
        trChangePassword.setNewPassword("1234");
    }

    @Test
    public void shouldDeleteUser() throws NotValidUserException {
        trDeleteUser.setUser(this.user);
        trDeleteUser.execute();
        boolean addingResult = trDeleteUser.isResult();
        assertTrue("should communicate with service to delete the user", addingResult);
    }

    @Test (expected = NotValidUserException.class)
    public void shouldNotDeleteIfNotExists() throws NotValidUserException {
        trDeleteUser.setUser(new User("Manuel", "manuel@gmail.com", "123"));
        trDeleteUser.execute();
        boolean addingResult = trDeleteUser.isResult();
        assertTrue("should communicate with service to delete the user", addingResult);
    }
}
