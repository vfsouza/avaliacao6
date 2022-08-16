package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Invoice;

import java.util.ArrayList;
import java.util.Collection;

public interface InvoiceDao {

    Collection<Invoice> readAll();
    Invoice readById(int id);
    void insert(Invoice client);
    void update(Invoice client);
    void delete(int id);
    void insertMany(ArrayList<Invoice> invoices);
}
