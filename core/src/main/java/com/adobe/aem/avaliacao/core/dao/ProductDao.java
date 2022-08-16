package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Product;

import java.util.ArrayList;
import java.util.Collection;

public interface ProductDao {

    Collection<Product> readAll();
    Product readById(int id);
    void insert(Product client);
    void update(Product client);
    void delete(int id);
    void insertMany(ArrayList<Product> products);
}
