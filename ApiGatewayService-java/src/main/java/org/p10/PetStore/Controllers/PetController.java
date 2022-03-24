package org.p10.PetStore.Controllers;

import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Repositories.PetRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1")
public class PetController {

    private final PetRepository petRepository;

    public PetController() {
        this.petRepository = new PetRepository();
    }

    @GET
    @Path("/pet/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPet(@PathParam("id") int petId) {
        Pet response = petRepository.getPet(petId);
        return Response.ok(response).build();
    }

    @POST
    @Path("/pet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPet(String request) {
        int response = petRepository.insertPet(request);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/pet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePet(String request) {
        int response = petRepository.updatePet(request);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/pet/{petId}/uploadImage")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPetPhoto(@PathParam("petId") int petId, String request) {
        int response = petRepository.insertPetPhoto(petId, request);
        if (response > 0) {
            return Response.ok(response).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/pet/{petId}")
    @Produces(MediaType.TEXT_PLAIN)
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