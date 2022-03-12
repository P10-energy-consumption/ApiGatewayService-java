package org.p10.PetStore.Controllers;

import org.p10.PetStore.Models.InventoryLine;
import org.p10.PetStore.Models.Order;
import org.p10.PetStore.Models.Pojo.OrderPojo;
import org.p10.PetStore.Models.OrderStatus;
import org.p10.PetStore.Repositories.StoreRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1")
public class StoreController {

    private final StoreRepository storeRepository;

    public StoreController() {
        this.storeRepository = new StoreRepository();
    }

    @GET
    @Path("/store/inventory")
    @Produces("text/plain")
    public Response getInventory() {
        List<InventoryLine> inventory = storeRepository.getInventory();
        if (inventory != null) {
            return Response.ok(inventory).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/store/order/{id}")
    @Produces("text/plain")
    public Response getOrder(@PathParam("id") int orderId) {
        Order order = storeRepository.getOrders(orderId);
        if (order != null) {
            return Response.ok(order).build();
        } else {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/store/order")
    @Produces("text/plain")
    public Response placeOrder(OrderPojo orderPojo) {
        Order o = storeRepository.postOrder(orderPojo);
        if (o != null) {
            return Response.ok(o).build();
        } else {
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/store/order/{id}")
    @Produces("text/plain")
    public Response deleteOrder(@PathParam("id") int orderId) {
        int affectedRows = storeRepository.deleteOrder(orderId);
        if (affectedRows > 0) {
            return Response.ok(affectedRows).build();
        } else {
            return Response.serverError().build();
        }
    }
}
