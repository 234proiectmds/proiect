package com.example.app.adminCakesPanel;

public class Admin {

    private String Judet, Oras, Parola, ParolaVerif, Email, Prenume, Nume, Telefon, CodPostal;

    public Admin(String judet, String oras, String parola, String parolaVerif, String email,
                 String prenume, String nume, String telefon, String codPostal) {
        Judet = judet;
        Oras = oras;
        Parola = parola;
        ParolaVerif = parolaVerif;
        Email = email;
        Prenume = prenume;
        Nume = nume;
        Telefon = telefon;
        CodPostal = codPostal;
    }

    public Admin() {
    }

    public String getJudet() {
        return Judet;
    }

    public void setJudet(String judet) {
        Judet = judet;
    }

    public String getOras() {
        return Oras;
    }

    public void setOras(String oras) {
        Oras = oras;
    }

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }

    public String getParolaVerif() {
        return ParolaVerif;
    }

    public void setParolaVerif(String parolaVerif) {
        ParolaVerif = parolaVerif;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPrenume() {
        return Prenume;
    }

    public void setPrenume(String prenume) {
        Prenume = prenume;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getCodPostal() {
        return CodPostal;
    }

    public void setCodPostal(String codPostal) {
        CodPostal = codPostal;
    }
}
