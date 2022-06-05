package com.example.app.adminCakesPanel;

public class DetaliiTorturi {

    public String torturi, cantitate, pret, descriere, imageURL, randomUID, admID;

    public DetaliiTorturi(String torturi, String cantitate, String pret, String descriere, String imageURL, String randomUID, String adminId) {
        torturi = torturi;
        cantitate = cantitate;
        pret = pret;
        descriere = descriere;
        imageURL = imageURL;
        randomUID = randomUID;
        this.admID = adminId;
    }

    public String getTorturi() {
        return torturi;
    }

    public void setTorturi(String torturi) {
        torturi = torturi;
    }

    public String getCantitate() {
        return cantitate;
    }

    public void setCantitate(String cantitate) {
        cantitate = cantitate;
    }

    public String getPret() {
        return pret;
    }

    public void setPret(String pret) {
        pret = pret;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        descriere = descriere;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        imageURL = imageURL;
    }

    public String getRandomUID() {
        return randomUID;
    }

    public void setRandomUID(String randomUID) {
        randomUID = randomUID;
    }

    public String getAdmID() {
        return admID;
    }

    public void setAdmID(String adminID) {
        admID = adminID;
    }
}
