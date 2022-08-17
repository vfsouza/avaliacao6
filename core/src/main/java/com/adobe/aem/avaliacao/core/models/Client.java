package com.adobe.aem.avaliacao.core.models;

public class Client {
    private int id;
    private String name;
    private String password;
    private String email;


    public Client(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Client(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Client(String name) {
        this.name = name;
    }

    public Client() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
