package org.p10.PetStore.Controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.p10.PetStore.Models.*;
import org.p10.PetStore.Models.Pojo.PetPhotoPojo;
import org.p10.PetStore.Models.Pojo.PetPojo;
import org.p10.PetStore.Repositories.PetRepository;

import java.util.List;
import java.util.UUID;

@Path("/v1")
public class PetController {

    private final PetRepository petRepository;

    public PetController() {
        this.petRepository = new PetRepository();
    }

    @GET
    @Path("/pet/{id}")
    @Produces("text/plain")
    public Response getPet(@PathParam("id") int petId) {
        Pet response = petRepository.getPet(petId);
        return Response.ok(response).build();
    }

    @POST
    @Path("/pet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPet(PetPojo petPojo) {
        Pet pet = new Pet(petPojo.getId(), PetCategory.values()[petPojo.getCategory()],
                petPojo.getName(), petPojo.getPhotoUrls(),
                petPojo.getTags(), PetStatus.values()[petPojo.getStatus()]);
        int response = petRepository.insertPet(pet);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/pet")
    @Produces("text/plain")
    public Response updatePet(PetPojo petPojo) {
        Pet pet = new Pet(petPojo.getId(), PetCategory.values()[petPojo.getCategory()],
                petPojo.getName(), petPojo.getPhotoUrls(),
                petPojo.getTags(), PetStatus.values()[petPojo.getStatus()]);
        int response = petRepository.updatePet(pet);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/pet/{petId}/uploadImage")
    @Produces("text/plain")
    public Response insertPetPhoto(PetPhotoPojo petPhotoPojo) {
        UUID photoId = UUID.randomUUID();
        String fileUrl = "/some/url/" + photoId;
        int response = petRepository.insertPetPhoto(photoId, petPhotoPojo.getPetID(),
                petPhotoPojo.getMetaData(), fileUrl);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/pet/{petId}")
    @Produces("text/plain")
    public Response deletePet(@PathParam("petId") int petId) {
        int response = petRepository.deletePet(petId);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/pet/findByStatus")
    @Produces("text/plain")
    public Response getPetByStatus(@QueryParam("petStatus") int petStatus) {
        List<Pet> response = petRepository.getPetByStatus(PetStatus.values()[petStatus]);
        return Response.ok(response).build();
    }
}