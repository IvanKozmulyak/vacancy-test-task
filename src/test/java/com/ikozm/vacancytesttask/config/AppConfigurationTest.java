package com.ikozm.vacancytesttask.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class AppConfigurationTest {
    private static final AppConfiguration appConfiguration = new AppConfiguration();

    @Test
    public void testRestTemplateBean() {
        RestTemplate restTemplate = appConfiguration.restTemplate();
        assertNotNull(restTemplate);
    }
}
