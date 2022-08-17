package com.adobe.aem.avaliacao.core.servlets;

import com.adobe.aem.avaliacao.core.service.ProductService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.jetbrains.annotations.NotNull;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;


@Component(immediate = true, service = Servlet.class, property = {
        SLING_SERVLET_METHODS + "=" + "GET",
        SLING_SERVLET_METHODS + "=" + "PUT",
        SLING_SERVLET_METHODS + "=" + "DELETE",
        SLING_SERVLET_METHODS + "=" + "POST",
        SLING_SERVLET_PATHS + "=" + "/bin/sistema/product",
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})

@ServiceDescription("Product servlet")
public class ProductServlet extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 1L;

    @Activate
    public ProductServlet(@Reference ProductService productService) {
        this.productService = productService;
    }

    private final ProductService productService;

    @Override
    protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        productService.doGet(request, response);
    }

    @Override
    protected void doPost(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        productService.doPost(request, response);
    }

    @Override
    protected void doDelete(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        productService.doDelete(request, response);
    }

    @Override
    protected void doPut(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        productService.doPut(request, response);
    }
}
