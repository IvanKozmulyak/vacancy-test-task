package com.ikozm.vacancytesttask.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ikozm.vacancytesttask.model.LocationStats;
import com.ikozm.vacancytesttask.model.Vacancy;
import com.ikozm.vacancytesttask.model.VacancyView;
import com.ikozm.vacancytesttask.repository.VacancyRepository;
import com.ikozm.vacancytesttask.repository.VacancyViewRepository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VacancyControllerTest {

    private VacancyRepository     vacancyRepository;
    private VacancyViewRepository vacancyViewRepository;
    private VacancyController     vacancyController;

    @Before
    public void setup() {
        vacancyRepository = mock(VacancyRepository.class);
        vacancyViewRepository = mock(VacancyViewRepository.class);
        vacancyController = new VacancyController(vacancyRepository, vacancyViewRepository);
    }

    @Test
    public void getVacancy_shouldReturnListOfVacancies() {
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

    @Test
    public void getVacancy_shouldReturnListOfLocationStats() {
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

    @Test
    public void getTopJobs() {
        List<Vacancy> expectedTopJobs = new ArrayList<>();
        Vacancy job1 = new Vacancy();
        job1.setId(1L);
        job1.setTitle("Software Engineer");
        job1.setViews(List.of(new VacancyView(job1),
                              new VacancyView(job1),
                              new VacancyView(job1),
                              new VacancyView(job1),
                              new VacancyView(job1)));
        expectedTopJobs.add(job1);

        Vacancy job2 = new Vacancy();
        job2.setId(2L);
        job2.setTitle("Product Manager");
        job2.setViews(List.of(new VacancyView(job1), new VacancyView(job1)));
        expectedTopJobs.add(job2);

        when(vacancyRepository.findTopByViews(2)).thenReturn(expectedTopJobs);

        List<Vacancy> actualTopJobs = vacancyController.getTopJobs(2);

        assertEquals(expectedTopJobs.size(), actualTopJobs.size());
        assertEquals(expectedTopJobs.get(0).getId(), actualTopJobs.get(0).getId());
        assertEquals(expectedTopJobs.get(1).getId(), actualTopJobs.get(1).getId());
    }
}
