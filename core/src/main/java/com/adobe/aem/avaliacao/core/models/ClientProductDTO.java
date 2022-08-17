package com.adobe.aem.avaliacao.core.models;

import java.util.ArrayList;

public class ClientProductDTO {
    Client client;
    ArrayList<Product> products;

    public ClientProductDTO(Client client, ArrayList<Product> products) {
        this.client = client;
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
