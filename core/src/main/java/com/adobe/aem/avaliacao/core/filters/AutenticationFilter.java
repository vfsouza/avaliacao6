package com.adobe.aem.avaliacao.core.filters;

import com.adobe.aem.avaliacao.core.models.Client;
import com.adobe.aem.avaliacao.core.models.ResponseJSON;
import com.google.gson.Gson;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component(service = Filter.class, property = {
        EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
        EngineConstants.SLING_FILTER_PATTERN + "=" + "/bin/keepalive/*"
})

@ServiceDescription("Authentication Filter")

@ServiceRanking(1)

@ServiceVendor("Adobe")
public class AutenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SlingHttpServletRequest req = (SlingHttpServletRequest) servletRequest;
        SlingHttpServletResponse resp = (SlingHttpServletResponse) servletResponse;

        HttpSession hs = req.getSession();
        Client client = (Client) hs.getAttribute("client");

        if (client == null) {
            resp.getWriter().write(new Gson().toJson(new ResponseJSON(401, "Unauthorized access. Please log in.")));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
