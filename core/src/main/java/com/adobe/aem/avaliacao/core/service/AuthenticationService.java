package com.adobe.aem.avaliacao.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
}
