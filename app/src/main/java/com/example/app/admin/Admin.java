package com.example.app.admin;

public class Admin
{
    private String judet, oras, parola, parolaVerif, email, prenume, nume, telefon, codPostal;

    public Admin(String judet, String oras, String parola, String parolaVerif, String email,
                 String prenume, String nume, String telefon, String codPostal)
    {
        this.judet = judet;
        this.oras = oras;
        this.parola = parola;
        this.parolaVerif = parolaVerif;
        this.email = email;
        this.prenume = prenume;
        this.nume = nume;
        this.telefon = telefon;
        this.codPostal = codPostal;
    }

    public Admin()
    {

    }

    public String getJudet() { return judet; }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getParolaVerif() {
        return parolaVerif;
    }

    public void setParolaVerif(String parolaVerif) {
        this.parolaVerif = parolaVerif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }
}
