package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.PetAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

public interface PetManagerService {

    /**
     * Updates the pet.
     * @param pet The pet to update
     */
    void updatePet(Pet pet);

    /**
     * Registers a new pet to a user.
     * @param user The user to whom the pet has to be registered
     * @param pet The pet to be registered to the user
     * @return True if the register has been done without any problems
     * @throws PetAlreadyExistingException The pet already belongs to the user
     */
    boolean registerNewPet(User user, Pet pet);

    /**
     * Updates the image of the pet.
     * @param user The owner of the pet
     * @param pet The pet from which the image will be updated
     * @param newPetImage The new image for the pet
     */
    void updatePetImage(User user, Pet pet, Bitmap newPetImage);

    /**
     * Delete the pet.
     * @param pet The pet to delete
     * @param user The owner of the pet
     */
    void deletePet(Pet pet, User user);

    /**
     * Delete a user.
     * @param user The user
     */
    void deletePetsFromUser(User user);

    /**
     * Find all the pets from a user.
     * @param user The user who wants to get his pets
     * @return The pets that belongs to the user
     */
    List<Pet> findPetsByOwner(User user) throws PetRepeatException;
}
