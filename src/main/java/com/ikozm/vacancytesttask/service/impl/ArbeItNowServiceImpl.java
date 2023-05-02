package com.ikozm.vacancytesttask.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.service.ArbeItNowService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the {@link ArbeItNowService} interface.
 */
@Service
public class ArbeItNowServiceImpl implements ArbeItNowService {

    private static final Logger       log = LoggerFactory.getLogger(ArbeItNowServiceImpl.class);
    private final        RestTemplate restTemplate;
    private final        String       URL = "https://www.arbeitnow.com/api/job-board-api";

    public ArbeItNowServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<Vacancy> getVacancies(int page) {
        log.info("Retrieving vacancies from ArbeItNow for page: {}", page);
        ResponseEntity<String> response = restTemplate.getForEntity(URL + "?page=" + page, String.class);
        Set<Vacancy> vacancies = new HashSet<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data");
            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    Vacancy vacancy = mapper.treeToValue(node, Vacancy.class);
                    vacancies.add(vacancy);
                }
            }
        } catch (JsonProcessingException e) {
            log.error("Error while processing JSON: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request: {}", e.getMessage());
        }
        log.debug("Retrieved vacancies from ArbeItNow: {}", vacancies);
        return vacancies;
    }
}
