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
                    response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "No invoices found")));
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
            ArrayList<Invoice> invoices;
            Type invoiceListType = new TypeToken<ArrayList<Invoice>>(){}.getType();
            try {
                invoices = new Gson().fromJson(payloadString, invoiceListType);
                invoiceDao.insertMany(invoices);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Invoices successfully registered")));
            } catch (Exception e) {
                response.getWriter().write(new Gson().toJson(e.getMessage()));
            }
        } else {
            Invoice invoice;
            try {
                invoice = new Gson().fromJson(payloadString, Invoice.class);
                invoiceDao.insert(invoice);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Invoice successfully registered")));
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
                invoiceDao.delete(Integer.parseInt(request.getParameter("id")));
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Invoice successfully deleted")));
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
            response.getWriter().write(new Gson().toJson(e.getMessage()));
        }
        Invoice invoice;
        try {
            invoice = new Gson().fromJson(payloadString, Invoice.class);
            invoiceDao.update(invoice);
            response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Invoice successfully updated")));
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e.getMessage()));
        }
    }
}
