package org.p10.PetStore.Repositories.Interfaces;

import org.p10.PetStore.Models.InventoryLine;
import org.p10.PetStore.Models.Order;
import org.p10.PetStore.Models.Pojo.OrderPojo;

import java.util.List;

public interface IStoreRepositories {
    List<InventoryLine> getInventory();
    Order getOrders(int orderId);
    Order postOrder(String request);
    int deleteOrder(int orderId);
}
