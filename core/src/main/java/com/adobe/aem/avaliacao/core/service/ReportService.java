package com.adobe.aem.avaliacao.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import java.io.IOException;

public interface ReportService {
    void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException;
}