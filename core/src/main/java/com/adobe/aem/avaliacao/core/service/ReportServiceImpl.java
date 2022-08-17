package com.adobe.aem.avaliacao.core.service;

import com.adobe.aem.avaliacao.core.dao.ClientDao;
import com.adobe.aem.avaliacao.core.dao.InvoiceDao;
import com.adobe.aem.avaliacao.core.dao.ProductDao;
import com.adobe.aem.avaliacao.core.models.ClientProductDTO;
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
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

@Component(immediate = true, service = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Reference
    private InvoiceDao invoiceDao;

    @Reference
    private ClientDao clientDao;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        if (request.getParameter("idClient") == null) {
            response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "Client id not provided")));
        } else {
            try {
                int clientId = Integer.parseInt(request.getParameter("idClient"));
                ArrayList<Integer> productIds = invoiceDao.readProductIds(clientId);
                ArrayList<Product> products = invoiceDao.readByProductId(productIds);
                ClientProductDTO cpDTO = new ClientProductDTO(clientDao.readById(clientId), products);
                PrintWriter pw = response.getWriter();
                pw.println("<div>");
                pw.println("ID: " + cpDTO.getClient().getId() + "<br>");
                pw.println("Name: " + cpDTO.getClient().getName() + "<br>");
                pw.println("Email: " + cpDTO.getClient().getEmail());
                pw.println("</div>");
                pw.println("<div>");
                pw.println("Products list: <br>");
                pw.println("<ul>");
                for (Product p : cpDTO.getProducts()) {
                    pw.println("<li>" + p.getId() + " | " + p.getName() + " | " + p.getCategory() + " | " + p.getPrice() + "</li>");
                }
                pw.println("<ul>");
                pw.println("</div>");
            } catch (NumberFormatException ex) {
                response.getWriter().write(new Gson().toJson(ex.getMessage()));
            }
        }

    }
}
