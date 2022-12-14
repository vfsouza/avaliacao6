package com.adobe.aem.avaliacao.core.service;

import com.adobe.aem.avaliacao.core.dao.ClientDao;
import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.Product;
import com.adobe.aem.avaliacao.core.models.ResponseJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

@Component(immediate = true, service = ClientService.class)
public class ClientServiceImpl implements ClientService {
    @Reference
    private ClientDao clientDao;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") == null) {
            Collection<Client> clients = clientDao.readAll();
            response.getWriter().write(new Gson().toJson(clients));
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Client client = clientDao.readById(id);
                if (client == null) {
                    response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "No products found")));
                    return;
                }
                response.getWriter().write(new Gson().toJson(client));
            } catch (NumberFormatException ex) {
                response.getWriter().write(new Gson().toJson(ex));
            }
        }

    }

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String payloadString = null;
        response.setContentType("application/json");
        try {
            payloadString = IOUtils.toString(request.getReader());
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e));
        }
        if (payloadString.contains("[") || payloadString.contains("]")) {
            ArrayList<Client> clients;
            Type clientListType = new TypeToken<ArrayList<Client>>(){}.getType();
            try {
                clients = new Gson().fromJson(payloadString, clientListType);
                clientDao.insertMany(clients);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Clients successfully registered")));
            } catch (Exception e) {
                response.getWriter().write(new Gson().toJson(e.getMessage()));
            }
        } else {
            Client client;
            try {
                client = new Gson().fromJson(payloadString, Client.class);
                clientDao.insert(client);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Client successfully registered")));
            } catch (Exception e) {
                response.getWriter().write(new Gson().toJson(e.getMessage()));
            }
        }
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") != null) {
            try {
                clientDao.delete(Integer.parseInt(request.getParameter("id")));
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Client successfully deleted")));
            } catch (NumberFormatException ex) {
                response.getWriter().write(new Gson().toJson(ex));
            }
        }
    }

    @Override
    public void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String payloadString = null;
        response.setContentType("application/json");
        try {
            payloadString = IOUtils.toString(request.getReader());
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e));
        }
        Client client;
        try {
            client = new Gson().fromJson(payloadString, Client.class);
            clientDao.update(client);
            response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Client updated successfully")));
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e.getMessage()));
        }
    }
}
