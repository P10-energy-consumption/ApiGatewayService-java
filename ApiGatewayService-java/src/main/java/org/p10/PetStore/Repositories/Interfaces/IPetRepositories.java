package org.p10.PetStore.Repositories.Interfaces;

import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetStatus;

import javax.ws.rs.core.Request;
import java.util.List;
import java.util.UUID;

public interface IPetRepositories {

    Pet getPet(int petId);
    int insertPet(String request);
    int updatePet(String request);
    int insertPetPhoto(int petId, String request);
    int deletePet(int petId);
    List<Pet> getPetByStatus(PetStatus status);
}
