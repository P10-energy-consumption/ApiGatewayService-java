package org.p10.PetStore.Repositories;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.p10.PetStore.Models.*;
import org.p10.PetStore.Models.Pojo.OrderPojo;
import org.p10.PetStore.Repositories.Interfaces.IStoreRepositories;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.p10.PetStore.Repositories.HTTPUtil.*;

public class StoreRepository implements IStoreRepositories {

    private final String url;
    private final Gson gson;

    public StoreRepository() {
        this.url = "http://host.docker.internal:8083/v1";
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .create();
    }

    static class LocalDateAdapter implements JsonSerializer<LocalDateTime> {
        public JsonElement serialize(LocalDateTime date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        }
    }
    static class LocalDateDeserializer implements JsonDeserializer<LocalDateTime> {
        public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Instant instant = Instant.ofEpochMilli(json.getAsJsonPrimitive().getAsLong());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    @Override
    public List<InventoryLine> getInventory() {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/store/inventory", "GET");
            String response = getHTTPResponse(con);
            if (response != null) {
                // Deserialize List of InventoryLine objects
                Type listOfInventoryType = new TypeToken<ArrayList<InventoryLine>>(){}.getType();
                return gson.fromJson(response, listOfInventoryType);
            } else {
                return null;
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public Order getOrders(int orderId) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/store/order/" + orderId, "GET");
            return new Order(gson.fromJson(getHTTPResponse(con), OrderPojo.class));
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public Order postOrder(OrderPojo order) {
        String json = gson.toJson(order);
        JSONObject jsonObject = new JSONObject(json);

        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/store/order", "POST");
            sendHTTPRequest(con, jsonObject);
            String response = getHTTPResponse(con);
            if (response != null) {
                return new Order(gson.fromJson(response, OrderPojo.class));
            } else {
                return null;
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public int deleteOrder(int orderId) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/store/order/" + orderId, "DELETE");
            String response = getHTTPResponse(con);
            if (response != null) {
                return Integer.parseInt(response);
            } else {
                return 0;
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
