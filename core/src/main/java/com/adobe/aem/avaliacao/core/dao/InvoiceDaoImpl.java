package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.Invoice;
import com.adobe.aem.avaliacao.core.service.DatabaseService;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class InvoiceDaoImpl implements InvoiceDao {

    @Reference
    private DatabaseService databaseService;

    @Override
    public Collection<Invoice> readAll() {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM invoice";
        Collection<Invoice> invoices = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    invoices.add(new Invoice(rs.getInt("number"), rs.getInt("idProduct"), rs.getInt("idClient"), rs.getDouble("price")));
                }
                return invoices;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Invoice readById(int number) {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM invoice WHERE number = " + number;
        Invoice invoice = new Invoice();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    invoice = new Invoice(rs.getInt("number"), rs.getInt("idProduct"), rs.getInt("idClient"), rs.getDouble("price"));
                }
                return invoice;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void insert(Invoice invoice) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO invoice (id_product, id_client, price) VALUES (?, ?, ?) ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoice.getIdProduct());
            ps.setInt(2, invoice.getIdClient());
            ps.setDouble(3, invoice.getPrice());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Invoice invoice) {
        Connection connection = databaseService.getConnection();
        String sql = "UPDATE invoice SET id_product = ?, id_client = ?, price = ? WHERE number = " + invoice.getNumber();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoice.getIdProduct());
            ps.setInt(2, invoice.getIdClient());
            ps.setDouble(3, invoice.getPrice());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int number) {
        Connection connection = databaseService.getConnection();
        String sql = "DELETE FROM invoice WHERE number = " + number;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insertMany(ArrayList<Invoice> invoices) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO invoice (id_product, id_client, price) VALUES (?, ?, ?) ";
        for (Invoice i : invoices) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, i.getIdProduct());
                ps.setInt(2, i.getIdProduct());
                ps.setDouble(3, i.getIdProduct());
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
