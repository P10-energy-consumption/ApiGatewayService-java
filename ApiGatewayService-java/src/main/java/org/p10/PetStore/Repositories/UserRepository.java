package org.p10.PetStore.Repositories;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.p10.PetStore.Models.Pojo.UserPojo;
import org.p10.PetStore.Models.User;
import org.p10.PetStore.Repositories.Interfaces.IUserRepositories;

import java.net.HttpURLConnection;

import static org.p10.PetStore.Repositories.HTTPUtil.*;

public class UserRepository implements IUserRepositories {

    private final String url;
    private final Gson gson;

    public UserRepository() {
        this.url = "http://host.docker.internal:8082/v1";
        this.gson = new Gson();
    }

    @Override
    public int insertUser(User user) {
        String json = gson.toJson(user);
        JSONObject jsonObject = new JSONObject(json);

        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/user", "POST");
            sendHTTPRequest(con, jsonObject);
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

    @Override
    public User getUser(String userName) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/user/" + userName, "GET");
            return new User(gson.fromJson(getHTTPResponse(con), UserPojo.class));
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public User updateUser(User user) {
        String json = gson.toJson(user);
        JSONObject jsonObject = new JSONObject(json);

        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/user", "PUT");
            sendHTTPRequest(con, jsonObject);
            return new User(gson.fromJson(getHTTPResponse(con), UserPojo.class));
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public String deleteUser(String userName) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/user/" + userName, "DELETE");
            return getHTTPResponse(con);
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }
}
