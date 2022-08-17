package com.adobe.aem.avaliacao.core.servlets;

import com.adobe.aem.avaliacao.core.service.ProductService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junit.framework.Assert;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

@ExtendWith(AemContextExtension.class)
public class AuthenticationServletTest {

    private ProductServlet productServlet;
    @Mock
    private ProductService productServiceMock;
    private MockSlingHttpServletRequest request;
    private MockSlingHttpServletResponse response;

    @BeforeEach
    void setup(AemContext context) {
        MockitoAnnotations.openMocks(this);

        request = context.request();
        response = context.response();

        productServlet = new ProductServlet(productServiceMock);
    }

    @Test
    void doGet() throws IOException {
        productServiceMock.doGet(request, response);
        Assertions.assertNotNull(response.getOutputAsString());
    }
}
