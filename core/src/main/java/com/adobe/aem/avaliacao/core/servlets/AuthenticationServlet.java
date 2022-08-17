package com.adobe.aem.avaliacao.core.servlets;

import com.adobe.aem.avaliacao.core.service.AuthenticationService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(immediate = true, service = Servlet.class, property = {
        SLING_SERVLET_METHODS + "=" + "POST",
        SLING_SERVLET_PATHS + "=" + "/bin/client/login",
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})

@ServiceDescription("Authentication servlet")
public class AuthenticationServlet extends SlingAllMethodsServlet {

    @Reference
    private AuthenticationService authenticationService;

    @Override
    protected void doPost(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response) throws ServletException, IOException {
        authenticationService.doPost(request, response);
    }
}
