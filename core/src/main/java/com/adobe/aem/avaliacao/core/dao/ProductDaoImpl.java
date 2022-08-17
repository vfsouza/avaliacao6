package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Product;
import com.adobe.aem.avaliacao.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component(immediate = true, service = ProductDao.class)
public class ProductDaoImpl implements ProductDao {

    @Reference
    private DatabaseService databaseService;

    @Override
    public ArrayList<Product> readAll(String param) {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM product";
        if (param != null) {
            if (param.equals("category")) {
                sql += " ORDER BY " + param;
            } else if (param.equals("price")) {
                sql += " ORDER BY " + param;
            } else {
                sql += " WHERE name LIKE '%" + param + "%'";
            }
        }
        ArrayList<Product> products = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("category"), rs.getDouble("price")));
                }
                return products;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Product readById(int id) {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM product WHERE id = " + id;
        Product product = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("category"), rs.getDouble("price"));
                }
                return product;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void insert(Product product) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?) ";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Product product) {
        Connection connection = databaseService.getConnection();
        String sql = "UPDATE product SET name = ?, category = ?, price = ? WHERE id = " + product.getId();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getCategory());
            ps.setDouble(3, product.getPrice());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = databaseService.getConnection();
        String sql = "DELETE FROM product WHERE id = " + id;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insertMany(ArrayList<Product> products) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO product (name, category, price) VALUES (?, ?, ?) ";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Product p : products) {
                ps.setString(1, p.getName());
                ps.setString(2, p.getCategory());
                ps.setDouble(3, p.getPrice());
                ps.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteMany(ArrayList<Integer> productIds) {
        Connection connection = databaseService.getConnection();

        for (Integer id : productIds) {
            String sql = "DELETE FROM product WHERE id = " + id;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
