package org.p10.PetStore.Models.Pojo;

public class PetPhotoPojo {
    private int petID;
    private String metaData;
    private String file;

    public PetPhotoPojo(int petID, String metaData, String file) {
        this.petID = petID;
        this.metaData = metaData;
        this.file = file;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
