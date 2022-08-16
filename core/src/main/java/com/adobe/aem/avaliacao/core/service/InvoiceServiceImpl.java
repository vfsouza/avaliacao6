package com.adobe.aem.avaliacao.core.service;

import com.adobe.aem.avaliacao.core.dao.InvoiceDao;
import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.Invoice;
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


@Component(immediate = true, service = InvoiceService.class)
public class InvoiceServiceImpl implements InvoiceService {
    @Reference
    private InvoiceDao invoiceDao;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("number") == null) {
            Collection<Invoice> invoices = invoiceDao.readAll();
            response.getWriter().write(new Gson().toJson(invoices));
        } else {
            try {
                int number = Integer.parseInt(request.getParameter("number"));
                Invoice invoice = invoiceDao.readById(number);
                if (invoice == null) {
                    response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "No products found")));
                    return;
                }
                response.getWriter().write(new Gson().toJson(invoice));
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
            ArrayList<Invoice> clients;
            Type invoiceListType = new TypeToken<ArrayList<Invoice>>(){}.getType();
            try {
                clients = new Gson().fromJson(payloadString, invoiceListType);
                invoiceDao.insertMany(clients);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            Invoice invoice;
            try {
                invoice = new Gson().fromJson(payloadString, Invoice.class);
                invoiceDao.insert(invoice);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") != null) {
            try {
                invoiceDao.delete(Integer.parseInt(request.getParameter("id")));
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
        Invoice invoice;
        try {
            invoice = new Gson().fromJson(payloadString, Invoice.class);
            invoiceDao.update(invoice);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
