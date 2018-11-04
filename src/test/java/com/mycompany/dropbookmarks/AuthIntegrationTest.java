/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dropbookmarks;

import io.dropwizard.Application;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.net.ssl.SSLContext;
import org.junit.ClassRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class AuthIntegrationTest {
    private static final String CONFIG_PATH
            = ResourceHelpers.resourceFilePath("test-config.yml");
    
    @ClassRule
    public static final DropwizardAppRule<dropbookmarksConfiguration> RULE
            = new DropwizardAppRule<>(dropbookmarksApplication.class,
            CONFIG_PATH);
    
    private static final HttpAuthenticationFeature FEATURE
            = HttpAuthenticationFeature.basic("username", "password");
    
//    private static final String TARGET = 
//            "http://localhost:8080";
    
    private static final String TARGET = 
            "https://localhost:8443";
    
    private static final String PATH = 
            "/hello/secured";
    
    private static final String TRUST_STORE_FILE_NAME
            = "dropbookmarks.keystore";
    
    private static final String TRUST_STORE_PASSWORD
            ="password";
    
    
    private Client client;
    
    @BeforeClass
    public static void setUpClass() throws Exception{
        Application<dropbookmarksConfiguration> appplication
                = RULE.getApplication();
        appplication.run("db", "migrate", "-i DEV", CONFIG_PATH);
    }
    
//    @Before
//    public void setUp(){
//        client=ClientBuilder.newClient();
//    }
    
    @Before
    public void setUp(){
        SslConfigurator configurator
                = SslConfigurator.newInstance();
        configurator.trustStoreFile(TRUST_STORE_FILE_NAME)
                .trustStorePassword(TRUST_STORE_PASSWORD);
        SSLContext context = configurator.createSSLContext();
        client=ClientBuilder.newBuilder()
                .sslContext(context)
                .build();
    }
    
    @After
    public void tearDown(){
        client.close();
    }
    
    @Test
    public void testSadPath(){
        Response response = client
                .target(TARGET)
                .path(PATH)
                .request()
                .get();
        assertEquals(Response.Status
              .UNAUTHORIZED.getStatusCode(),
                response.getStatus());
    }

    @Test
    public void testHappyPath(){
       String expected = "hello secured world";
       client.register(FEATURE);
       String actual = client
               .target(TARGET)
               .path(PATH)
               .request(MediaType.TEXT_PLAIN)
               .get(String.class);
       assertEquals(expected, actual);
    }
    
}
