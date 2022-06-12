package com.example.app.client;

public class Client {
    private String prenume, nume, email, parola, confirmaParola, telefon, strada, numarStrada, detaliiAdresa, codPostal, oras, judet;

    public Client() {
    }

    public Client(String firstName, String lastName, String email, String password, String confirmPassword, String mobile, String street, String streetNumber, String detailsAddress, String zipCode, String city, String county) {
        this.prenume = firstName;
        this.nume = lastName;
        this.email = email;
        this.parola = password;
        this.confirmaParola = confirmPassword;
        this.telefon = mobile;
        this.strada = street;
        this.numarStrada = streetNumber;
        this.detaliiAdresa = detailsAddress;
        this.codPostal = zipCode;
        this.oras = city;
        this.judet = county;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getConfirmaParola() {
        return confirmaParola;
    }

    public void setConfirmaParola(String confirmaParola) {
        this.confirmaParola = confirmaParola;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getNumarStrada() {
        return numarStrada;
    }

    public void setNumarStrada(String numarStrada) {
        this.numarStrada = numarStrada;
    }

    public String getDetaliiAdresa() {
        return detaliiAdresa;
    }

    public void setDetaliiAdresa(String detaliiAdresa) {
        this.detaliiAdresa = detaliiAdresa;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }
}
