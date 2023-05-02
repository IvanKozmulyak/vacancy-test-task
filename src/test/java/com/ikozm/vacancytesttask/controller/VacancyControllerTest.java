package com.ikozm.vacancytesttask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ikozm.vacancytesttask.model.LocationStats;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.repository.VacancyRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class VacancyControllerTest {

    private VacancyRepository vacancyRepository;
    private VacancyController vacancyController;

    @Before
    public void setup() {
        vacancyRepository = mock(VacancyRepository.class);
        vacancyController = new VacancyController(vacancyRepository);
    }

    @Test
    public void getJobs_shouldReturnListOfVacancies() {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt"));
        Vacancy vacancy1 = new Vacancy();
        vacancy1.setTitle("Job title 1");
        vacancy1.setCompanyName("Company name 1");
        vacancy1.setDescription("Job description 1");
        vacancy1.setUrl("url 1");
        Vacancy vacancy2 = new Vacancy();
        vacancy2.setTitle("Job title 2");
        vacancy2.setCompanyName("Company name 2");
        vacancy2.setDescription("Job description 2");
        vacancy2.setUrl("url 2");
        List<Vacancy> expectedVacancies = Arrays.asList(vacancy1, vacancy2);

        // When
        Page<Vacancy> pages = new PageImpl<>(expectedVacancies, pageable, expectedVacancies.size());
        when(vacancyRepository.findAll(pageable)).thenReturn(pages);

        Page<Vacancy> actualVacancies = vacancyController.getJobs(0, 10, "createdAt");

        // Then
        assertEquals(expectedVacancies, actualVacancies.getContent());
    }

    @Test
    public void getLocationStats_shouldReturnListOfLocationStats() {
        // Given
        List<LocationStats> expectedLocationStats = Arrays.asList(
                new LocationStats("Location 1", 5L),
                new LocationStats("Location 2", 3L)
                                                                 );

        // When
        when(vacancyRepository.getLocationStats()).thenReturn(expectedLocationStats);

        List<LocationStats> actualLocationStats = vacancyController.getLocationStats();

        // Then
        assertEquals(expectedLocationStats, actualLocationStats);
    }
}
