package com.adobe.aem.avaliacao.core.dao;

import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.service.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Component(immediate = true, service = ClientDao.class)
public class ClientDaoImpl implements ClientDao {

    @Reference
    private DatabaseService databaseService;

    @Override
    public Collection<Client> readAll() {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM client";
        Collection<Client> clients = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clients.add(new Client(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("email")));
                }
                return clients;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Client readById(int id) {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM client WHERE id = " + id;
        Client client = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("email"));
                }
                return client;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void insert(Client client) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO client (name, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getPassword());
            ps.setString(3, client.getEmail());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Client readByEmail(String email) {
        Connection connection = databaseService.getConnection();
        String sql = "SELECT * FROM client WHERE email = '" + email + "'";
        Client client = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    client = new Client(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("email"));
                }
                return client;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Client client) {
        Connection connection = databaseService.getConnection();
        String sql = "UPDATE client SET name = ?, password = ?, email = ? WHERE id = " + client.getId();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, client.getName());
            ps.setString(2, client.getPassword());
            ps.setString(3, client.getEmail());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = databaseService.getConnection();
        String sql = "DELETE FROM client WHERE id = " + id;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insertMany(Collection<Client> clients) {
        Connection connection = databaseService.getConnection();
        String sql = "INSERT INTO client (name, password, email) VALUES (?, ?, ?)";
        for (Client c : clients) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, c.getName());
                ps.setString(2, c.getPassword());
                ps.setString(3, c.getEmail());
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void deleteMany(ArrayList<Integer> clientIds) {
        Connection connection = databaseService.getConnection();

        for (Integer id : clientIds) {
            String sql = "DELETE FROM client WHERE id = " + id;
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
