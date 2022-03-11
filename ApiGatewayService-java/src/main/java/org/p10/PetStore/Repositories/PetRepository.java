package org.p10.PetStore.Repositories;

import org.p10.PetStore.Database.ConnectionFactory;
import org.p10.PetStore.Models.Pet;
import org.p10.PetStore.Models.PetCategory;
import org.p10.PetStore.Models.PetStatus;
import org.p10.PetStore.Repositories.Interfaces.IPetRepositories;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONObject;


public class PetRepository implements IPetRepositories {

    private final Connection connection;
    private final URL url;
    private final HttpURLConnection con;


    public PetRepository() {
        connection = new ConnectionFactory().createDBConnection();
        try {
            this.url = new URL("http://host.docker.internal:8081/v1/pet/1");
            this.con = (HttpURLConnection)url.openConnection();
            this.con.setRequestMethod("GET");
            this.con.setRequestProperty("Content-Type", "application/json");
            this.con.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not connect to backend.");
        }

    }

    @Override
    public Pet getPet(int petId) {
        JSONObject json = new JSONObject();
        json.put("id", petId);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insertPet(Pet pet) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "insert into pets.pet (id, name, category, status, tags, created, createdby) " +
                            "values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'PetStore.Pet.Api');"
            );
            stmt.setInt(1, pet.getId());
            stmt.setString(2, pet.getName());
            stmt.setInt(3, pet.getCategory().ordinal());
            stmt.setInt(4, pet.getStatus().ordinal());
            stmt.setString(5, pet.getTags());
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int updatePet(Pet pet) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "update pets.pet set Name = ?, Status = ?, Tags = ?, " +
                            "Category = ?, Modified = current_timestamp, " +
                            "ModifiedBy = 'PetStore.Pet.Api' " +
                            "where Id = ?"
            );
            stmt.setString(1, pet.getName());
            stmt.setInt(2, pet.getStatus().ordinal());
            stmt.setString(3, pet.getTags());
            stmt.setInt(4, pet.getCategory().ordinal());
            stmt.setInt(5, pet.getId());
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int insertPetPhoto(UUID photoId, int petId, String metaData, String url) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "insert into pets.photos (id, petid, url, metadata, created, createdby) " +
                            "values (?, ?, ?, ?, current_timestamp, 'PetStore.Pet.Api')"
            );
            stmt.setObject(1, photoId);
            stmt.setInt(2, petId);
            stmt.setString(3, url);
            stmt.setString(4, metaData);
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public int deletePet(int petId) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM pets.pet where id=?"
            );
            stmt.setInt(1, petId);
            int affectedRows = stmt.executeUpdate();

            stmt.close();
            connection.close();

            return affectedRows;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return 0;
        }
    }

    @Override
    public List<Pet> getPetByStatus(PetStatus status) {
        PreparedStatement stmt;
        List<Pet> petList = new ArrayList<>();
        try {
            stmt = connection.prepareStatement(
                    "select p.id, p.Name, p.Category, p.Status, p.Tags " +
                            "from pets.pet p where p.IsDelete = FALSE and p.status = ?"
            );
            stmt.setInt(1, status.ordinal());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setCategory(PetCategory.values()[rs.getInt("category")]);
                pet.setName(rs.getString("name"));
                pet.setPhotoUrls(null);
                pet.setTags(rs.getString("tags"));
                pet.setStatus(PetStatus.values()[rs.getInt("status")]);

                petList.add(pet);
            }

            stmt.close();
            connection.close();

            return petList;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());

            return null;
        }
    }
}
