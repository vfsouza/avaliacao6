package com.adobe.aem.avaliacao.core.service;

import com.adobe.aem.avaliacao.core.dao.ProductDao;
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

@Component(immediate = true, service = ProductService.class)
public class ProductServiceImpl implements ProductService {
    @Reference
    private ProductDao productDao;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (request.getParameter("id") == null) {
            ArrayList<Product> products = productDao.readAll(request.getParameter("param"));
            response.getWriter().write(new Gson().toJson(products));
        } else {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDao.readById(id);
                response.getWriter().write(new Gson().toJson(product));
                if (product == null) {
                    response.getWriter().write(new Gson().toJson(new ResponseJSON(400, "No products found")));
                }
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
            ArrayList<Product> products;
            Type productListType = new TypeToken<ArrayList<Product>>(){}.getType();
            try {
                products = new Gson().fromJson(payloadString, productListType);
                productDao.insertMany(products);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Products successfully registered")));
            } catch (Exception e) {
                response.getWriter().write(new Gson().toJson(e.getMessage()));
            }
        } else {
            Product product;
            try {
                product = new Gson().fromJson(payloadString, Product.class);
                productDao.insert(product);
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Product successfully registered")));
            } catch (Exception e) {
                response.getWriter().write(new Gson().toJson(e.getMessage()));
            }
        }
    }

    @Override
    public void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            try {
                productDao.delete(Integer.parseInt(request.getParameter("id")));
                response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Product successfully deleted")));
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
        Product product;
        try {
            product = new Gson().fromJson(payloadString, Product.class);
            productDao.update(product);
            response.getWriter().write(new Gson().toJson(new ResponseJSON(200, "Product successfully updated")));
        } catch (Exception e) {
            response.getWriter().write(new Gson().toJson(e.getMessage()));
        }
    }
}
