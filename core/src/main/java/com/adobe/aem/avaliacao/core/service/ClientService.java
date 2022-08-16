package com.adobe.aem.avaliacao.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;

import java.io.IOException;

public interface ClientService {
    void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
    void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
    void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
    void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
}
