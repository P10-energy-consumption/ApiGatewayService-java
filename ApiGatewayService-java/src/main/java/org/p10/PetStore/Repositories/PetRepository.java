package org.p10.PetStore.Repositories;

import com.google.gson.reflect.TypeToken;
import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Models.Pojo.PetPojo;
import org.p10.PetStore.Repositories.Interfaces.IPetRepositories;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import static org.p10.PetStore.Repositories.HTTPUtil.*;


public class PetRepository implements IPetRepositories {

    private final String url;
    private final Gson gson;


    public PetRepository() {
        this.url = "http://host.docker.internal:8081/v1";
        this.gson = new Gson();
    }

    @Override
    public Pet getPet(int petId) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet/" + petId, "GET");
            return new Pet(gson.fromJson(getHTTPResponse(con), PetPojo.class));
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    @Override
    public int insertPet(String request) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet", "POST");
            sendHTTPRequest(con, request);
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
    public int updatePet(String request) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet", "PUT");
            sendHTTPRequest(con, request);
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
    public int insertPetPhoto(int petId, String request) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet/" + petId + "/uploadImage", "POST");
            sendHTTPRequest(con, request);
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
    public int deletePet(int petId) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet/" + petId, "DELETE");
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
    public List<Pet> getPetByStatus(PetStatus status) {
        HttpURLConnection con = null;
        try {
            con = getConnection(this.url + "/pet/findByStatus?status=" + status.ordinal(), "GET");
            String response = getHTTPResponse(con);
            if (response != null) {
                // Deserialize List of PetPojo objects
                Type listOfPetType = new TypeToken<ArrayList<PetPojo>>(){}.getType();
                List<PetPojo> petPojos = gson.fromJson(response, listOfPetType);

                // Convert PetPojo objects to Pet objects
                List<Pet> petList = new ArrayList<>();
                for (PetPojo petPojo : petPojos) {
                    petList.add(new Pet(petPojo));
                }
                return petList;
            } else {
                return null;
            }
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }


}
