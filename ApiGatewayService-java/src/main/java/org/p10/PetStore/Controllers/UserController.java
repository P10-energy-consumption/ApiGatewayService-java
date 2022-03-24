package org.p10.PetStore.Controllers;

import org.p10.PetStore.Models.User;
import org.p10.PetStore.Repositories.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1")
public class UserController {

    private final UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    @POST
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertUser(String request) {
        int affectedRows = userRepository.insertUser(request);
        if (affectedRows > 0) {
            return Response.ok(affectedRows).build();
        } else {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateUser(String request) {
        User user = userRepository.updateUser(request);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/user/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@PathParam("username") String username) {
        String name = userRepository.deleteUser(username);
        if (name != null) {
            return Response.ok(name).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/user/{username}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUser(@PathParam("username") String username) {
        User user = userRepository.getUser(username);
        return Response.ok(user).build();
    }
}
