package com.adobe.aem.avaliacao.core.service;

import com.adobe.aem.avaliacao.core.dao.ClientDao;
import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.ResponseJSON;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component(immediate = true, service = AuthenticationService.class)
public class AuthenticationServiceImpl implements AuthenticationService {
    @Reference
    private ClientDao clientDao;

    @Override
    public void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String emailRequest = request.getParameter("email");
        String passwordRequest = request.getParameter("password");
        try {
            Client client = clientDao.readByEmail(emailRequest);
            if (client == null || !emailRequest.equals(client.getEmail()) && !passwordRequest.equals(client.getPassword())) {
                response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "Wrong email or password")));
                return;
            }
            HttpSession hs = request.getSession();
            hs.setAttribute("client", client);
            response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Client successfully authenticated")));
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e.getMessage()));
        }
    }
}
