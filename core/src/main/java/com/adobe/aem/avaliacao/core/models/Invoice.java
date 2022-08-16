package com.adobe.aem.avaliacao.core.models;

public class Invoice {
    private int number;
    private int idProduct;
    private int idClient;
    private double price;

    public Invoice(int number, int idProduct, int idClient, double price) {
        this.number = number;
        this.idProduct = idProduct;
        this.idClient = idClient;
        this.price = price;
    }

    public Invoice() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
