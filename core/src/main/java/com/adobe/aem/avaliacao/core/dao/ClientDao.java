package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public interface ClientDao {
    Collection<Client> readAll();
    Client readById(int id);
    void insert(Client client);

    Client readByEmail(String email);

    void update(Client client);
    void delete(int id);

    public void insertMany(Collection<Client> clients);

    void deleteMany(ArrayList<Integer> clientIds);
}
