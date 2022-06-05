package com.example.app.adminCakesPanel;

public class DetaliiTorturi {

    public String Torturi, Cantitate, Pret, Descriere, ImageURL, RandomUID, AdminID;

    public DetaliiTorturi(String torturi, String cantitate, String pret, String descriere, String imageURL, String randomUID, String adminID) {
        Torturi = torturi;
        Cantitate = cantitate;
        Pret = pret;
        Descriere = descriere;
        ImageURL = imageURL;
        RandomUID = randomUID;
        AdminID = adminID;
    }

    public String getTorturi() {
        return Torturi;
    }

    public void setTorturi(String torturi) {
        Torturi = torturi;
    }

    public String getCantitate() {
        return Cantitate;
    }

    public void setCantitate(String cantitate) {
        Cantitate = cantitate;
    }

    public String getPret() {
        return Pret;
    }

    public void setPret(String pret) {
        Pret = pret;
    }

    public String getDescriere() {
        return Descriere;
    }

    public void setDescriere(String descriere) {
        Descriere = descriere;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getAdminID() {
        return AdminID;
    }

    public void setAdminID(String adminID) {
        AdminID = adminID;
    }
}
